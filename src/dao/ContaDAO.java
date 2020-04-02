/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Model.Conta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author g3ra1d0
 */
public class ContaDAO {

    private Connection conexao;

//    public ContaDAO() {
//        this.conexao = FabricaConexao.GeraConexao();
//    }
    public ContaDAO() {
        this.conexao = FabricaConexao.GeraConexao();
    }
    

    public ContaDAO(Connection objConexao) {
        this.conexao = objConexao;
    }

    public void insert(Conta objConta) {
        String sql = "insert into conta( descricao, saldo) values (?, ?)";

        try {
            PreparedStatement pst = this.conexao.prepareStatement(sql);

            pst.setString(1, objConta.getDescricao());
            pst.setDouble(2, objConta.getSaldo());

            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro ao salvar o objeto: " + ex.getMessage());
        }
    }

    public void update(Conta objConta) {
        String sql = "update conta set descricao = ? , saldo = ? where  id = ? ";
        try {
            PreparedStatement pst = this.conexao.prepareStatement(sql);

            pst.setString(1, objConta.getDescricao());
            pst.setDouble(2, objConta.getSaldo());
            pst.setInt(3, objConta.getId());

            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar o objeto: " + ex.getMessage());
        }
    }

    public void delete(Conta objConta) {
        String sql = "Delete from conta where id = ?";

        try {
            PreparedStatement pst = this.conexao.prepareStatement(sql);

            pst.setInt(1, objConta.getId());

            pst.executeUpdate();
        } catch (Exception e) {
            System.err.println("Erro ao excluir objeto: " + e.getMessage());
        }
    }

    public Conta select(int id) {
        String sql = "select * from conta where id = ?";

        try {
            PreparedStatement pst = this.conexao.prepareStatement(sql);

            pst.setInt(1, id);

            ResultSet Resultado = pst.executeQuery();
            Conta objConta = new Conta();

            if (Resultado.next()) {
                objConta.setId(Resultado.getInt("id"));
                objConta.setDescricao(Resultado.getString("descricao"));
                objConta.setSaldo(Resultado.getDouble("saldo"));
            }

            return objConta;

        } catch (Exception e) {
            System.err.println("Erro ao selecionar objeto: " + e.getMessage());
        }

        return null;
    }

}
