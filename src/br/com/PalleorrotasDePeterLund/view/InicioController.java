/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.FxManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
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
        FxManager.carregarJanela(FxManager.carregarComponente("Login"), "Autenticação", FxManager.Tipo.MODAL, FxManager.Tipo.UNRESIZABLE).showAndWait();
    }
    

}
