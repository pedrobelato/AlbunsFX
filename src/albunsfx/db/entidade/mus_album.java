package albunsfx.db.entidade;

public class mus_album {
    private Musica musica;
    private Album album;

    public mus_album(){
        this(null,null);
    }
    
    public mus_album(Musica musica, Album album) {
        this.musica = musica;
        this.album = album;
    }

    public Musica getMusica() {
        return musica;
    }

    public void setMusica(Musica musica) {
        this.musica = musica;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
    
    
    
}
