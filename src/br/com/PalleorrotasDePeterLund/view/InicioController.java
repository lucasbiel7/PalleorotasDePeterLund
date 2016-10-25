/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.FxManager;
import br.com.PalleorrotasDePeterLund.control.Sessao;
import br.com.PalleorrotasDePeterLund.control.dao.GrutaDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Gruta;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    @FXML
    private SplitMenuButton smbGrutas;
    @FXML
    private ScrollPane spConteudo;

    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        atualizarLogin();
        for (Gruta gruta : new GrutaDAO().pegarTodos()) {
            MenuItem menuItem = new MenuItem(gruta.getNome());
            menuItem.setOnAction((ActionEvent event) -> {
                apPrincipal.getScene().setRoot(FxManager.carregarComponente("MostrarConteudo", FxManager.carregarComponente("VisualizarGruta", gruta)));
            });
            smbGrutas.getItems().add(menuItem);
        }
        Platform.runLater(() -> {
            apPrincipal.getScene().getWindow().setOnCloseRequest((WindowEvent event) -> btSairActionEvent(null));
            stage = (Stage) apPrincipal.getScene().getWindow();
        });
        spConteudo.setContent(FxManager.carregarComponente("PeterLund"));

    }

    @FXML
    private void btSairActionEvent(ActionEvent ae) {
        stage.close();
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
        if (Sessao.usuario != null) {
            apPrincipal.getScene().setRoot(FxManager.carregarComponente("MostrarConteudo", FxManager.carregarComponente("GerenciarAplicativo")));
        }
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
