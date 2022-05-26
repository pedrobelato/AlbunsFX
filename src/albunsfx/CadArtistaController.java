
package albunsfx;

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
import albunsfx.db.dal.TipoArtistaDAL;
import albunsfx.db.entidade.Artista;
import albunsfx.db.entidade.TipoArtista;
import albunsfx.db.util.Banco;
import com.jfoenix.controls.JFXComboBox;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

public class CadArtistaController implements Initializable {
    
   
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
    private TableView<Artista> tabela;
    @FXML
    private JFXTextField txnome;
    @FXML
    private JFXTextField txorigem;
    @FXML
    private JFXDatePicker dtnasc;
    @FXML
    private JFXComboBox<TipoArtista> cbtipo;
    @FXML
    private TableColumn<Artista, Integer> colid;
    @FXML
    private TableColumn<Artista, String> colnome;
    @FXML
    private TableColumn<Artista, Integer> coltipo;
    @FXML
    private ImageView foto;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // vincular as colunas da tabela aos beans
        colid.setCellValueFactory(new PropertyValueFactory("id"));
        colnome.setCellValueFactory(new PropertyValueFactory("nome"));
        coltipo.setCellValueFactory(new PropertyValueFactory("tipoartista"));
        
        // configurar os componentes, preparando para edição/busca/fechamento da janela         
        estadoOriginal();
    }

    private void estadoOriginal() {
        foto.setImage(new Image("images/singer.png"));
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
        carregarCombos();
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
        ArtistaDAL dal = new ArtistaDAL();
        List<Artista> res = dal.get(filtro);
        ObservableList<Artista> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    private void carregarCombos() {
        TipoArtistaDAL dal = new TipoArtistaDAL();
        cbtipo.setItems(FXCollections.observableArrayList(dal.get("")));     
    }

    @FXML
    private void clkBtNovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkBtAlterar(ActionEvent event) {
        Artista a = (Artista) tabela.getSelectionModel().getSelectedItem();
        txid.setText("" + a.getId());
        txnome.setText(a.getNome());
        txorigem.setText(a.getOrigem());
        dtnasc.setValue(a.getDtnasc());
        cbtipo.getSelectionModel().select(0);
        cbtipo.getSelectionModel().select(a.getTipoartista());
        foto.setImage(new Image(new ArtistaDAL().getFoto(a.getId())));
        estadoEdicao();        
    }

    @FXML
    private void clkBtApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            ArtistaDAL dal = new ArtistaDAL();
            Artista art;
            art = tabela.getSelectionModel().getSelectedItem();
            dal.apagar(art);
            carregaTabela("");
        }
    }

    @FXML
    private void clkBtConfirmar(ActionEvent event) {
        int cod;
        try {
            cod = Integer.parseInt(txid.getText());
        } catch (Exception e) {
            cod = 0;
        }
        Artista art = new Artista(cod, txnome.getText(), txorigem.getText(), 
                                  dtnasc.getValue(),cbtipo.getSelectionModel().getSelectedItem());
        ArtistaDAL dal = new ArtistaDAL();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if (art.getId() == 0) // novo cadastro
        {   
            if (!dal.salvar(art, imageToInputStream(foto.getImage()))) {
                a.setContentText("Problemas ao Gravar: "+Banco.getConexao().getMensagemErro());
                 a.showAndWait();
            }
        }else //alteração de cadastro
        if (!dal.alterar(art, imageToInputStream(foto.getImage()))) {
            a.setContentText("Problemas ao Alterar: "+Banco.getConexao().getMensagemErro());
             a.showAndWait();
        }
       
        estadoOriginal();

    }
    private InputStream imageToInputStream(Image image) {
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream=null;
        try {
            ImageIO.write(bImage, "jpg", outputStream);
            byte[] res = outputStream.toByteArray();
            inputStream = new ByteArrayInputStream(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
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
        carregaTabela("upper(art_nome) like '%"+txbusca.getText().toUpperCase()+"%'");
    }

    @FXML
    private void clkTabela(MouseEvent event) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    @FXML
    private void clkFoto(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha uma foto");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
        
        File file = fileChooser.showOpenDialog(null);
        
        foto.setImage(new Image(file.toURI().toString()));
        
    }
    
}
