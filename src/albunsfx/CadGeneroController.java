
package albunsfx;

import albunsfx.db.entidade.Genero;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import albunsfx.db.dal.ArtistaDAL;
import albunsfx.db.dal.GeneroDAL;
import albunsfx.db.dal.TipoArtistaDAL;
import albunsfx.db.entidade.Artista;
import albunsfx.db.entidade.TipoArtista;
import albunsfx.db.util.Banco;
import com.jfoenix.controls.JFXComboBox;
import java.util.List;

/**
 *
 * @author Silvio
 */
public class CadGeneroController implements Initializable {
    
   
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
    private AnchorPane pndados;
    @FXML
    private JFXTextField txid;
    @FXML
    private HBox pnpesquisa;
    @FXML
    private JFXTextField txbusca;
    @FXML
    private Button btbuscar;
    @FXML
    private TableView<Genero> tabela;
    @FXML
    private JFXTextField txnome;
    
    @FXML
    private TableColumn<Genero, Integer> colid;
    @FXML
    private TableColumn<Genero, String> colnome;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // vincular as colunas da tabela aos beans
        colid.setCellValueFactory(new PropertyValueFactory("id"));
        colnome.setCellValueFactory(new PropertyValueFactory("nome"));
        
        // configurar os componentes, preparando para edição/busca/fechamento da janela         
        estadoOriginal();
    }

    private void estadoOriginal() {
        pnpesquisa.setDisable(false);
        pndados.setDisable(true);

        btconfirmar.setDisable(true);
        btcancelar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        btnovo.setDisable(false);

        ObservableList<Node> componentes = pndados.getChildren(); //”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl) // textfield, textarea e htmleditor
            {
                ((TextInputControl) n).setText("");
            }
            if (n instanceof ComboBox) {
                ((ComboBox) n).getItems().clear();
            }
        }

        carregaTabela("");
    }
   
    private void estadoEdicao() {     // carregar os componentes da tela (listbox, combobox, ...)
        // p.e. : carregaEstados();
        pnpesquisa.setDisable(true);
        pndados.setDisable(false);
        btconfirmar.setDisable(false);
        btnovo.setDisable(true);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txnome.requestFocus(); //o cursor vai para o textfield de titulo
    }
    private void carregaTabela(String filtro) {
        GeneroDAL dal = new GeneroDAL();
        List<Genero> res = dal.get(filtro);
        ObservableList<Genero> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }

    @FXML
    private void clkBtNovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkBtAlterar(ActionEvent event) {
        Genero g = (Genero) tabela.getSelectionModel().getSelectedItem();
        txid.setText("" + g.getId());
        txnome.setText(g.getNome());
        estadoEdicao();
    }

    @FXML
    private void clkBtApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if(a.showAndWait().get() == ButtonType.OK){
            GeneroDAL dal = new GeneroDAL();
            Genero g;
            g = tabela.getSelectionModel().getSelectedItem();
            dal.apagar(g);
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
        Genero g = new Genero(cod, txnome.getText());
        GeneroDAL dal = new GeneroDAL();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if(g.getId() == 0){
            if(dal.salvar(g))
                a.setContentText("Gravado com sucesso");
            else
                a.setContentText("Problemas ao Gravar");
        }
        else
            if(dal.alterar(g))
                a.setContentText("Alterado com sucesso");
            else
                a.setContentText("Problemas ao Alterar");
        a.showAndWait();
        estadoOriginal();
    }

    @FXML
    private void clkBtCancelar(ActionEvent event) {
        if(!pndados.isDisabled())  // encontra em estado de edição
            estadoOriginal();
        else
            pndados.getScene().getWindow().hide();
    }

    @FXML
    private void clkBtBuscar(ActionEvent event) {
        carregaTabela("upper(gen_nome) like '%"+txbusca.getText().toUpperCase()+"%'");
    }

    @FXML
    private void clkTabela(MouseEvent event) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    
}
