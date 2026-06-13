package entities;

public class Usuario {
    private int idUsuario;

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String cargo;
    private String setor;
    private String nivelAcesso;
    private String senha;


    public Usuario(){

    }


    public Usuario(
            String nome,
            String cpf,
            String email,
            String telefone,
            String cargo,
            String setor,
            String nivelAcesso,
            String senha) {

        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.cargo = cargo;
        this.setor = setor;
        this.nivelAcesso = nivelAcesso;
        this.senha = senha;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getcpf(){
        return cpf;
    }
    public void setCpf(String cpf){
        this.cpf =cpf;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this. email =email;
    }
    public String getTelefone(){
    return telefone;
    }
    public void setTelefone(String telefone){
        this.telefone =telefone;
    }
    public String getCargo(){
        return cargo;
    }
    public void setCargo(String cargo){
        this.cargo = cargo;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }
    public void setnivelAcesso (String nivelAcesso){
        this.nivelAcesso =nivelAcesso;
    }
    public String getSenha(){
        return senha;
    }
    public void setSenha(String senha){

        this.senha =senha;
    }

}



