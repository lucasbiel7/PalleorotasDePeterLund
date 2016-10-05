/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.FxManager;
import br.com.PalleorrotasDePeterLund.control.Sessao;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class InicioController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Hyperlink hlLogin;
    @FXML
    private HBox hbUsuario;
    @FXML
    private Label lbUsuario;
    @FXML
    private ImageView ivFoto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            apPrincipal.getScene().getWindow().setOnCloseRequest((WindowEvent event) -> btSairActionEvent(null));
        });
    }

    @FXML
    private void btSairActionEvent(ActionEvent ae) {
        ((Stage) apPrincipal.getScene().getWindow()).close();
        FxManager.carregarJanela(FxManager.carregarComponente("Sobre"), "Sobre o aplicativo", FxManager.Tipo.EXIT_ON_CLOSE, FxManager.Tipo.UNDECORATED).show();
    }

    @FXML
    private void btLoginActionEvent(ActionEvent ae) {
        if (Sessao.usuario == null) {
            FxManager.carregarJanela(FxManager.carregarComponente("Login"), "Autenticação", FxManager.Tipo.MODAL, FxManager.Tipo.UNRESIZABLE).showAndWait();
            atualizarLogin();
        } else {
            Sessao.usuario = null;
            atualizarLogin();
        }
    }

    @FXML
    private void lbUsuarioMouseEvent(MouseEvent mouseEvent) {
        
    }

    private void atualizarLogin() {
        if (Sessao.usuario != null) {
            lbUsuario.setText(Sessao.usuario.getNome());
            if (Sessao.usuario.getFoto() != null) {
                ivFoto.setImage(new Image(new ByteArrayInputStream(Sessao.usuario.getFoto())));
            }
            hlLogin.setText("Logout");
        } else {
            lbUsuario.setText("");
            ivFoto.setImage(null);
            hlLogin.setText("Login");
        }
    }
}
