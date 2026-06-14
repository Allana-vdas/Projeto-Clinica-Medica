package dao;


import db.Db;
import entities.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PacienteDao {

    public void insert(Paciente obj){

        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = Db.getConnection();

        ps = conn.prepareStatement("INSERT INTO Paciente "
                +"(Nome, CPF, RG, Telefone, Endereco, Email, Data_Nascimento, Contato_Emergencia, ID_Endereco, ID_Plano) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, obj.getNome());
        ps.setString(2, obj.getCpf());
        ps.setString(3, obj.getRg());
        ps.setString(4, obj.getTelefone());
        ps.setString(5, obj.getEndereco());
        ps.setString(6, obj.getEmail());
        ps.setString(7, obj.getDataNascimento());
        ps.setString(8, obj.getContatoEmergencia());
        ps.setInt(9, obj.getIdEndereco());
        ps.setInt(10, obj.getIdPlano());

        int rowsAffected = ps.executeUpdate();

        if(rowsAffected > 0){

            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()){
                obj.setIdPaciente(rs.getInt(1));
            }

            Db.closeResultSet(rs);
        }


        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

 finally {
            Db.closeStatement(ps);

            }
        }
        public List<Paciente> findAll(){

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{

            conn = Db.getConnection();

            String sql = "SELECT * FROM Paciente ORDER BY ID_Paciente";

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            List<Paciente> list = new ArrayList<>();

           while (rs.next()) {

               Paciente paciente = new Paciente();

               paciente.setIdPaciente(rs.getInt("ID_Paciente"));
               paciente.setNome(rs.getString("Nome"));
               paciente.setCpf(rs.getString("CPF"));
               paciente.setRg(rs.getString("RG"));
               paciente.setTelefone(rs.getString("Telefone"));
               paciente.setEndereco(rs.getString("Endereco"));
               paciente.setEmail(rs.getString("Email"));
               paciente.setDataNascimento(rs.getString("Data_Nascimento"));
               paciente.setContatoEmergencia(rs.getString("Contato_Emergencia"));
               paciente.setIdEndereco(rs.getInt("ID_Endereco"));
               paciente.setIdPlano(rs.getInt("ID_Plano"));

               list.add(paciente);

           }

           return list;

        } catch (SQLException e ){
            throw new RuntimeException(e.getMessage());

            }
            finally{

                Db.closeResultSet(rs);
                Db.closeStatement(ps);


            }
        }

        public void update(Paciente obj){

        Connection conn = null;
        PreparedStatement ps = null;

        try {


            conn = Db.getConnection();


                    ps = conn.prepareStatement(
                            "UPDATE Paciente "
                            + "SET Nome = ?, "
                            + "CPF = ?, "
                                    + "RG = ?, "
                                    + "Telefone = ?, "
                                    + "Endereco = ?, "
                                    + "Email = ?, "
                                    + "Data_Nascimento = ?, "
                                    + "Contato_Emergencia = ?, "
                                    + "ID_Endereco= ?, "
                                    + "ID_Plano = ? "
                                    + "WHERE ID_Paciente = ? ");



                            ps.setString(1, obj.getNome());
                            ps.setString(2, obj.getCpf());
                             ps.setString(3, obj.getRg());
                             ps.setString(4, obj.getTelefone());
                             ps.setString(5, obj.getEndereco());
                             ps.setString(6, obj.getEmail());
                             ps.setString(7, obj.getDataNascimento());
                            ps.setString(8, obj.getContatoEmergencia());
                            ps.setInt(9, obj.getIdEndereco());
                            ps.setInt(10, obj.getIdPlano());
                            ps.setInt(1, obj.getIdPaciente());


                            ps.executeUpdate();


        } catch (SQLException e ){
            throw new RuntimeException(e.getMessage());

        }

finally {
            Db.closeStatement(ps);

        }

    }

    public void deleteById(Integer id){

        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = Db.getConnection();

            ps  = conn.prepareStatement(
                    "DELETE FROM Paciente WHERE ID_Paciente = ?");

                    ps.setInt(1,id);

                    ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());

        }

        finally {
            Db.closeStatement(ps);
        }
    }
}
