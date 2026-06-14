package application;

import dao.ConsultaDao;
import dao.ExameDao;
import dao.PacienteDao;
import dao.UsuarioDao;
import entities.Consulta;
import entities.Exame;
import entities.Paciente;
import entities.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.layout.VBox;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Main extends Application {
    //convertendo as datas
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    //criando objetos pra usar os comando sql que tem na classe dao
    private ConsultaDao consultaDao = new ConsultaDao();
    private PacienteDao pacienteDao = new PacienteDao();
    private UsuarioDao usuarioDao = new UsuarioDao();
    private ExameDao exameDao = new ExameDao();

    private TextField txtIdConsulta, txtIdPaciente, txtIdSetor, txtCRM;
    private TextField txtDataHora, txtValor, txtTipo, txtStatus, txtObservacoes;
    private TableView<Consulta> tableConsulta;

    private TextField txtPacienteId, txtNomePaciente, txtCpfPaciente, txtRgPaciente,
            txtNomeTelefonePaciente, txtNomeEnderecoPaciente,
            txtEmailPaciente, txtDataNascimentoPaciente, txtContatoEmergenciaPaciente, txtIdEnderecoPaciente,txtIdPlanoPaciente;
    private TableView<Paciente> tablePaciente;

    private TextField txtIdUsuario,txtNome,txtTelefone,txtCpf,txtEmail,txtCargo;
    private TextField txtSetor, txtNivelAcesso;
    private TableView<Usuario> tableUsuario;

    private TextField txtIdExame,txtIdConsultaExame,txtTipoExame,txtDataExame;
    private TextField txtResultado,txtStatusExame,txtValorExame;
    private TableView<Exame> tableExame;

    private Label lblStatusGlobal; //serve pra mostrar mensagens pro usuario na tela

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Clícica Médica"); //definindo o titulo da janela

        lblStatusGlobal = new Label("Status: Banco De Dados Conectado!"); //criando um texto na tela
        lblStatusGlobal.setText("O Sistema Foi Iniciado!"); //mudando o texto

        TabPane tabPane = new TabPane(); //criando o conjunto de abas, TabPane guarda todas as abas
        Tab tabConsulta = new Tab("Consultas", criaPainelConsultas()); //criando a aba chamada consulta
        Tab tabPaciente = new Tab("Pacientes", criarPainelPacientes());
        Tab tabUsuario = new Tab("Usuários", criarPainelUsuario());
        Tab tabExame = new Tab("Exames", criarPainelExames());

        tabConsulta.setClosable(false); //para impedir que o usuario feche a aba
        tabPaciente.setClosable(false);
        tabUsuario.setClosable(false);
        tabExame.setClosable(false);
        tabPane.getTabs().addAll(tabConsulta, tabPaciente, tabUsuario, tabExame); //adicionando as abas dentro do tabpane no conjunto

        VBox root = new VBox(10, tabPane, lblStatusGlobal); //Vbox organizador visual pra colocar os componentes um embaixo do outro, 10 é o espaço entre eles
        root.setPadding(new Insets(10));

        //informando a altura e largura da janela
        primaryStage.setScene(new Scene(root, 600, 650));
        primaryStage.show(); //pra exibir a janela

        atualizarListas();
    }
    //metodo pra montar as abas
    private VBox criaPainelConsultas() {
        txtIdConsulta = new TextField(); //criando uma caixa de texto pro usuario digitar
        txtIdConsulta.setPromptText("ID da Consulta"); //mostrar esse texto enquanto tiver vasio
        txtIdConsulta.setEditable(false); //pra impedir que o usuario altere o id que vai vim do banco automatico
        txtIdConsulta.setStyle("-fx-background-color: #e8e8e8;"); // deixar o campo cinza indicando bloqueio, o usuario nao pode digitar
        //esses 2 ultimos txt é só pro id principal que é o da classe
        txtIdPaciente = new TextField();
        txtIdPaciente.setPromptText("ID do Paciente");
        txtCRM = new TextField();
        txtCRM.setPromptText("CRM do Médico");
        txtIdSetor = new TextField();
        txtIdSetor.setPromptText("ID do Setor");
        txtDataHora = new TextField();
        txtDataHora.setPromptText("Data e Hora da Consulta");
        txtValor = new TextField();
        txtValor.setPromptText("Valor da Consulta");
        txtTipo = new TextField();
        txtTipo.setPromptText("Tipo da Urgência");
        txtStatus = new TextField();
        txtStatus.setPromptText("Status da consulta");
        txtObservacoes = new TextField();
        txtObservacoes.setPromptText("Alguma Observação");

        tableConsulta = new TableView<>(); //criando a tabela onde vai mostrar as consultas do banco
        //colunas da tabela só com os mais importantes
        TableColumn<Consulta, String> colId = new TableColumn<>("ID"); //criando uma coluna chamada id
        //aqui é pra dizer qual informação vai aparecer em cada coluna
        colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getId_consulta())));
        colId.setPrefWidth(50); //definindo a largura da coluna
        TableColumn<Consulta, String> colPaciente = new TableColumn<>("Paciente");
        colPaciente.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getId_paciente())));
        colPaciente.setPrefWidth(80);
        TableColumn<Consulta, String> colCRM = new TableColumn<>("CRM do Médico");
        colCRM.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getCrm_do_medico())));
        colCRM.setPrefWidth(120);
        TableColumn<Consulta, String> colData = new TableColumn<>("Data e Hora da Consulta");
        colData.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getData_e_hora())));
        colData.setPrefWidth(150);
        TableColumn<Consulta, String> colStatus = new TableColumn<>("Status da Consulta");
        colStatus.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getStatus_da_consulta())));
        colStatus.setPrefWidth(100);

        //adicionando as colunas dentro da tabela
        tableConsulta.getColumns().addAll(colId, colPaciente, colCRM, colData, colStatus);
        tableConsulta.setPrefHeight(250); //definin a atura da tabela

        //essa parte é pra ela fazer o preenchimento automatico quando o usuario clicar em uma linha da atabela
        tableConsulta.setOnMouseClicked(e -> {
            Consulta selecionada = tableConsulta.getSelectionModel().getSelectedItem();
            if (selecionada != null){
                txtIdConsulta.setText(String.valueOf(selecionada.getId_consulta()));
                txtIdPaciente.setText(String.valueOf(selecionada.getId_paciente()));
                txtCRM.setText(String.valueOf(selecionada.getCrm_do_medico()));
                txtIdSetor.setText(String.valueOf(selecionada.getId_setor()));
                txtDataHora.setText(String.valueOf(selecionada.getData_e_hora()));
                txtValor.setText(String.valueOf(selecionada.getValor_da_consulta()));
                txtTipo.setText(String.valueOf(selecionada.getTipo_de_urgencia()));
                txtStatus.setText(String.valueOf(selecionada.getStatus_da_consulta()));
                txtObservacoes.setText(String.valueOf(selecionada.getObservaçoes_sobre_consulta()));
            }
        });

        //definição dos botoes de controle do crud
        Button btnAdd = new Button("Salvar"); //aqui chama o metodo insert pra salvar a nova consulta
        Button btnEdit = new Button("Editar"); //chama o metodo update quando clicar em uma tabela e alterar alguma coisa nela
        Button btnDel = new Button("Deletar"); //chama o metodo delete pra deletar uma consulta

        btnAdd.setOnAction(e -> { //aqui é pra quando clicar no botao salvar vai executar tudo que tem dentro dele
            try {
               Consulta consulta = new Consulta();
               consulta.setId_paciente(Integer.parseInt(txtIdPaciente.getText())); //pega o que foi digitado no txt e converte pra numero inteiro
               consulta.setCrm_do_medico(txtCRM.getText());
               consulta.setId_setor(Integer.parseInt(txtIdSetor.getText()));
               consulta.setData_e_hora(LocalDateTime.parse(txtDataHora.getText()));
               consulta.setValor_da_consulta(Double.parseDouble(txtValor.getText()));
               consulta.setTipo_de_urgencia(txtTipo.getText());
               consulta.setStatus_da_consulta(txtStatus.getText());
               consulta.setObservaçoes_sobre_consulta(txtObservacoes.getText());
               consultaDao.insert(consulta); //dps do objeto preenchido chama o dao
               lblStatusGlobal.setText("Statu: Consulta adicionada!"); //se der certo envia esse msg
               txtIdPaciente.clear(); //limpando os campos
               txtCRM.clear();
               txtIdSetor.clear();
               txtDataHora.clear();
               txtValor.clear();
               txtStatus.clear();
               txtObservacoes.clear();
            } catch (Exception ex) {
                lblStatusGlobal.setText("Erro: " + ex.getMessage());
            }
        });

        btnEdit.setOnAction(e -> {
            try {
                Consulta consulta = new Consulta();
                consulta.setId_consulta(Integer.parseInt(txtIdConsulta.getText()));
                consulta.setId_paciente(Integer.parseInt(txtIdPaciente.getText())); //pega o que foi digitado no txt e converte pra numero inteiro
                consulta.setCrm_do_medico(txtCRM.getText());
                consulta.setId_setor(Integer.parseInt(txtIdSetor.getText()));
                consulta.setData_e_hora(LocalDateTime.parse(txtDataHora.getText()));
                consulta.setValor_da_consulta(Double.parseDouble(txtValor.getText()));
                consulta.setTipo_de_urgencia(txtTipo.getText());
                consulta.setStatus_da_consulta(txtStatus.getText());
                consulta.setObservaçoes_sobre_consulta(txtObservacoes.getText());

                consultaDao.update(consulta);
                lblStatusGlobal.setText("Status: Consulta atualizada!");
            } catch (Exception ex) {
                lblStatusGlobal.setText("Erro: " + ex.getMessage());
            }
        });

        btnDel.setOnAction(e -> {
            try {
                consultaDao.deleteById(Integer.parseInt(txtIdConsulta.getText())); //pega o id digitado da tabela e chama o delete
                lblStatusGlobal.setText("Statu: Consulta removida!"); //se der certo envia esse msg
                txtIdConsulta.clear(); //limpando os campos
                txtIdPaciente.clear();
                txtCRM.clear();
                txtIdSetor.clear();
                txtDataHora.clear();
                txtValor.clear();
                txtStatus.clear();
                txtObservacoes.clear();
            } catch (Exception ex) {
                lblStatusGlobal.setText("Erro: " + ex.getMessage());
            }
        });

        HBox botoes = new HBox(10,btnAdd,btnEdit,btnDel);
        return new VBox(8, //o 8 é o espaço entre cada um
                new Label("ID Consulta:"),txtIdConsulta,
                new Label("ID Paciente:"),txtIdPaciente,
                new Label("CRM do Médico:"),txtCRM,
                new Label("ID Setor:"),txtIdSetor,
                new Label("Data e Hora:"),txtDataHora,
                new Label("Valor:"),txtValor,
                new Label("Tipo de Urgência:"),txtTipo,
                new Label("Status:"),txtStatus,
                new Label("Observações:"),txtObservacoes,
                botoes,tableConsulta);
    }

    private VBox criarPainelPacientes() {
        return new VBox();
    }

    private VBox criarPainelUsuario() {
        return new VBox();
    }

    private VBox criarPainelExames() {

        txtIdExame = new TextField();
        txtIdExame.setPromptText("ID do exame");
        txtIdExame.setEditable(false);
        txtIdExame.setStyle("-fx-background-color: #e8e8e8;");
        txtIdConsultaExame = new TextField();
        txtIdConsultaExame.setPromptText("ID da Consulta ");
        txtTipoExame = new TextField();
        txtTipoExame.setPromptText("Tipo de Exame ");
        txtDataExame = new TextField();
        txtDataExame.setPromptText("Data do Exame ");
        txtResultado = new TextField();
        txtResultado.setPromptText("Resultado do Exame");
        txtStatusExame = new TextField();
        txtStatusExame.setPromptText("Status do Exame");
        txtValorExame = new TextField();
        txtValorExame.setPromptText("Valor do Exame");

        tableExame = new TableView<>();

        TableColumn<Exame, String> colId= new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIdExame())));
        colId.setPrefWidth(70);

        TableColumn<Exame, String> colConsulta = new TableColumn<>("Consulta");
        colConsulta.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIdConsulta())));
        colConsulta.setPrefWidth(100);

        TableColumn<Exame, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoExame()));
        colTipo.setPrefWidth(130);

        TableColumn<Exame, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDataExame()));
        colData.setPrefWidth(130);

        TableColumn<Exame, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        colStatus.setPrefWidth(100);

        tableExame.getColumns().addAll(colId, colConsulta,colTipo,colData,colStatus);
        tableExame.setPrefHeight(250);

        tableExame.setOnMouseClicked(e ->{ Exame exame = tableExame.getSelectionModel().getSelectedItem();
        if(exame != null ){
            txtIdExame.setText(String.valueOf(exame.getIdExame()));
            txtIdConsultaExame.setText(String.valueOf(exame.getIdConsulta()));
            txtTipoExame.setText(exame.getTipoExame());
            txtDataExame.setText(exame.getDataExame());
            txtResultado.setText(exame.getResultado());
            txtStatusExame.setText(exame.getStatus());
            txtValorExame.setText(String.valueOf(exame.getValor()));
        }
        });

        Button btnAdd = new Button("Salvar");
        Button btnEdit = new Button("Editar");
        Button btnDel = new Button("Deletar");

        btnAdd.setOnAction(e -> {
            try {
                Exame exame = new Exame();
                exame.setIdConsulta(Integer.parseInt(txtIdConsultaExame.getText()));
                exame.setTipoExame(txtTipoExame.getText());
                exame.setDataExame(txtDataExame.getText());
                exame.setResultado(txtResultado.getText());
                exame.setStatus(txtStatusExame.getText());
                exame.setValor(Double.parseDouble(txtValorExame.getText()));
                exameDao.insert(exame);

                lblStatusGlobal.setText("Status: Exame adicionado");
                txtIdConsultaExame.clear();
                txtTipoExame.clear();
                txtDataExame.clear();
                txtResultado.clear();
                txtValorExame.clear();
            }catch (Exception ex){
                lblStatusGlobal.setText("Erro" + ex.getMessage());
            }
        });

        btnEdit.setOnAction(e -> {
            try{
                Exame exame = new Exame();
                exame.setIdExame(Integer.parseInt(txtIdExame.getText()));
                exame.setIdConsulta(Integer.parseInt(txtIdConsultaExame.getText()));
                exame.setTipoExame(txtTipoExame.getText());
                exame.setDataExame(txtDataExame.getText());
                exame.setResultado(txtResultado.getText());
                exame.setStatus(txtStatusExame.getText());
                exame.setValor(Double.parseDouble(txtValorExame.getText()));
                exameDao.update(exame);
                lblStatusGlobal.setText("Status: Exame atualizado");
            }catch (Exception ex){
                lblStatusGlobal.setText("Erro" + ex.getMessage());
            }
        });

        btnDel.setOnAction(e -> {
            try {
                exameDao.deleteById(Integer.parseInt(txtIdExame.getText()));
                lblStatusGlobal.setText("Status: Exame removido");
                txtIdExame.clear();
                txtIdConsultaExame.clear();
                txtTipoExame.clear();
                txtResultado.clear();
                txtStatusExame.clear();
                txtValorExame.clear();
            }catch (Exception ex){
                lblStatusGlobal.setText("Erro" + ex.getMessage());
            }
        });

        HBox botoes = new HBox(10,btnAdd,btnEdit,btnDel);
        return new VBox(8,
                new Label("ID Exame "),txtIdExame,
                new Label("ID Consulta "), txtIdConsultaExame,
                new Label("Tipo do Exame"), txtTipoExame,
                new Label("Data do Exame "), txtDataExame,
                new Label("Resultado "), txtResultado,
                new Label("Status "), txtStatusExame,
                new Label("Valor "), txtValorExame,
                botoes,
                tableExame);
    }

    private void atualizarListas(){

    }

    public static void main(String[] args) {
        launch(args);
    }
}


