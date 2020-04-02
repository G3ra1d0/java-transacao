/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Model.Extrato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author g3ra1d0
 */
public class ExtratoDAO {

    private Connection conexao;

//    public ExtratoDAO() {
//        this.conexao = FabricaConexao.GeraConexao();
//    }


    public ExtratoDAO(Connection objConexao) {
        this.conexao = objConexao;
    }    
    
    public void insert(Extrato objExtrato) {
        String sql = "insert into extrato( descricao, valor, tipo, contaID ) values (?, ?, ?, ?)";

        try {
            PreparedStatement pst = this.conexao.prepareStatement(sql);

            pst.setString(1, objExtrato.getDescricao());
            pst.setDouble(2, objExtrato.getValor());
            pst.setString(3, objExtrato.getTipo());
            pst.setInt(4, objExtrato.getContaID());

            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro ao salvar o objeto: " + ex.getMessage());
        }
    }

    public void update(Extrato objExtrato) {
        String sql = " update extrato set descricao = ? , valor = ? , tipo = ? , contaID = ? where  id = ? ";
        try {

            PreparedStatement pst = this.conexao.prepareStatement(sql);

            pst.setString(1, objExtrato.getDescricao());
            pst.setDouble(2, objExtrato.getValor());
            pst.setString(3, objExtrato.getTipo());
            pst.setInt(4, objExtrato.getContaID());
            pst.setInt(5, objExtrato.getId());

            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar o objeto: " + ex.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "Delete from extrato where id = ?";

        try {
            PreparedStatement pst = this.conexao.prepareStatement(sql);

            pst.setInt(1, id);

            pst.executeUpdate();
        } catch (Exception e) {
            System.err.println("Erro ao excluir objeto: " + e.getMessage());
        }
    }

    public Extrato select(int id) {
        String sql = "select * from extrato where id = ?";

        try {
            PreparedStatement pst = this.conexao.prepareStatement(sql);

            pst.setInt(1, id);

            ResultSet Resultado = pst.executeQuery();
            Extrato objExtrato = new Extrato();

            if (Resultado.next()) {
                objExtrato.setId(Resultado.getInt("id"));
                objExtrato.setDescricao(Resultado.getString("descricao"));
                objExtrato.setValor(Resultado.getDouble("valor"));
                objExtrato.setTipo(Resultado.getString("tipo"));
                objExtrato.setContaID(Resultado.getInt("contaID"));

            }

            return objExtrato;

        } catch (Exception e) {
            System.err.println("Erro ao selecionar objeto: " + e.getMessage());
        }

        return null;
    }

}
