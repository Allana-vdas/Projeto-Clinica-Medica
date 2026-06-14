package application;

import dao.ConsultaDao;
import dao.ExameDao;
import dao.PacienteDao;
import dao.UsuarioDao;
import entities.Consulta;
import entities.Exame;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.layout.VBox;
import java.text.SimpleDateFormat;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import entities.Usuario;

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

    //ATRIBUTOS PACIENTE COLOCA PACIENTEID E NAO IDPACIENTE EM BAIXO COLOCA O TABLEVIEW


    private TextField txtIdUsuario,txtNome,txtTelefone,txtCpf,txtEmail,txtCargo;
    private TextField txtSetor;
    private TextField txtNivelAcesso;

    private TableView<Usuario> tableUsuario;

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