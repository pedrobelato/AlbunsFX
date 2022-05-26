package albunsfx;

import albunsfx.db.dal.TipoArtistaDAL;
import albunsfx.db.entidade.TipoArtista;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.Observable;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class CadTipoArtistaController implements Initializable {

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
    private JFXTextField txnome;
    @FXML
    private JFXTextField txbusca;
    @FXML
    private Button btbuscar;
    @FXML
    private TableView<TipoArtista> tabela;
    @FXML
    private TableColumn<TipoArtista, Integer> colid;
    @FXML
    private TableColumn<TipoArtista, String> colnome;
    @FXML
    private AnchorPane ap;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colid.setCellValueFactory(new PropertyValueFactory("id"));
        colnome.setCellValueFactory(new PropertyValueFactory("nome"));
        
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
        for(Node n: componentes){
            if(n instanceof TextInputControl){
                ((TextInputControl) n).setText("");
            }
        }
        
        carregaTabela("");
    }
    
    private void estadoEdicao(){
        ap.setDisable(false);
        
        txid.setDisable(true);
        btconfirmar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        
        txnome.requestFocus();
    }
    
    private void carregaTabela(String filtro){
        TipoArtistaDAL dal = new TipoArtistaDAL();
        List<TipoArtista> res = dal.get(filtro);
        ObservableList<TipoArtista> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    @FXML
    private void clkBtNovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkBtAlterar(ActionEvent event) {
        TipoArtista t = (TipoArtista) tabela.getSelectionModel().getSelectedItem();
        txid.setText(""+t.getId());
        txnome.setText(t.getNome());
        estadoEdicao();
    }

    @FXML
    private void clkBtApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if(a.showAndWait().get() == ButtonType.OK){
            TipoArtistaDAL dal = new TipoArtistaDAL();
            TipoArtista t;
            t = tabela.getSelectionModel().getSelectedItem();
            dal.alterar(t);
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
        
        TipoArtista t = new TipoArtista(cod, txnome.getText());
        TipoArtistaDAL dal = new TipoArtistaDAL();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if(t.getId() == 0){
            if(dal.salvar(t)){
                a.setContentText("Gravado com sucesso");
            }
            else{
                a.setContentText("Problemas ao gravar");
            }
        }
        else
            if(dal.alterar(t)){
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
        carregaTabela("upper (ta_nome) like '%"+txbusca.getText().toUpperCase()+"%'");
    }

    @FXML
    private void clkTabela(MouseEvent event) {
        if(tabela.getSelectionModel().getSelectedIndex() >= 0){
            btapagar.setDisable(false);
            btalterar.setDisable(false);
        }
    }
    
}
