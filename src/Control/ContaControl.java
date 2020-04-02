/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Fachada.operacaoConta;
import Model.Conta;
import dao.ContaDAO;
import dao.FabricaConexao;
import java.sql.Connection;

/**
 *
 * @author g3ra1d0
 */
public class ContaControl {

    public void saque(int idConta, double valor) {
        ContaDAO dao = new ContaDAO();
        Conta conta;
        conta = dao.select(idConta);
        operacaoConta.saque(conta, valor);

    }
    
    public void deposito(int idConta, double valor){
        ContaDAO dao = new ContaDAO();
        Conta conta;
        conta = dao.select(idConta);
        operacaoConta.deposito(conta, valor);
    }
    
    public void transferencia(int idContaSaida, int idContaDestino, double valor){
        ContaDAO dao = new ContaDAO();
        Conta contaSaida, contaDestino;
        contaSaida = dao.select(idContaSaida);
        contaDestino = dao.select(idContaDestino);
        operacaoConta.transferencia(contaSaida, contaDestino, valor);
    }
    
    public void rendimento(int idConta, double taxa){
        ContaDAO dao = new ContaDAO();
        Conta conta;
        conta = dao.select(idConta);
        operacaoConta.rendimento(conta, taxa);
    }
    

}

