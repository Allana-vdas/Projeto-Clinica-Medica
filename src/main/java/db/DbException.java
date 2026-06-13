package db;

//classe de exeção personalizada para erros gerais de banco de dados
//classe pra lançar quando acontecer algum erro
public class DbException extends RuntimeException {
    private static final long seriaVersionUID = 1l;
    //essa linha é usada pela java quando objetos podem ser serializados
    //não vamos usar coloquei pq tem no exemplo de Wild

    //construtor da exeção, que vai receber uma mensagem dizendo o erro
    public DbException(String msg){
        super(msg); //envia a mensagem para Runtime
    }
}
