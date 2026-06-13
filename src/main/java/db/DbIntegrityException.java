package db;

//clesse de erros integridades do banco, tipo pra quando for excluir um registro que ja ta sendo usado por outra tabela
public class DbIntegrityException extends RuntimeException{
    private static final long serialVersionUID = 1;

    public DbIntegrityException(String msg){
        super(msg);
    }
}
