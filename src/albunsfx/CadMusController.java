package albunsfx;

import albunsfx.db.dal.ArtistaDAL;
import albunsfx.db.dal.GeneroDAL;
import albunsfx.db.dal.musicaDAL;
import albunsfx.db.entidade.Artista;
import albunsfx.db.entidade.Genero;
import albunsfx.db.entidade.Musica;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class CadMusController implements Initializable {

    @FXML
    private Button btnovo;
    @FXML
    private Button btalterar;
    @FXML
    private Button btapagar;
    @FXML
    private Button btconfirmar;
    @FXML
    private Button btcancelar;
    @FXML
    private JFXTextField txid;
    @FXML
    private JFXTextField txtitulo;
    @FXML
    private JFXTextField txduracao;
    @FXML
    private JFXTextField txurl;
    @FXML
    private JFXComboBox<Genero> cbgenero;
    @FXML
    private JFXComboBox<Artista> cbartista;
    @FXML
    private TableView<Musica> tabela;
    @FXML
    private TableColumn<Musica, Integer> colid;
    @FXML
    private TableColumn<Musica, String> coltitulo;
    @FXML
    private TableColumn<Musica, Integer> colartista;
    @FXML
    private TableColumn<Musica, Integer> colgenero;
    @FXML
    private TableColumn<Musica, String> colurl;
    @FXML
    private TableColumn<Musica, Integer> colduracao;
    @FXML
    private JFXTextField txbusca;
    @FXML
    private Button btbuscar;
    @FXML
    private AnchorPane ap;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colid.setCellValueFactory(new PropertyValueFactory("id"));
        coltitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        colartista.setCellValueFactory(new PropertyValueFactory("artista"));
        colgenero.setCellValueFactory(new PropertyValueFactory("genero"));
        colurl.setCellValueFactory(new PropertyValueFactory("url"));
        colduracao.setCellValueFactory(new PropertyValueFactory("duracao"));
        
        estadoOriginal();
    }   
    
    private void estadoOriginal(){
        ap.setDisable(true);
        
        btalterar.setDisable(true);
        btapagar.setDisable(true);
        btconfirmar.setDisable(true);
        btcancelar.setDisable(false);
        btnovo.setDisable(false);
        
        ObservableList<Node> componentes = ap.getChildren();
        for(Node n : componentes){
            if(n instanceof TextInputControl){
                ((TextInputControl) n).setText("");
            }
            if(n instanceof ComboBox){
                ((ComboBox) n).getItems().clear();
            }
        }
        
        carregaTabela("");
        carregarCombos();
    }
    
    private void estadoEdicao(){
        ap.setDisable(false);
        
        btconfirmar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        
        txtitulo.requestFocus();
    }
    
    private void carregaTabela(String filtro){
        musicaDAL  dal = new musicaDAL();
        List<Musica> res = dal.get(filtro);
        ObservableList<Musica> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    private void carregarCombos(){
        ArtistaDAL adal = new ArtistaDAL();
        GeneroDAL gdal = new GeneroDAL();
        cbartista.setItems(FXCollections.observableArrayList(adal.get("")));
        cbgenero.setItems(FXCollections.observableArrayList(gdal.get("")));    
    }
    
    @FXML
    private void clkBtNovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkBtAlterar(ActionEvent event) {
        Musica m = (Musica) tabela.getSelectionModel().getSelectedItem();
        txid.setText(""+m.getId());
        txtitulo.setText(m.getTitulo());
        txduracao.setText(""+m.getDuracao());
        txurl.setText(m.getUrl());
        cbartista.getSelectionModel().select(m.getArtista());
        cbgenero.getSelectionModel().select(m.getGenero());
        estadoEdicao();
    }

    @FXML
    private void clkBtApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if(a.showAndWait().get() == ButtonType.OK){
            musicaDAL dal = new musicaDAL();
            Musica m;
            m = tabela.getSelectionModel().getSelectedItem();
            dal.apagar(m);
            carregaTabela("");
        }
    }

    @FXML
    private void clkBtConfirmar(ActionEvent event) {
        int cod;
        try{
            cod = Integer.parseInt(txid.getText());
        }
        catch(Exception e){
            cod = 0;
        }
        
        Musica m = new Musica(txurl.getText(), cbartista.getSelectionModel().getSelectedItem(), 
                              cbgenero.getSelectionModel().getSelectedItem(),Integer.parseInt(txduracao.getText()), 
                              txtitulo.getText(), cod);
        
        musicaDAL dal = new musicaDAL();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if(m.getId() == 0){
            if(dal.salvar(m)){
                a.setContentText("Gravado com sucesso");
            }
            else{
                a.setContentText("Problemas ao gravar");
            }
        }
        else
            if(dal.alterar(m)){
                a.setContentText("Alterado com sucesso");
            }
            else{
                a.setContentText("Problemas ao alterar");
            }
        a.showAndWait();
        estadoOriginal();
    }

    @FXML
    private void clkBtCancelar(ActionEvent event) {
        if(!ap.isDisable())
           estadoOriginal();
        else
            ap.getScene().getWindow().hide();
    }

    @FXML
    private void clkBtBuscar(ActionEvent event) {
        carregaTabela("upper (mus_titulo) like '%"+txbusca.getText().toUpperCase()+"%'");
    }

    @FXML
    private void clkTabela(MouseEvent event) {
        if(tabela.getSelectionModel().getSelectedIndex() >= 0){
            btapagar.setDisable(false);
            btalterar.setDisable(false);
        }
    }
    
}
