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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.InfoOverlay;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class FormacaoGrutaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private StackPane spFoto;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ImageView imageView=new ImageView(new Image(getClass().getResourceAsStream("/br/com/PalleorrotasDePeterLund/view/image/comoForma.png")));
        imageView.setFitWidth(350);
        InfoOverlay infoOverlay=new InfoOverlay(imageView," 1- estalactite; 2- cortina; 3- coluna; 4-canudo(estalactite) 5- estalagmites, 6-helictite; 7- flores de aragonita; 8- casacata de pedra; 9- cristais \"dente-de-cão\" 10- pérolas de caverna; 11- \"vulcões\" 12- represesas travertino "
                + "\nFonte: Cavernas Brasileiras; Clayton f. Lino; João Allievi, pg. 43");
        spFoto.getChildren().add(infoOverlay);
    }

}
