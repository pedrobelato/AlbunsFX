package albunsfx.db.dal;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import albunsfx.db.entidade.Artista;
import albunsfx.db.util.Banco;
import java.io.InputStream;


public class ArtistaDAL 
{
//    public boolean salvar(Artista a)
//    {
//        String sql="insert into artista (art_id,art_nome,art_origem,art_dtnasc, ta_id) values (default,'#1','#2','#3',#4)";
//        sql=sql.replace("#1", a.getNome());
//        sql=sql.replace("#2", ""+a.getOrigem());
//        sql=sql.replace("#3", a.getDtnasc().toString());
//        sql=sql.replace("#4", ""+a.getTipoartista().getId());
//        return Banco.getConexao().manipular(sql);
//    }
    public boolean salvar(Artista a, InputStream is)
    {
        String sql="insert into artista (art_id,art_nome,art_origem,art_dtnasc,ta_id, art_foto) values (default,'#1','#2','#3','#4',?)";
        sql=sql.replace("#1", a.getNome());
        sql=sql.replace("#2", ""+a.getOrigem());
        sql=sql.replace("#3", a.getDtnasc().toString());
        sql=sql.replace("#4", ""+a.getTipoartista().getId());
        return Banco.getConexao().gravarAlterarComFoto(sql,is);
    }
    
    public InputStream getFoto(int id)
    {
        return Banco.getConexao().carregarFoto("select art_foto from artista where art_id="+id);
    }
    
    public boolean alterar(Artista a, InputStream is)
    {
        String sql="update artista set art_nome='#1',art_origem='#2',art_dtnasc='#3', ta_id=#4, art_foto=? where art_id="+a.getId();
        sql=sql.replace("#1", a.getNome());
        sql=sql.replace("#2", ""+a.getOrigem());
        sql=sql.replace("#3", a.getDtnasc().toString());
        sql=sql.replace("#4", ""+a.getTipoartista().getId());
        return Banco.getConexao().gravarAlterarComFoto(sql,is);
    }
    public boolean apagar(Artista a)
    {
        return Banco.getConexao().manipular("delete from artista where art_id="+a.getId());
    }
    public Artista get(int id)
    {   
        Artista a=null;
        String sql="select * from artista where art_id="+id;
        ResultSet rs=Banco.getConexao().consultar(sql);
        TipoArtistaDAL tadal=new TipoArtistaDAL();
        try{
        if(rs.next())
            a=new Artista(rs.getInt("art_id"),rs.getString("art_nome"),rs.getString("art_origem"),
                    rs.getDate("art_dtnasc").toLocalDate(),tadal.get(rs.getInt("ta_id")));
        }catch(Exception e){};
        return a;
    }
    public List get(String filtro)
    {   String sql="select * from artista";
        if (!filtro.isEmpty())
            sql+=" where "+filtro;
        List<Artista> list = new ArrayList();
        TipoArtistaDAL tadal=new TipoArtistaDAL();
        ResultSet rs=Banco.getConexao().consultar(sql);
        try{
           while(rs.next())
               list.add(new Artista(rs.getInt("art_id"),rs.getString("art_nome"),rs.getString("art_origem"),
                    rs.getDate("art_dtnasc").toLocalDate(),tadal.get(rs.getInt("ta_id"))));
        }catch(Exception e){};
        return list;
    }
}
