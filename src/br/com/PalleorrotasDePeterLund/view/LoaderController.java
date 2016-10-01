/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.FxManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class LoaderController implements Initializable {

    @FXML
    private Label lbCarregando;
    private Timeline loader;
    @FXML
    private AnchorPane apPrincipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loader = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
            lbCarregando.setText(lbCarregando.getText() + ".");
        }));
        loader.setCycleCount(4);
        loader.play();
        loader.setOnFinished((ActionEvent event) -> {
            ((Stage) apPrincipal.getScene().getWindow()).close();
            FxManager.carregarJanela(FxManager.carregarComponente("Inicio"), "Ínicio", FxManager.Tipo.EXIT_ON_CLOSE, FxManager.Tipo.MAXIMIZED).show();
        });
    }

}
