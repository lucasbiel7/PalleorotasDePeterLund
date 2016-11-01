/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.dao.UsuarioDAO;
import br.com.PalleorrotasDePeterLund.model.Perfil;
import br.com.PalleorrotasDePeterLund.model.entity.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class GerenciarUsuariosController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TableView<Usuario> tvUsuarios;
    @FXML
    private TableColumn<Usuario, String> tcNome;
    @FXML
    private TableColumn<Usuario, Usuario> tcPerfil;
    @FXML
    private TextField tfNome;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tvUsuarios.getItems().setAll(new UsuarioDAO().pegarTodos());
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcPerfil.setCellValueFactory((TableColumn.CellDataFeatures<Usuario, Usuario> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcPerfil.setCellFactory((TableColumn<Usuario, Usuario> param) -> {
            return new TableCell<Usuario, Usuario>() {
                @Override
                protected void updateItem(Usuario item, boolean empty) {
                    if (empty) {
                        setText("");
                        setGraphic(null);
                    } else {
                        ComboBox<Perfil> cbPerfil = new ComboBox<>();
                        cbPerfil.getItems().setAll(Perfil.values());
                        cbPerfil.getSelectionModel().select(item.getPerfil());
                        cbPerfil.setOnAction((ActionEvent event) -> {
                            item.setPerfil(cbPerfil.getValue());
                            new UsuarioDAO().editar(item);
                        });
                        setGraphic(cbPerfil);
                    }
                }
            };
        });
    }

    @FXML
    private void tfNomeKeyEvent(KeyEvent keyEvent) {
        tvUsuarios.getItems().setAll(new UsuarioDAO().pegarPorNome(tfNome.getText()));
    }

}
