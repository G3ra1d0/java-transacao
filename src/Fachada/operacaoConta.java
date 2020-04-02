/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fachada;

import Model.Conta;
import Model.Extrato;
import dao.ContaDAO;
import dao.ExtratoDAO;
import dao.FabricaConexao;
import java.sql.Connection;

/**
 *
 * @author g3ra1d0
 */
public class operacaoConta {

    public static void saque(Conta conta, double valorSaldo) {
        if (conta.getSaldo() >= 0) {
            Connection cnx = FabricaConexao.GeraConexaoCustomizada(1);

            try {
                Extrato extrato = new Extrato();
                extrato.setDescricao("Saque");
                extrato.setTipo("S");
                extrato.setValor(valorSaldo);
                extrato.setContaID(conta.getId());

                ExtratoDAO daoExtrato = new ExtratoDAO(cnx);
                daoExtrato.insert(extrato);

                conta.setSaldo(conta.getSaldo() - valorSaldo);
                ContaDAO daoConta = new ContaDAO(cnx);

                daoConta.update(conta);

                FabricaConexao.commitTransacao(cnx);
            } catch (Exception e) {
                FabricaConexao.rollbackTransacao(cnx);
            } finally {
                FabricaConexao.closeConnection(cnx);
            }

        } else {
            System.out.println("Saldo Insuficiente");
        }
    }

    public static void deposito(Conta conta, double valorSaldo) {
        Connection cnx = FabricaConexao.GeraConexaoCustomizada(1);

        try {
            Extrato extrato = new Extrato();
            extrato.setDescricao("Deposito");
            extrato.setTipo("D");
            extrato.setValor(valorSaldo);
            extrato.setContaID(conta.getId());

            ExtratoDAO daoExtrato = new ExtratoDAO(cnx);
            daoExtrato.insert(extrato);

            conta.setSaldo(conta.getSaldo() + valorSaldo);
            ContaDAO daoConta = new ContaDAO(cnx);

            daoConta.update(conta);

            FabricaConexao.commitTransacao(cnx);
        } catch (Exception e) {
            FabricaConexao.rollbackTransacao(cnx);
        } finally {
            FabricaConexao.closeConnection(cnx);
        }

    }

    public static void transferencia(Conta contaSaida, Conta contaDestino, double valorSaldo) {
        if (contaSaida.getSaldo() >= 0) {
            Connection cnx = FabricaConexao.GeraConexaoCustomizada(1);

            try {
                //   saque
                Extrato extrato = new Extrato();
                extrato.setDescricao("Transferencia");
                extrato.setTipo("S");
                extrato.setValor(valorSaldo);
                extrato.setContaID(contaSaida.getId());

                ExtratoDAO daoExtrato = new ExtratoDAO(cnx);
                daoExtrato.insert(extrato);

                contaSaida.setSaldo(contaSaida.getSaldo() - valorSaldo);
                
                // deposito
                extrato.setDescricao("Transferencia");
                extrato.setTipo("D");
                extrato.setValor(valorSaldo);
                extrato.setContaID(contaDestino.getId());

                daoExtrato.insert(extrato);

                contaDestino.setSaldo(contaDestino.getSaldo() + valorSaldo);
                
                // salvando
                ContaDAO daoConta = new ContaDAO(cnx);
                daoConta.update(contaSaida);
                daoConta.update(contaDestino);
                
                FabricaConexao.commitTransacao(cnx);
            } catch (Exception e) {
                FabricaConexao.rollbackTransacao(cnx);
            } finally {
                FabricaConexao.closeConnection(cnx);
            }
        } else {
            System.out.println("Saldo Insuficiente");

        }
    }

    public static void rendimento(Conta conta, double taxa){
        Connection cnx = FabricaConexao.GeraConexaoCustomizada(1);

        try {
            Extrato extrato = new Extrato();
            extrato.setDescricao("Rendimento");
            extrato.setTipo("R");
            extrato.setValor((conta.getSaldo() * taxa) / 100);
            extrato.setContaID(conta.getId());

            ExtratoDAO daoExtrato = new ExtratoDAO(cnx);
            daoExtrato.insert(extrato);

            conta.setSaldo(conta.getSaldo() + ((conta.getSaldo() * taxa) / 100));
            ContaDAO daoConta = new ContaDAO(cnx);

            daoConta.update(conta);

            FabricaConexao.commitTransacao(cnx);
        } catch (Exception e) {
            FabricaConexao.rollbackTransacao(cnx);
        } finally {
            FabricaConexao.closeConnection(cnx);
        }


    }
}
