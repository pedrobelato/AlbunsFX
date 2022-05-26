package albunsfx;

import albunsfx.db.dal.AlbumDAL;
import albunsfx.db.dal.ArtistaDAL;
import albunsfx.db.dal.GeneroDAL;
import albunsfx.db.dal.mus_albumDAL;
import albunsfx.db.dal.musicaDAL;
import albunsfx.db.entidade.Album;
import albunsfx.db.entidade.Artista;
import albunsfx.db.entidade.Genero;
import albunsfx.db.entidade.Musica;
import albunsfx.db.entidade.mus_album;
import albunsfx.db.util.Banco;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.scene.layout.region.Margins;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

public class CadAlbumController implements Initializable {

    @FXML
    private Button btconfirmar;
    @FXML
    private Button btcancelar;
    @FXML
    private JFXTextField txid;
    @FXML
    private JFXTextField txano;
    @FXML
    private JFXTextField txdesc;
    @FXML
    private JFXComboBox<Genero> cbgenero;
    @FXML
    private JFXComboBox<Artista> cbartista;
    @FXML
    private Spinner<Integer> spratiing;
    @FXML
    private ImageView ivimagem;
    @FXML
    private JFXTextField txbusca;
    @FXML
    private Button btbuscar;
    @FXML
    private Button btNova;
    @FXML
    private TableView<Musica> tabelamus;
    @FXML
    private TableColumn<Musica, Integer> tmtitulo;
    @FXML
    private TableColumn<Musica, Integer> tmart;
    @FXML
    private Button btinsere;
    @FXML
    private Button btretira;
    @FXML
    private TableView<Musica> tabelanovo;
    @FXML
    private TableColumn<Musica, Integer> novotitulo;
    @FXML
    private TableColumn<Musica, Integer> novoart;
    private Object SpinnerValueFactory;
    @FXML
    private JFXTextField txtitulo;
    
    List<Musica> lista = new ArrayList();
    List<Musica> listadois = new ArrayList();
    Album a;

    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        ivimagem.setImage(new Image("images/singer.png"));
        tmart.setCellValueFactory(new PropertyValueFactory("id"));
        tmtitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        
        novoart.setCellValueFactory(new PropertyValueFactory("artista"));
        novotitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        carregaTabela("");
        carregarCombos();
        
