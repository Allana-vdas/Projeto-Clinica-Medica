package application;

import dao.ConsultaDao;
import dao.ExameDao;
import dao.PacienteDao;
import dao.UsuarioDao;
import entities.Consulta;
import entities.Exame;
import entities.Paciente;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.layout.VBox;
import java.text.SimpleDateFormat;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Main extends Application {
    //convertendo as datas
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    //criando objetos pra usar os comando sql que tem na classe dao
    private ConsultaDao consultaDao = new ConsultaDao();
    private PacienteDao pacienteDao = new PacienteDao();
    private UsuarioDao usuarioDao = new UsuarioDao();
    private ExameDao exameDao = new ExameDao();

    private TextField txtIdConsulta, txtIdPaciente,txtIdSetor, txtCRM;
    private TextField txtDataHora, txtValor, txtTipo, txtStatus, txtObservacoes;
    private TableView<Consulta> tableConsulta;

    private TextField txtPacienteId, txtNomePaciente, txtCpfPaciente, txtRgPaciente,
            txtNomeTelefonePaciente, txtNomeEnderecoPaciente,
            txtEmailPaciente, txtDataNascimentoPaciente, txtContatoEmergenciaPaciente, txtIdEnderecoPaciente,txtIdPlanoPaciente;
    private TableView<Paciente> tablePaciente;

    //ATRIBUTOS USUARIO EM BAIXO COLOCA O TABLEVIEW

    private TextField txtIdExame,txtIdConsultaExame,txtTipoExame,txtDataExame;
    private TextField txtResultado,txtStatusExame,txtValorExame;
    private TableView<Exame> tableExame;



    private Label lblStatusGlobal; //serve pra mostrar mensagens pro usuario na tela

    //metodo pra montar as abas
    private VBox criaPainelConsultas(){
return new VBox();
    }

    private VBox criarPainelPacientes(){
return new VBox();
    }

    private VBox criarPainelUsuario(){
return new VBox();
    }

    private VBox criarPainelExames(){
        return new VBox();

    }

    public void start(Stage primaryStage){

    }


}