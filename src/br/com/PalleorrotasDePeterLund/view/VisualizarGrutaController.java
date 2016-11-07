/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.FxManager;
import br.com.PalleorrotasDePeterLund.control.dao.GrutaImagemDAO;
import br.com.PalleorrotasDePeterLund.control.dao.Imagem360DAO;
import br.com.PalleorrotasDePeterLund.model.entity.Gruta;
import br.com.PalleorrotasDePeterLund.model.entity.GrutaImagem;
import br.com.PalleorrotasDePeterLund.model.entity.Imagem;
import br.com.PalleorrotasDePeterLund.model.entity.Imagem360;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import org.controlsfx.control.InfoOverlay;
import org.controlsfx.control.PopOver;

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
    private FlowPane fpFotos;
    @FXML
    private Pagination pgFotos;
    @FXML
    private WebView wvConteudo;
    @FXML
    private StackPane spImageTexto;
    @FXML
    private AnchorPane apConteudo;
    private Gruta gruta;
    private List<GrutaImagem> imagens;
    private List<ImageView> imageViews;
    private List<InfoOverlay> infoOverlays;

    private final int LINHA = 2;
    private int COLUNA = 3;
    private PopOver popOver;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            gruta = (Gruta) apPrincipal.getUserData();
            lbTitulo.setText(gruta.getNome());
            wvConteudo.getEngine().loadContent("<html><head><style>"
                    + "body {"
                    + "background-color: #EFFEBA;"
                    + "color:black;"
                    + "}"
                    + "div{"
                    + "text-align: justify;"
                    + "}"
                    + "</style>"
                    + "<head><body><div>" + gruta.getConteudo());
            List<GrutaImagem> grutaImagens = new GrutaImagemDAO().pegarPorGrutaTexto(gruta, true);
            if (!grutaImagens.isEmpty()) {
                ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(grutaImagens.get(0).getId().getImagem().getImagem())));
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(400);
                imageView.setSmooth(true);
                InfoOverlay infoOverlay = new InfoOverlay(imageView, grutaImagens.get(0).getLegenda());
                spImageTexto.getChildren().setAll(infoOverlay);
            }
        });
        pgFotos.currentPageIndexProperty().addListener((Observable observable) -> {
            limparImageView();
            carregarFotos();
        });
        popOver = new PopOver();
    }

    @FXML
    private void tbFotoOnSelectionChanged(Event event) {
        if (imagens == null || imagens.isEmpty()) {
            imagens = new GrutaImagemDAO().pegarPorGruta(gruta);
            COLUNA=(int) fpFotos.getWidth()/310;
            pgFotos.setPageCount((int) Math.ceil(imagens.size() / (double) (LINHA * COLUNA)));
            carregarImageViews();
            limparImageView();
            carregarFotos();
        }
    }

    private void carregarImageViews() {
        fpFotos.getChildren().clear();
        imageViews = new ArrayList<>();
        infoOverlays = new ArrayList<>();
        for (int i = 0; i < LINHA; i++) {
            for (int j = 0; j < COLUNA; j++) {
                final ImageView imageView = new ImageView();
                final InfoOverlay infoOverlay = new InfoOverlay(imageView, "");
                infoOverlay.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    atualizarInforOverlay(newValue, infoOverlay);
                });
                imageView.imageProperty().addListener((ObservableValue<? extends Image> observable, Image oldValue, Image newValue) -> {
                    atualizarInforOverlay(infoOverlay.getText(), infoOverlay);
                });
                imageView.setFitWidth(300);
                imageView.setFitHeight(300);
                imageView.setPreserveRatio(false);
                imageView.setSmooth(true);
                fpFotos.getChildren().add(infoOverlay);
                imageViews.add(imageView);
                infoOverlays.add(infoOverlay);
            }
        }
    }

    private void limparImageView() {
        for (ImageView imageView : imageViews) {
            imageView.setImage(null);
            imageView.setVisible(false);
        }
        for (InfoOverlay infoOverlay : infoOverlays) {
            infoOverlay.setText("");
            infoOverlay.setVisible(false);
        }
    }

    private void carregarFotos() {
        int inicio = pgFotos.getCurrentPageIndex() * COLUNA * LINHA;
        int fim = inicio + (COLUNA * LINHA);
        int imvcount = 0;
        for (GrutaImagem grutaImagem : imagens.subList(inicio, fim > imagens.size() ? imagens.size() : fim)) {
            ImageView imageView = imageViews.get(imvcount);
            InfoOverlay infoOverlay = infoOverlays.get(imvcount);
            infoOverlay.setText(grutaImagem.getLegenda());
            imageView.setImage(new Image(new ByteArrayInputStream(grutaImagem.getId().getImagem().getImagem())));
            imageView.setVisible(true);
            infoOverlay.setVisible(true);
            final List<Imagem> imagens = new ArrayList<>();
            Thread carregandoImagem = new Thread(() -> {
                imagens.add(grutaImagem.getId().getImagem());
                for (Imagem360 imagem360 : new Imagem360DAO().pegarPorPrincipal(grutaImagem.getId().getImagem())) {
                    imagens.add(imagem360.getId().getComposicao());
                }
            });
            carregandoImagem.start();
            imageView.setOnMouseReleased((MouseEvent event) -> {
                try {
                    carregandoImagem.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(VisualizarGrutaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                AnchorPane anchorPane = (AnchorPane) FxManager.carregarComponente("ImageView360", imagens);
                anchorPane.setPrefSize(600, 400);
                popOver.setContentNode(anchorPane);
                popOver.show(apPrincipal.getScene().getWindow());
            });
            imvcount++;
        }
    }

    private void atualizarInforOverlay(String legenda, InfoOverlay infoOverlay) {
        if (legenda == null || legenda.isEmpty()) {
            if (infoOverlay.getContent() instanceof ImageView) {
                if (((ImageView) infoOverlay.getContent()).getImage() != null) {
                    infoOverlay.setVisible(true);
                } else {
                    infoOverlay.setVisible(false);
                }
            } else {
                infoOverlay.setVisible(false);
            }
        } else {
            infoOverlay.setVisible(true);
        }
    }
}
