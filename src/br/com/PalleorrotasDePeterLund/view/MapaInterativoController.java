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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class MapaInterativoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ImageView ivFoto;

    private PopOver poInformacao;

    private boolean mapa;

    private Text tDescricao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tDescricao = new Text();
        poInformacao = new PopOver(tDescricao);
    }

    @FXML
    private void rcMaquineMouseEvent(MouseEvent mouseEvent) {
        if (mapa) {
            ivFoto.setImage(ImageFactory.loadImage("mapa.jpg"));
            mapa = false;
        } else {
            mostrarConteudo("maquine.png", " distância da Gruta Maquiné à Belo Horizonte é 132km", mouseEvent.getScreenX(), mouseEvent.getSceneY());
            Platform.runLater(() -> {
                mapa = true;
            });
        }
    }

    @FXML
    private void rcLapinhaMouseEvent(MouseEvent mouseEvent) {
        if (mapa) {
            ivFoto.setImage(ImageFactory.loadImage("mapa.jpg"));
            mapa = false;
        } else {
            mostrarConteudo("sete lagoa.png", "A distância da Gruta da lapinha à Belo Horizonte é 52km", mouseEvent.getScreenX(), mouseEvent.getSceneY());
            Platform.runLater(() -> {
                mapa = true;
            });
        }
    }

    @FXML
    private void rcReiDoMatMouseEvent(MouseEvent mouseEvent) {
        if (mapa) {
            ivFoto.setImage(ImageFactory.loadImage("mapa.jpg"));
            mapa = false;
        } else {
            mostrarConteudo("rei do mato.png", "A distância da Gruta do Rei do Mato à Belo Horizonte é 69km", mouseEvent.getScreenX(), mouseEvent.getSceneY());
            Platform.runLater(() -> {
                mapa = true;

            });
        }
    }

    @FXML
    private void ivFotoMouseEvent(MouseEvent mouseEvent) {
        if (mapa && !(mouseEvent.getSource() instanceof Rectangle)) {
            ivFoto.setImage(ImageFactory.loadImage("mapa.jpg"));
            mapa = false;
        }
    }

    private void mostrarConteudo(String imagem, String descricao, double x, double y) {
        ivFoto.setImage(ImageFactory.loadImage(imagem));
        tDescricao.setText("  "+descricao+"  ");
        poInformacao.show(ivFoto, x, y);
    }
}
