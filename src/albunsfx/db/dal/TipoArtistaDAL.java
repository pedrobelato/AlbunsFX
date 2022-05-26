package albunsfx.db.dal;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import albunsfx.db.entidade.TipoArtista;
import albunsfx.db.util.Banco;


public class TipoArtistaDAL 
{
    public boolean salvar(TipoArtista ta)
    {
        String sql="insert into tipoartista (ta_id,ta_nome) values (default,'#1')";
        sql=sql.replace("#1", ta.getNome());
        return Banco.getConexao().manipular(sql);
    }
    public boolean alterar(TipoArtista ta)
    {
        String sql="update musica set ta_nome='#1' where ta_id="+ta.getId();
        sql=sql.replace("#1", ta.getNome());
        return Banco.getConexao().manipular(sql);
    }
    public boolean apagar(TipoArtista m)
    {
        return Banco.getConexao().manipular("delete from musica where mus_id="+m.getId());
    }
    public TipoArtista get(int id)
    {   TipoArtista ta=null;
        String sql="select * from tipoartista where ta_id="+id;
        ResultSet rs=Banco.getConexao().consultar(sql);
        try{
        if(rs.next())
            ta=new TipoArtista(rs.getInt("ta_id"),rs.getString("ta_nome"));
        }catch(Exception e){};
        return ta;
    }
    public List get(String filtro)
    {   String sql="select * from tipoartista";
        if (!filtro.isEmpty())
            sql+=" where "+filtro;
        List<TipoArtista> list = new ArrayList();
        ResultSet rs=Banco.getConexao().consultar(sql);
        try{
           while(rs.next())
               list.add(new TipoArtista(rs.getInt("ta_id"),rs.getString("ta_nome")));
        }catch(Exception e){};
        return list;
    }
}
