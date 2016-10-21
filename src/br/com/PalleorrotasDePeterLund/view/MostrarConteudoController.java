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
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class MostrarConteudoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ScrollPane spContainer;

    private Parent anterior;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            Parent parent = (Parent) apPrincipal.getUserData();
            spContainer.setContent(parent);
        });
    }

    @FXML
    private void btVoltarActionEvent(ActionEvent ae) {
        if (anterior == null) {
            System.gc();
            apPrincipal.getScene().setRoot(FxManager.carregarComponente("Inicio"));
        }
    }
}
