package albunsfx.db.dal;

import albunsfx.db.entidade.Musica;
import albunsfx.db.entidade.mus_album;
import albunsfx.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class mus_albumDAL {
    
    public boolean salvar(int alid, int musid){
        String sql = "insert into album_musica(al_id, mus_id) values(#1,#2)";
        sql = sql.replace("#1", ""+alid);
        sql = sql.replace("#2", ""+musid);
        return Banco.getConexao().manipular(sql);
    }
    
    public boolean alterar(int alid, int musid){
        String sql = "update album_musica set mus_id=#1 where al_id="+alid;
        sql = sql.replace("#1", ""+musid);
        return Banco.getConexao().manipular(sql);
    }
    
    public boolean apagar(int alid,int musid){
        return Banco.getConexao().manipular("delete from album_musica where al_id="+alid+"and mus_id="+musid);
    }
    
    public mus_album get(int alid, int musid){
        mus_album ma = null;
        String sql = "select * from album_musica where al_id="+alid+"and mus_id="+musid;
        ResultSet rs = Banco.getConexao().consultar(sql);
        musicaDAL mdal = new musicaDAL();
        AlbumDAL adal = new AlbumDAL();
        try{
            if(rs.next())
                ma=new mus_album(mdal.get(rs.getInt("mus_id")), adal.get(rs.getInt("al_id")));
        }
        catch(Exception e){};
        return ma;
    }
    
    public List get(int id){
        String sql = "Select * from album_musica where al_id="+id;
        List<Musica> list = new ArrayList();
        musicaDAL mdal = new musicaDAL();
        ResultSet rs = Banco.getConexao().consultar(sql);
        try{
            while(rs.next())
                list.add(mdal.get(rs.getInt("mus_id")));
        }
        catch(Exception e){};
        return list;
    }
    
}
