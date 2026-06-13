package dao;

import db.Db;
import entities.Consulta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDao {
    public void insert(Consulta obj){ //metodo que recebe os dados da consulta que tem no banco
        //variaveis de conexão, começa com null pq ainda não tem nehum objeto
        Connection conn = null; //conexão com o banco
        PreparedStatement ps = null; //comando SQL que vai ser executado

        try {
            conn = Db.getConnection(); //metodo da classe Db
            //aqui vai ser as informações que tem no sql que vai ser executado
            ps = conn.prepareStatement("INSERT INTO Consulta " + "(Id_Paciente, CRM, ID_Setor, DataHora, Valor, Tipo, Status, Observacoes) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS); //pede ao mysql pra devolver o id da consulta gerado automaticamente
            ps.setInt(1,obj.getId_paciente()); //substitui a primeira ?
            ps.setString(2, obj.getCrm_do_medico()); //substitui a segunda ? e assim por diante
            ps.setInt(3, obj.getId_setor());
            ps.setTimestamp(4, Timestamp.valueOf(obj.getData_e_hora())); //Na classe consulta é LocalDateTime mas o mysql usa TimeStamp então aqui ta fazendo a conversão
            ps.setDouble(5,obj.getValor_da_consulta());
            ps.setString(6, obj.getTipo_de_urgencia());
            ps.setString(7, obj.getStatus_da_consulta());
            ps.setString(8, obj.getObservaçoes_sobre_consulta());

            //comando pra executar o sql do banco
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0){ //if pra verificar se inseriu pelo menos uma linha pra continuar
                ResultSet rs = ps.getGeneratedKeys(); //pega o id criado automaticamente pelo banco
                if (rs.next()){ //vai pra primeira linha do resultado
                    obj.setId_consulta(rs.getInt(1)); //coloca o id gerado dentro do objeto java antes era 0 e passa a ser outro numero
                }
                Db.closeResultSet(rs); //fecha o resultado pra liberar a memoria
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally { //vai executar msm se der algum erro
            Db.closeStatement(ps); //fechando o comando sql e liberando a memoria
        }
    }

    // esse metodo conecta no banco, seleciona a tabela consulta e coloca os objetos na lista
    public List<Consulta> findAll(){ //findAll é um metodo pra buscar todas as consultas do banco
        //essas são as variaveis de conexão
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = Db.getConnection(); //abrindo a conexão usando a classe db
            String sql = "SELECT * FROM Consulta ORDER BY ID_Consulta"; //vai buscar todas as consultas pelos id

            ps = conn.prepareStatement(sql); //prepara o comando
            rs = ps.executeQuery(); //executa o select e o resultado vai pro resultset

            //Criando uma lista vazia pra guardar as consultas encontradas
            List<Consulta> list = new ArrayList<>();
            while (rs.next()){ //vai passando por cada linha retornada pelo banco

                Consulta consulta = new Consulta(); //criando um objeto vazio para receber os dados da linha atual
                consulta.setId_consulta(rs.getInt("ID_Consulta")); //pegando o id da consulta no banco e colocando no objeto
                consulta.setId_paciente(rs.getInt("ID_Paciente"));
                consulta.setCrm_do_medico(rs.getString("CRM"));
                consulta.setId_setor(rs.getInt("ID_Setor"));
                consulta.setData_e_hora(rs.getTimestamp("DataHora").toLocalDateTime());
                consulta.setValor_da_consulta(rs.getDouble("Valor"));
                consulta.setTipo_de_urgencia(rs.getString("Tipo"));
                consulta.setStatus_da_consulta(rs.getString("Status"));
                consulta.setObservaçoes_sobre_consulta(rs.getString("Observacoes"));

                list.add(consulta); //colocar os objetos preenchidos na lista
            } return list; //quando terminar de percorrer a lista retorna ela com as consultas
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally { //fechando o resultado da consulta e o comando sql
            Db.closeResultSet(rs);
            Db.closeStatement(ps);
        }
    }

    //metodo update pra altera uma consulta que ja tenha no banco
    public void update(Consulta obj){
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = Db.getConnection();
            ps = conn.prepareStatement("UPDATE Consulta " + "SET ID_Paciente = ?, CRM = ?, ID_Setor= ?,  DataHora = ?, Valor = ?, Tipo = ?, Status = ?, Observacoes = ? " + "WHERE ID_Consulta = ?");
            //aqui é pra atualizar os da consulta que tem esse id

            ps.setInt(1,obj.getId_paciente());
            ps.setString(2, obj.getCrm_do_medico());
            ps.setInt(3, obj.getId_setor());
            ps.setTimestamp(4, Timestamp.valueOf(obj.getData_e_hora()));
            ps.setDouble(5,obj.getValor_da_consulta());
            ps.setString(6, obj.getTipo_de_urgencia());
            ps.setString(7, obj.getStatus_da_consulta());
            ps.setString(8, obj.getObservaçoes_sobre_consulta());
            ps.setInt(9,obj.getId_consulta()); //aqui é o id da consulta que vai atualizar
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            Db.closeStatement(ps); //fechando o comando
        }
    }

    //metodo pra deletear uma consulta
    public void deleteById(Integer id){ //recebe o id da consulta que vai ser excluida
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = Db.getConnection();
            //aqui é pra apagar a consulta com base no id que ele vai receber
            ps = conn.prepareStatement("DELETE FROM Consulta WHERE ID_Consulta = ?");
            ps.setInt(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            Db.closeStatement(ps);
        }

    }
}
