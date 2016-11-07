/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.ImageFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.InfoOverlay;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class LuziaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private StackPane spFoto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ImageView imageView = new ImageView(ImageFactory.loadImage("conteudo/luzia.jpg"));
        imageView.setFitWidth(300);
        InfoOverlay infoOverlay = new InfoOverlay(imageView, "Fonte: Fósseis no Brasil - Luzia.\n"
                + "\n"
                + "Disponível em: http://www.uol/noticias/especiais/fosseis-no-brasil-onde-estao-registros-humanos-antigos-no-pais.htm#o-brasil-nao-foi-descoberto-em-1500");
        spFoto.getChildren().add(infoOverlay);
    }

}
