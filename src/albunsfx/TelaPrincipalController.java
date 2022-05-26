package albunsfx;

import albunsfx.db.util.Banco;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;

public class TelaPrincipalController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    

    @FXML
    private void clkFechar(ActionEvent event) {
        // perguntar antes
        Platform.exit();
    }

    @FXML
    private void clkCadArtista(ActionEvent event) throws IOException {
         //executar a janela de cadastro
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CadArtista.fxml")));
        stage.setScene(scene);
        stage.setTitle("Artistas");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        stage.showAndWait();
    }
    

    @FXML
    private void clkCadGenero(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CadGenero.fxml")));
        stage.setScene(scene);
        stage.setTitle("Generos");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        stage.showAndWait();
    }

    @FXML
    private void clkCadMusica(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CadMus.fxml")));
        stage.setScene(scene);
        stage.setTitle("Musica");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        stage.showAndWait();
    }

    @FXML
    private void clkCadTipoArtista(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CadTipoArtista.fxml")));
        stage.setScene(scene);
        stage.setTitle("TipoArtista");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        stage.showAndWait();        
    }

    @FXML
    private void clkCadAlbum(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CadAlbum.fxml")));
        stage.setScene(scene);
        stage.setTitle("Album");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        stage.showAndWait();    
    }

    @FXML
    private void clkCadPesqAlbum(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("PesAlbum.fxml")));
        stage.setScene(scene);
        stage.setTitle("Pesquisar album");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        stage.showAndWait();    
    }

    @FXML
    private void clkRelMusicas(ActionEvent event) {
        String sql = "select * from musica,artista where musica.art_id = artista.art_id order by musica.mus_titulo";
        // string sql ordenado pelo nome
        gerarRelatorio(sql, "Relatorios/Relatorio_Musicas.jasper");
    }

    @FXML
    private void clkRelMusicasAlbum(ActionEvent event) {
        String sql = "select album.al_id, album.al_titulo, album.al_ano, musica.mus_id, musica.mus_titulo, musica.mus_duracao from album, musica, album_musica where album_musica.al_id=album.al_id and album_musica.mus_id=musica.mus_id order by al_titulo";
        gerarRelatorio(sql, "Relatorios/Relatorio_MusicasAlbum.jasper");
    }
    
    private void gerarRelatorio(String sql,String relat){
        try
        { //sql para obter os dados para o relatorio
          ResultSet rs = Banco.getConexao().consultar(sql);
          //implementação da interface JRDataSource para DataSource ResultSet
          JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
          //chamando o relatório
          String jasperPrint = JasperFillManager.fillReportToFile(relat,null, jrRS);
          JasperViewer viewer = new JasperViewer(jasperPrint, false, false);
          viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);//maximizado
          viewer.setTitle("Relatório");//titulo do relatório
          viewer.setVisible(true);
        } catch (JRException erro)
        {  erro.printStackTrace(); }
   }
}
