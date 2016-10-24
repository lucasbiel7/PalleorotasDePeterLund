/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.dao.GrutaImagemDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Gruta;
import br.com.PalleorrotasDePeterLund.model.entity.GrutaImagem;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class VisualizarGrutaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Label lbTitulo;
    @FXML
    private Text tConteudo;

    @FXML
    private GridPane gpFotos;
    @FXML
    private Pagination pgFotos;

    private Gruta gruta;
    private List<GrutaImagem> imagens;
    private List<ImageView> imageViews;

    private final int LINHA = 2;
    private final int COLUNA = 3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            gruta = (Gruta) apPrincipal.getUserData();
            lbTitulo.setText(gruta.getNome());
            tConteudo.setText(gruta.getConteudo());
        });
        pgFotos.currentPageIndexProperty().addListener((Observable observable) -> {
            limparImageView();
            carregarFotos();
        });
    }

    @FXML
    private void tbFotoOnSelectionChanged(Event event) {
        if (imagens == null || imagens.isEmpty()) {
            imagens = new GrutaImagemDAO().pegarPorGruta(gruta);
            pgFotos.setPageCount(Math.round(imagens.size() / (LINHA * COLUNA)));
            carregarImageViews();
            limparImageView();
            carregarFotos();
        }
    }

    private void carregarImageViews() {
        gpFotos.getChildren().clear();
        imageViews = new ArrayList<>();
        for (int i = 0; i < LINHA; i++) {
            for (int j = 0; j < COLUNA; j++) {
                ImageView imageView = new ImageView();
                imageView.setFitWidth(300);
                imageView.setFitHeight(300);
                imageView.setPreserveRatio(false);
                imageView.setSmooth(true);
                gpFotos.add(imageView, j, i);
                imageViews.add(imageView);
            }
        }
    }

    private void limparImageView() {
        for (ImageView imageView : imageViews) {
            imageView.setImage(null);
            imageView.setVisible(false);
        }
    }

    private void carregarFotos() {
        int inicio = pgFotos.getCurrentPageIndex() * COLUNA * LINHA;
        int fim = inicio + (COLUNA * LINHA);
        int imvcount = 0;
        for (GrutaImagem imagen : imagens.subList(inicio, fim > imagens.size() ? imagens.size() : fim)) {
            ImageView imageView = imageViews.get(imvcount);
            imageView.setImage(new Image(new ByteArrayInputStream(imagen.getId().getImagem().getImagem())));
            imageView.setVisible(true);
            imvcount++;
        }
    }

}
