package albunsfx.db.entidade;

public class Musica {
    
    private String url;
    private Artista artista;
    private Genero genero;
    private int duracao;
    private String titulo;
    private int id;
    
    public Musica(){       
    }
    
    public Musica(String url, Artista artista, Genero genero, int duracao, String titulo){
        this(url,artista,genero,duracao,titulo,0);
    }
    
    public Musica(String url, Artista artista, Genero genero, int duracao, String titulo, int id) {
        this.url = url;
        this.artista = artista;
        this.genero = genero;
        this.duracao = duracao;
        this.titulo = titulo;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return titulo;
    }
}
