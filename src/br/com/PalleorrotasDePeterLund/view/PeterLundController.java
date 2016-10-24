/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.controlsfx.control.InfoOverlay;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class PeterLundController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Label lbTitulo;
    @FXML
    private Text tConteudo;
    @FXML
    private StackPane spOverLay;

    private InfoOverlay ioFoto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        spOverLay.getChildren().add(ioFoto);
        
    }

}
