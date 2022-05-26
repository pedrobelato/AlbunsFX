package albunsfx.db.entidade;

public class TipoArtista 
{
    private int id;
    private String nome;

    public TipoArtista() {
        this(0,"");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public TipoArtista(String nome) {
        this(0,nome);
    }
    
    public TipoArtista(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nome; 
    }
}
