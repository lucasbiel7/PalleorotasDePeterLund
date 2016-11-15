/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.ImageFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
public class PovoDeLuziaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private StackPane spFoto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            ImageView imageView = new ImageView(ImageFactory.loadImage("conteudo/povoDeLuzia.png"));
            imageView.setFitWidth(450);
            System.out.println(spFoto.getHeight());
            imageView.setFitHeight(600);
            imageView.setPreserveRatio(false);
            InfoOverlay infoOverlay = new InfoOverlay(imageView, "Crânios de Lagoa Santa: ossadas da região mineira serviram para formular teoria alternativa de povoamento das Américas\n"
                    + "\n"
                    + "Fonte : Pesquisa FAPESP - A América de Luzia\n"
                    + "Disponível em: http://revistapesquisa.fapesp.br/2012/08/22/a-am%C3%A9rica-de-luzia/");
            spFoto.getChildren().add(infoOverlay);
        });
    }

}
