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
public class GeologiaRegionalController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private StackPane spImagem;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ImageView imageView = new ImageView(ImageFactory.loadImage("conteudo/Foto da Geologia do Bambuí .png"));
        imageView.setFitWidth(450);
        imageView.setFitHeight(600);
        InfoOverlay infoOverlay = new InfoOverlay(imageView, "Coluna estratigráfica do Grupo Bambuí\n"
                + "(sensu Dardenne, 1978). O Conglomerado Carrancas\n"
                + "foi representado na base, embora não constitua\n"
                + "unidade formal em função de sua ocorrência restrita.");
        spImagem.getChildren().add(infoOverlay);
    }

}
