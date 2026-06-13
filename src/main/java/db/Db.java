package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;


//classe responsavel por abrir e fechar conexão como banco
public class Db {
    //variavel de conexão null pq ainda não existe uma conexão
    private static Connection conn = null;

    //metodo pra abrir conexão
    public static Connection getConnection() {
        if (conn == null) { //verificando se já tem uma conexão aberta, se não tiver abre uma
            try {
                Properties props = loadProperties(); //carrega os dados do arquivo de texto
                System.out.println(props.getProperty("user"));
                String url = props.getProperty("dburl"); //guarda o jdbc:mysql://localhost:3306/clinicamedica na variavel url
                conn = DriverManager.getConnection(url, props); //aqui é a conversa entre java e o Mysql se tiver tudo certo cria a conexão
            } catch (SQLException e) { //tratamento de erro pra caso o banco tiver desligado ou senha errada
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    //fechando a conexão de forma segura
    public static void closeConnection() {
        if (conn != null) { //fecha se estiver aberta
            try {
                conn.close(); //encerra a comunicação com o banco
                conn = null; //para abrir outra conexão dps
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    //metodo auxiliar pra proteger os dados do banco como usuario e senha
    private static Properties loadProperties() { //le o arquivo db.properties
        try {
            FileInputStream fs = new FileInputStream("db.properties"); //abrindo o arquivo
            Properties pros = new Properties();//guarda as informações do arquivo
            pros.load(fs); //le tudo que tem no arquivo
            return pros;
        } catch (IOException e) {
            throw new DbException("Erro ao ler as credenciais do banco: " + e.getMessage());
        }
    }

    //metodo pra fechar comandos do mysql
    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }

    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}