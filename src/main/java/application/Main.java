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
import java.util.List;

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
                txtTipo.clear();
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

        txtPacienteId = new TextField();
        txtPacienteId.setPromptText("ID do Paciente");
        txtPacienteId.setEditable(false);
        txtPacienteId.setStyle("-fx-background-color: #e8e8e8;");

        txtNomePaciente = new TextField();
        txtNomePaciente.setPromptText("Nome");

        txtCpfPaciente = new TextField();
        txtCpfPaciente.setPromptText("CPF");

        txtRgPaciente = new TextField();
        txtRgPaciente.setPromptText("RG");

        txtNomeTelefonePaciente = new TextField();
        txtNomeTelefonePaciente.setPromptText("Telefone");

        txtNomeEnderecoPaciente = new TextField();
        txtNomeEnderecoPaciente.setPromptText("Endereco");

        txtEmailPaciente = new TextField();
        txtEmailPaciente.setPromptText("Email");

        txtDataNascimentoPaciente = new TextField();
        txtDataNascimentoPaciente.setPromptText("Data de Nascimento");

        txtContatoEmergenciaPaciente = new TextField();
        txtContatoEmergenciaPaciente.setPromptText("Contato de Emergencia");

        txtIdEnderecoPaciente = new TextField();
        txtIdEnderecoPaciente.setPromptText("ID Endereco");

        txtIdPlanoPaciente = new TextField();
        txtIdPlanoPaciente.setPromptText("ID Plano");


        tablePaciente = new TableView<>();

        TableColumn<Paciente, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        String.valueOf(cellData.getValue().getIdPaciente())));
        colId.setPrefWidth(50);

        TableColumn<Paciente, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getNome()));
        colNome.setPrefWidth(180);


        TableColumn<Paciente, String> colCpf= new TableColumn<>("CPF");
        colCpf.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getCpf()));
        colCpf.setPrefWidth(150);

        TableColumn<Paciente, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getTelefone()));
        colTelefone.setPrefWidth(120);


        tablePaciente.getColumns().addAll(colId, colNome, colCpf, colTelefone);
        tablePaciente.setPrefHeight(250);

        tablePaciente.setOnMouseClicked(e -> {

            Paciente paciente = tablePaciente.getSelectionModel().getSelectedItem();

            if (paciente != null) {

                txtPacienteId.setText(String.valueOf(paciente.getIdPaciente()));
                txtNomePaciente.setText(paciente.getNome());
                txtCpfPaciente.setText(paciente.getCpf());
                txtRgPaciente.setText(paciente.getRg());
                txtNomeTelefonePaciente.setText(paciente.getTelefone());
                txtNomeEnderecoPaciente.setText(paciente.getEndereco());
                txtEmailPaciente.setText(paciente.getEmail());
                txtDataNascimentoPaciente.setText(paciente.getDataNascimento());
                txtContatoEmergenciaPaciente.setText(paciente.getContatoEmergencia());
                txtIdEnderecoPaciente.setText(String.valueOf(paciente.getIdEndereco()));
                txtIdPlanoPaciente.setText(String.valueOf(paciente.getIdPlano()));
            }

        });

        Button btnAdd = new Button("Salvar");
        Button btnEdit = new Button("Editar");
        Button btnDelete = new Button("Excluir");

        HBox botoes = new HBox(10, btnAdd, btnEdit, btnDelete);

        return new VBox(8,

                new Label("ID"), txtPacienteId,
                new Label("Nome"), txtNomePaciente,
                new Label("CPF"), txtCpfPaciente,
                new Label("RG"), txtRgPaciente,
                new Label("Telefone"), txtNomeTelefonePaciente,
                new Label("Endereco"), txtNomeEnderecoPaciente,
                new Label("Email"), txtEmailPaciente,
                new Label("Data de Nascimento"), txtDataNascimentoPaciente,
                new Label("Contato de Emergencia"), txtContatoEmergenciaPaciente,
                new Label("ID Endereco"), txtIdEnderecoPaciente,
                new Label("ID Plano"), txtIdPlanoPaciente,

                botoes,
                tablePaciente);


    }

    private VBox criarPainelUsuario() {
// criar os campos do texto
        txtIdUsuario = new TextField();
        txtIdUsuario.setPromptText("ID do usuário");
        txtIdUsuario.setEditable(false);
        txtIdUsuario.setStyle("-fx-background-color: #e8e8e8;");

        txtNome= new TextField();
        txtNome.setPromptText("Nome");

        txtTelefone = new TextField();
        txtTelefone.setPromptText("Telefone");

        txtCpf = new TextField();
        txtCpf.setPromptText("Cpf");

        txtEmail= new TextField();
        txtEmail.setPromptText("Email");

        txtCargo = new TextField();
        txtCargo.setPromptText("Cargo");

        txtSetor= new TextField();
        txtSetor.setPromptText("Setor");

        txtNivelAcesso= new TextField();
        txtNivelAcesso.setPromptText("Nivel de Acesso");

        //criar a tabela
        tableUsuario = new TableView<>();

        // criar as colunas desta tabela
        TableColumn<Usuario,String> colId = new TableColumn<>("ID");

        TableColumn<Usuario,String> colNome = new TableColumn<>("Nome");

        TableColumn<Usuario,String> colCargo= new TableColumn<>("Cargo");

        TableColumn<Usuario,String> colSetor = new TableColumn<>("Setor");

       // configuração das colunas
        colId.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(String.valueOf
                        (cellData.getValue().getIdUsuario())));

        colNome
                .setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(String.valueOf
                        (cellData.getValue().getNome())));

        colCargo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(String.valueOf
                        (cellData.getValue().getCargo())));

        colSetor.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(String.valueOf
                        (cellData.getValue().getSetor())));

        // adicionar as colunas na tabela
        tableUsuario.getColumns().addAll(colId,colNome,colCargo,colSetor);

        tableUsuario.setPrefHeight(250);


        //o clique da tabela para preencher os campos
        tableUsuario.setOnMouseClicked( e ->{
            Usuario usuario = tableUsuario.getSelectionModel().getSelectedItem();
            if (usuario != null){

                txtIdUsuario.setText(String.valueOf(usuario.getIdUsuario()));

                txtNome.setText(usuario.getNome());
                txtTelefone.setText(usuario.getTelefone());
                txtCpf.setText(usuario.getCpf());
                txtEmail.setText(usuario.getEmail());
                txtCargo.setText(usuario.getCargo());
                txtSetor.setText(usuario.getSetor());
                txtNivelAcesso.setText(usuario.getNivelAcesso());

            }
        });

        Button btnAdd = new Button("Salvar ");
        Button btnEdit = new Button("Editar ");
        Button btnDel = new Button("Deletar");

        // vamos programar o botão Salvar
         btnAdd.setOnAction( e ->{

             try {
                 Usuario usuario = new Usuario();

                 usuario.setNome((txtNome.getText()));
                 usuario.setTelefone((txtTelefone.getText()));
                 usuario.setCpf((txtCpf.getText()));
                 usuario.setEmail((txtEmail.getText()));
                 usuario.setCargo((txtCargo.getText()));
                 usuario.setSetor((txtSetor.getText()));
                 usuario.setNivelAcesso((txtNivelAcesso.getText()));

                 usuarioDao.insert(usuario);
                 atualizarListas();
                 limparCamposUsuario();
                 lblStatusGlobal.setText("Status: Usuario cadastrado!!");

             } catch (Exception ex) {
                 lblStatusGlobal.setText("Erro:" + ex.getMessage());
             }

         });

         //programar o botão Editar
        btnEdit.setOnAction(e ->{

            try{
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(
                        Integer.parseInt(txtIdUsuario.getText()));

                usuario.setNome((txtNome.getText()));
                usuario.setTelefone((txtTelefone.getText()));
                usuario.setCpf((txtCpf.getText()));
                usuario.setEmail((txtEmail.getText()));
                usuario.setCargo((txtCargo.getText()));
                usuario.setSetor((txtSetor.getText()));
                usuario.setNivelAcesso((txtNivelAcesso.getText()));

                 usuarioDao.update(usuario);

                 atualizarListas();

                 limparCamposConsulta();
                lblStatusGlobal.setText("Status: Usuario Atualizado!!");

            } catch (Exception ex) {
                lblStatusGlobal.setText("Erro: "+ ex.getMessage());
            }
        });
        // programar o botão Deletar
        btnDel.setOnAction(e ->{
            try{
                usuarioDao.deleteById(Integer.parseInt(txtIdUsuario.getText()));

                atualizarListas();

                limparCamposUsuario();

                lblStatusGlobal.setText("Status : usuário removido!");
            } catch (Exception ex) {

                lblStatusGlobal.setText("Erro:"+ex.getMessage());
            }
        });
        //criar a linha dos botões
        HBox botoes = new HBox(10 ,btnAdd,btnEdit,btnDel);

        // vamos fazer com que retorne o painel

        return new VBox(8,
                new Label("ID Usuario:"),txtIdUsuario,
                new Label("Nome:"),txtNome,
                new Label("Telefone"),txtTelefone,
                new Label("Cpf:"),txtCpf,
                new Label("Email"),txtEmail,
                new Label ("Cargo"),txtCargo,
                new Label ("Setor"),txtSetor,
                new Label ("Nivel de Acesso"), txtNivelAcesso,
                botoes,tableUsuario);

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
        List<Consulta> consultas = consultaDao.findAll(); //busca todas as consultas do banco
        tableConsulta.getItems().clear(); //limpa a tabela
        tableConsulta.getItems().addAll(consultas); //carrega de novo

        //PACIENTE

        if (tableUsuario != null) {
            List<Usuario> usuarios = usuarioDao.findAll(); //todas as consultas
            tableUsuario.getItems().clear();//limpa
            tableUsuario.getItems().addAll(usuarios);// entra de novo
        }

        //EXAME
    }

    //metodo pra limpar os campos da tela dps de salvar,editar ou deletar uma consulta
    private void limparCamposConsulta(){
        txtIdConsulta.clear();
        txtIdPaciente.clear();
        txtCRM.clear();
        txtIdSetor.clear();
        txtDataHora.clear();
        txtValor.clear();
        txtTipo.clear();
        txtStatus.clear();
        txtObservacoes.clear();
    }

    private void limparCamposPaciente(){

    }

    private void limparCamposUsuario(){
        txtIdUsuario.clear();
        txtNome.clear();
        txtTelefone.clear();
        txtCpf.clear();
        txtEmail.clear();
        txtCargo.clear();
        txtSetor.clear();
        txtNivelAcesso.clear();

    }

    private void limparCamposExame(){

    }

    public static void main(String[] args) {
        launch(args);
    }
}


