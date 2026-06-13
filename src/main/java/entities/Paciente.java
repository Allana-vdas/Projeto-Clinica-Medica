package entities;

public class Paciente {

    private int idPaciente;


    private String nome;
    private String cpf;
    private String rg;
    private String telefone;
    private String endereco;
    private String email;
    private String dataNascimento;
    private String contatoEmergencia;


    private int idEndereco;
    private int idPlano;

    public Paciente(){
    }


    public Paciente(String nome,
                    String cpf,
                    String rg,
                    String telefone,
                    String endereco,
                    String email,
                    String dataNascimento,
                    String contatoEmergencia,
                    int idEndereco,
                    int idPlano){

        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.contatoEmergencia = contatoEmergencia;
        this.idEndereco = idEndereco;
        this.idPlano = idPlano;

    }

    public int getIdPaciente(){
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente){
        this.idPaciente = idPaciente;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getContatoEmergencia() {
        return contatoEmergencia;
    }

    public void setContatoEmergencia(String contatoEmergencia) {
        this.contatoEmergencia = contatoEmergencia;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public int getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(int idPlano) {
        this.idPlano = idPlano;
    }
}
