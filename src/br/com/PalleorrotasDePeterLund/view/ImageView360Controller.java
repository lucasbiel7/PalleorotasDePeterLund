/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.model.entity.Imagem;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class ImageView360Controller implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private ImageView ivPrincipal;

    private List<Imagem> imagens;

    private int lastX = 0;
    private int atualImagem = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imagens = new ArrayList<>();
        Platform.runLater(() -> {
            if (apPrincipal.getUserData() instanceof Image) {
                imagens.add((Imagem) apPrincipal.getUserData());
            } else if (apPrincipal.getUserData() instanceof List) {
                imagens = (List<Imagem>) apPrincipal.getUserData();
            }
            carregarImagem(0);
            ivPrincipal.setFitWidth(apPrincipal.getWidth());
            ivPrincipal.setFitHeight(apPrincipal.getHeight());
        });
        ivPrincipal.setOnDragDetected((MouseEvent event) -> {
            apPrincipal.getScene().setCursor(Cursor.CROSSHAIR);
        });
        ivPrincipal.setOnDragDone((DragEvent event) -> {
            apPrincipal.getScene().setCursor(Cursor.DEFAULT);
        });
        ivPrincipal.setOnMouseDragged((MouseEvent event) -> {
            if (event.getX() > lastX) {
                ++atualImagem;
            } else {
                --atualImagem;
            }
            if (atualImagem >= imagens.size()) {
                atualImagem = imagens.size() - 1;
            }
            if (atualImagem < 0) {
                atualImagem = 0;
            }
            carregarImagem(atualImagem);
            lastX = (int) event.getX();
            event.consume();
        });
    }

    private void carregarImagem(int i) {
        ivPrincipal.setImage(new Image(new ByteArrayInputStream(imagens.get(i).getImagem())));
    }
}
