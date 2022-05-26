package albunsfx.db.dal;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import albunsfx.db.entidade.Genero;
import albunsfx.db.util.Banco;

public class GeneroDAL {
    
    public boolean salvar(Genero g)
    {
        String sql="insert into genero (gen_id,gen_nome) values (default,'#1')";
        sql=sql.replace("#1", g.getNome());
        return Banco.getConexao().manipular(sql);
    }
    
    public boolean alterar(Genero g)
    {
        String sql="update genero set gen_nome='#1' where gen_id="+g.getId();
        sql=sql.replace("#1", g.getNome());
        return Banco.getConexao().manipular(sql);
    }
    
    public boolean apagar(Genero g)
    {
        return Banco.getConexao().manipular("delete from genero where gen_id="+g.getId());
    }
    
    public Genero get(int id)
    {   Genero g=null;
        String sql="select * from genero where gen_id="+id;
        ResultSet rs=Banco.getConexao().consultar(sql);
        try{
        if(rs.next())
            g=new Genero(rs.getInt("gen_id"),rs.getString("gen_nome"));
        }catch(Exception e){};
        return g;
    }
    
    public List get(String filtro)
    {   String sql="select * from genero";
        if (!filtro.isEmpty())
            sql+=" where "+filtro;
        List<Genero> list = new ArrayList();
        ResultSet rs=Banco.getConexao().consultar(sql);
        try{
           while(rs.next())
               list.add(new Genero(rs.getInt("gen_id"),rs.getString("gen_nome")));
        }
        catch(Exception e){};
        return list;
    }
}

