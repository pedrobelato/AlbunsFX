package albunsfx.db.dal;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import albunsfx.db.entidade.Musica;
import albunsfx.db.util.Banco;

public class musicaDAL {
    
    public boolean salvar(Musica m){
        String sql = "insert into musica(mus_id, mus_titulo, mus_duracao, mus_url, art_id, gen_id) values(default,'#1',#2,'#3',#4,#5)";
        sql = sql.replace("#1", m.getTitulo());
        sql = sql.replace("#2", ""+m.getDuracao());
        sql = sql.replace("#3", m.getUrl());
        sql = sql.replace("#4", ""+m.getArtista().getId());
        sql = sql.replace("#5", ""+m.getGenero().getId());
        return Banco.getConexao().manipular(sql);
    }
    
    public boolean alterar(Musica m){
        String sql = "update musica set mus_titulo='#1', mus_duracao=#2, mus_url='#3', art_id=#4, gen_id=#5 where mus_id="+m.getId();
        sql = sql.replace("#1", m.getTitulo());
        sql = sql.replace("#2", ""+m.getDuracao());
        sql = sql.replace("#3", m.getUrl());
        sql = sql.replace("#4", ""+m.getArtista().getId());
        sql = sql.replace("#5", ""+m.getGenero().getId());
        return Banco.getConexao().manipular(sql);
    }
    
    public boolean apagar(Musica m){
        return Banco.getConexao().manipular("delete from musica where mus_id="+m.getId());
    }
    
    public Musica get(int id){
        Musica m = null;
        String sql = "select * from musica where mus_id=" + id;
        ResultSet rs = Banco.getConexao().consultar(sql);
        ArtistaDAL adal= new ArtistaDAL();
        GeneroDAL gdal = new GeneroDAL();
        try{
            if(rs.next())
                    m = new Musica(rs.getString("mus_titulo"), adal.get(rs.getInt("art_id")), gdal.get(rs.getInt("gen_id")), 
                                   rs.getInt("mus_duracao"), rs.getString("mus_titulo"), rs.getInt("mus_id"));
        }catch(Exception e){};
        return m;     
    }
    
    public List get(String filtro){
        String sql = "Select * from musica";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
       List<Musica> list = new ArrayList();
       ArtistaDAL adal= new ArtistaDAL();
       GeneroDAL gdal = new GeneroDAL();
       ResultSet rs = Banco.getConexao().consultar(sql);
       try{
           while(rs.next())
               list.add(new Musica(rs.getString("mus_titulo"), adal.get(rs.getInt("art_id")), gdal.get(rs.getInt("gen_id")), 
                                   rs.getInt("mus_duracao"), rs.getString("mus_titulo"), rs.getInt("mus_id")));
       }
       catch(Exception e){};
       return list;
    }
}