        SpinnerValueFactory<Integer> g = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,5);
        spratiing.setValueFactory(g);
    }    
    
    public void setAtributo(Album a){
        this.a = a;
        tela();
        carregaTabela("");
    }
    
    private void tela(){
        txtitulo.setText(a.getTitulo());
        txano.setText(Integer.toString(a.getAno()));
        txdesc.setText(a.getDescricao());
        txid.setText(Integer.toString(a.getId()));
        cbartista.getSelectionModel().select(0);
        cbartista.getSelectionModel().select(a.getArtista().getId());
        cbgenero.getSelectionModel().select(0);
        cbgenero.getSelectionModel().select(a.getGenero().getId());
        ivimagem.setImage(new Image(new AlbumDAL().getFoto(a.getId())));
    }
    
    private void carregarCombos(){
        GeneroDAL gdal = new GeneroDAL();
        cbgenero.setItems(FXCollections.observableArrayList(gdal.get("")));
        
        ArtistaDAL adal = new ArtistaDAL();
        cbartista.setItems(FXCollections.observableArrayList(adal.get("")));
    }
    
    private void carregaTabela(String filtro){
        musicaDAL dal = new musicaDAL();
        List<Musica> res = dal.get(filtro);
        ObservableList<Musica> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabelamus.setItems(modelo);
        
        mus_albumDAL mbdal = new mus_albumDAL();
        List<Musica> mus = new ArrayList();
        
        if(a != null){
            mus = mbdal.get(Integer.parseInt(txid.getText()));
        }
        else{
            mus = mbdal.get(Banco.getConexao().getMaxPK("album", "al_id")+1);      
        }
        if(mus != null){
            lista = mus;
            ObservableList<Musica> model;
            model = FXCollections.observableArrayList(mus);
            tabelanovo.setItems(model);
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
        GeneroDAL gdal = new GeneroDAL();
        ArtistaDAL adal = new ArtistaDAL();
        Album al = new Album(cod, Integer.parseInt(txano.getText()), spratiing.getValue(), txtitulo.getText(), txdesc.getText(), 
                             cbgenero.getSelectionModel().getSelectedItem(), cbartista.getSelectionModel().getSelectedItem());
        AlbumDAL aldal = new AlbumDAL();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if(al.getId() == 0){
            if(!aldal.salvar(al, imageToInputStream(ivimagem.getImage()))){
                 a.setContentText("Problemas ao Gravar: "+Banco.getConexao().getMensagemErro());
                 a.showAndWait();
            }
        }
        else
            if(!aldal.alterar(al, imageToInputStream(ivimagem.getImage()))){
                a.setContentText("Problemas ao Alterar: "+Banco.getConexao().getMensagemErro());
                a.showAndWait();
            }
        mus_albumDAL mbdal = new mus_albumDAL();
        if(a == null){
            for(Musica m: listadois){
                if(!mbdal.salvar(Banco.getConexao().getMaxPK("album", "al_id"),m.getId())){
                    a.setContentText("Problemas ao Gravar: "+Banco.getConexao().getMensagemErro());
                    a.showAndWait();
                } 
            }  
        }
        else{
            for(Musica m: listadois){
                if(!mbdal.salvar(Integer.parseInt(txid.getText()), m.getId())){
                    a.setContentText("Problemas ao Gravar: "+Banco.getConexao().getMensagemErro());
                    a.showAndWait();
                } 
            }  
        }

    }

    @FXML
    private void clkBtCancelar(ActionEvent event) {
        txid.setText("");
        txano.setText("");
        txbusca.setText("");
        txdesc.setText("");
        txtitulo.setText("");
        cbartista.getSelectionModel().select(-1);
        cbgenero.getSelectionModel().select(-1);
        ivimagem.setImage(new Image("images/singer.png"));
        a = null;
        lista.clear();
        carregaTabela("");     
    }

    @FXML
    private void clkBtBuscar(ActionEvent event) {
        carregaTabela("upper(mus_titulo) like '%"+txbusca.getText().toUpperCase()+"%'");
    }

    @FXML
    private void btinsereClick(ActionEvent event) {
        int flag=0;
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        int idmus;
        for(Musica m: lista){
            idmus = tabelamus.getSelectionModel().getSelectedItem().getId();
            if(idmus == m.getId()){
                flag=1;
            }       
        }
        if(flag == 0){
            if(tabelamus.getSelectionModel().getSelectedIndex() >= 0){
                listadois.add(tabelamus.getSelectionModel().getSelectedItem());
                lista.add(tabelamus.getSelectionModel().getSelectedItem());
                ObservableList<Musica> modelo;
                modelo = FXCollections.observableArrayList(lista);
                tabelanovo.setItems(modelo);
            }
        }

    }

    @FXML
    private void btretiraClick(ActionEvent event) {
        mus_albumDAL mbdal = new mus_albumDAL();
        if(tabelanovo.getSelectionModel().getSelectedIndex() >= 0){
            listadois.remove(tabelanovo.getSelectionModel().getSelectedItem());
            lista.remove(tabelanovo.getSelectionModel().getSelectedItem());
            mbdal.apagar(Integer.parseInt(txid.getText()),tabelanovo.getSelectionModel().getSelectedItem().getId());
            
            carregaTabela("");
        }
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
    private void clkfoto(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha uma foto");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
        
        File file = fileChooser.showOpenDialog(null);
        
        ivimagem.setImage(new Image(file.toURI().toString()));
    }

    @FXML
    private void clkTabela(MouseEvent event) {
    }
}
