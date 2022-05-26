package albunsfx.db.util;

public class Banco  //Singleton 
{
    private static Conexao con;

    private Banco() {   }
    
    public static boolean conectar()
    {
        con=new Conexao();
        return con.conectar("jdbc:postgresql://localhost/", "myalbuns", "postgres", "postgres123");
    }
    public static Conexao getConexao()
    {
        return con;
    }
}






/*
postgres://ugvnczwk:k3HzMJ5IQFjnt1mEGIdwNjkCpAYRZ3af@motty.db.elephantsql.com:5432/ugvnczwk
pass: k3HzMJ5IQFjnt1mEGIdwNjkCpAYRZ3af

*/