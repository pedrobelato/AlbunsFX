package albunsfx.db.entidade;

import java.time.LocalDate;

public class Artista 
{
    private int id;
    private String nome;
    private String origem;
    private LocalDate dtnasc;
    private TipoArtista tipoartista;

    public Artista() {
        this(0,"","",LocalDate.now(),null);
    }

    public Artista(int id, String nome, String origem, LocalDate dtnasc, TipoArtista tipoartista) {
        this.id = id;
        this.nome = nome;
        this.origem = origem;
        this.dtnasc = dtnasc;
        this.tipoartista = tipoartista;
    }

    public Artista(String nome, String origem, LocalDate dtnasc, TipoArtista tipoartista) {
        this(0,nome,origem,dtnasc,tipoartista);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public LocalDate getDtnasc() {
        return dtnasc;
    }

    public void setDtnasc(LocalDate dtnasc) {
        this.dtnasc = dtnasc;
    }

    public TipoArtista getTipoartista() {
        return tipoartista;
    }

    public void setTipoartista(TipoArtista tipoartista) {
        this.tipoartista = tipoartista;
    }
    

    @Override
    public String toString() {
        return nome; 
    }
}
