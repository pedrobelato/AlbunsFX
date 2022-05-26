package albunsfx.db.entidade;

import javafx.scene.control.SpinnerValueFactory;

public class Album {
    private int id, ano, rating;
    private String titulo, descricao;
    private Genero genero;
    private Artista artista;

    public Album(){
        this(0,0,0,"","",null,null);
    }
    
    public Album(int id, int ano, int rating, String titulo, String descricao, Genero genero, Artista artista) {
        this.id = id;
        this.ano = ano;
        this.rating = rating;
        this.titulo = titulo;
        this.descricao = descricao;
        this.genero = genero;
        this.artista = artista;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }
    
    
}
