/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.Formatter;
import br.com.PalleorrotasDePeterLund.control.Seguranca;
import br.com.PalleorrotasDePeterLund.control.dao.UsuarioDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class GerenciarUsuarioController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfLogin;
    @FXML
    private PasswordField pfSenha;
    @FXML
    private DatePicker dpDataNascimento;
    @FXML
    private ImageView ivFoto;
    @FXML
    private Label lbFoto;
    @FXML
    private Label lbTitulo;

    private Usuario usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            if (apPrincipal.getUserData() != null) {
                usuario = (Usuario) apPrincipal.getUserData();
            }
            carregarUsuario();
        });
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent ae) {
        usuario.setNome(tfNome.getText());
        usuario.setEmail(tfEmail.getText());
        usuario.setLogin(tfLogin.getText());
        usuario.setSenha(Seguranca.encriptar(pfSenha.getText()));
        usuario.setDataDeNascimento(Formatter.toDate(dpDataNascimento.getValue()));
        if (usuario.getId() == null) {
            new UsuarioDAO().cadastrar(usuario);
        } else {
            new UsuarioDAO().editar(usuario);
        }

    }

    @FXML
    private void btCancelarActionEvent(ActionEvent ae) {
        ((Stage) apPrincipal.getScene().getWindow()).close();
    }

    private void carregarUsuario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
