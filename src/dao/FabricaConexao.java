
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author g3ra1d0
 */
public class FabricaConexao {

    private static final String STR_DRIVER = "org.gjt.mm.mysql.Driver";  // definição de qual banco será utilizado
    private static final String DATABASE = "banco"; // Nome do banco de dados         
    private static final String IP = "127.0.0.1";  // ip de conexao

    private static final String STR_CON = "jdbc:mysql://" + IP + ":3306/" + DATABASE; // string de conexao com o banco de dados

    private static final String USER = "root"; // Nome do usuário
    private static final String PASSWORD = "geraldo"; // senha

    private static Connection objConexao = null;

    public static Connection GeraConexao() {
        try {
//            Class.forName(STR_DRIVER);
            objConexao = DriverManager.getConnection(STR_CON, USER, PASSWORD);
            System.out.println("Conector com o BD!");
        } catch (SQLException e) {
            String errorMsg = "Erro ao obter a conexao: " + e.getMessage();
            System.out.println(errorMsg);
        }
        
        return objConexao;
    }
    
    public static Connection GeraConexaoCustomizada(int nivelIsolamento) {
        try {
            objConexao = DriverManager.getConnection(STR_CON, USER, PASSWORD);
            objConexao.setAutoCommit(false);
            objConexao.setTransactionIsolation(nivelIsolamento);
        } catch (SQLException e) {
            String errorMsg = "Erro ao obter a conexao: " + e.getMessage();
            System.out.println(errorMsg);
        }
        return objConexao;
    }

    public static void rollbackTransacao(Connection conexao) {
        try {
            conexao.rollback();
        } catch (SQLException e) {
            String errorMsg = "Erro ao obter a conexao: " + e.getMessage();
            System.out.println(errorMsg);
        }
    }

    public static void commitTransacao(Connection conexao) {
        try {
            conexao.commit();
        } catch (SQLException e) {
            String errorMsg = "Erro ao obter a conexao: " + e.getMessage();
            System.out.println(errorMsg);
        }
    }

    public static void closeConnection(Connection conexao) {
        try {
            conexao.close();
        } catch (SQLException e) {
            String errorMsg = "Erro ao obter a conexao: " + e.getMessage();
            System.out.println(errorMsg);
        }
    }

}
