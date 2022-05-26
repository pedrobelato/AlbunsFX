package albunsfx;

import albunsfx.db.dal.AlbumDAL;
import albunsfx.db.entidade.Album;
import albunsfx.db.entidade.Artista;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PesAlbumController implements Initializable {

    @FXML
    private JFXTextField txbusca;
    @FXML
    private Button btbuscar;
    @FXML
    private TableView<Album> tabela;
    @FXML
    private TableColumn<Album, Integer> colid;
    @FXML
    private TableColumn<Album, String> coltitulo;
    @FXML
    private TableColumn<Album, Integer> colartista;
    @FXML
    private TableColumn<Album, Integer> colrating;
    @FXML
    private Button btconfirmar;
    @FXML
    private Button btcancelar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colid.setCellValueFactory(new PropertyValueFactory("id"));
        colartista.setCellValueFactory(new PropertyValueFactory("artista"));
        coltitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        colrating.setCellValueFactory(new PropertyValueFactory("rating"));
        
        carregaTabela("");
    }    
    
    private void carregaTabela(String filtro){
        AlbumDAL adal = new AlbumDAL();
        List<Album> res = adal.get(filtro);
        ObservableList<Album> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    @FXML
    private void clkBtBuscar(ActionEvent event) {
        carregaTabela("upper(al_titulo) like '%"+txbusca.getText().toUpperCase()+"%'");
    }

    @FXML
    private void clkBtConfirmar(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(getClass().getResource("CadAlbum.fxml"));
        Parent root = loader.load();
        
        CadAlbumController controller = loader.getController();
        controller.setAtributo(tabela.getSelectionModel().getSelectedItem());
        
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Album");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        
    }

    @FXML
    private void clkBtCancelar(ActionEvent event) {
        btconfirmar.setDisable(true);
        btcancelar.setDisable(true);
        carregaTabela("");
    }
    
}
