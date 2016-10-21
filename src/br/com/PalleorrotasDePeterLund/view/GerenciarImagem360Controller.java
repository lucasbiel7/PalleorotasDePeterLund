/*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.FxManager;
import br.com.PalleorrotasDePeterLund.control.Message;
import br.com.PalleorrotasDePeterLund.control.dao.Imagem360DAO;
import br.com.PalleorrotasDePeterLund.model.entity.Imagem;
import br.com.PalleorrotasDePeterLund.model.entity.Imagem360;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class GerenciarImagem360Controller implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private GridPane gpFotos;
    @FXML
    private Button btAdicionarFotos;
    @FXML
    private ScrollPane spPreview;

    private Imagem imagem;

    private List<Imagem> imagens;

    private boolean remover;

    private FileChooser fileChooser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagens = new ArrayList<>();
        Platform.runLater(() -> {
            this.imagem = (Imagem) apPrincipal.getUserData();
            imagens = new Imagem360DAO().pegarPorPrincipal(imagem).stream().map(Imagem360::getId).map(Imagem360.Imagem360Id::getComposicao).collect(Collectors.toList());
            imagens.add(imagem);
            carregarFotos();
        });
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagem", "*.jpg", "*.png", "*.gif", "*.JPG", "*.PNG"));

    }

    @FXML
    private void btRemoverFotosActionEvent(ActionEvent ae) {
        remover = !remover;
        Button button = (Button) ae.getSource();
        button.setText(remover ? "Remover foto" : "Clique na foto para remover");
    }

    @FXML
    private void btAdicionarFotosActionEvent(ActionEvent ae) {
        List<File> files = fileChooser.showOpenMultipleDialog(apPrincipal.getScene().getWindow());
        Thread uploadImage = new Thread(() -> {
            if (files != null) {
                for (File file : files) {
                    try {
                        final Imagem imagem = new Imagem();
                        imagem.setImagem(Files.readAllBytes(file.toPath()));
                        final int pos = imagens.size();
                        imagens.add(imagem);
                        Platform.runLater(() -> {
                            adicionarFoto(imagem, pos);
                        });
                    } catch (IOException ex) {
                        Logger.getLogger(GerenciarImagem360Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        uploadImage.start();
        btAdicionarFotos.setText("Carregando fotos");
        btAdicionarFotos.setDisable(true);
        new Thread(() -> {
            try {
                uploadImage.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(GerenciarImagem360Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            Platform.runLater(() -> {
                btAdicionarFotos.setText("Adicionar Fotos");
                btAdicionarFotos.setDisable(false);
            });

        }).start();

    }

    private void carregarFotos() {
        gpFotos.getChildren().clear();
        int n = 0;
        for (Imagem imagem : imagens) {
            adicionarFoto(imagem, n);
            n++;
        }
        spPreview.setContent(FxManager.carregarComponente("ImageView360", imagens));
    }

    void adicionarFoto(Imagem imagem, int number) {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setOnMouseReleased((MouseEvent event) -> {
            if (remover) {
                if (imagem.equals(this.imagem)) {
                    Message.mostrarMessage("Remover foto principal", "Não é permitido remover a foto principal", Message.Tipo.ERRO);
                } else {
                    imagens.remove(imagem);
                    carregarFotos();
                }
            }
        });
        imageView.setImage(new Image(new ByteArrayInputStream(imagem.getImagem())));
        gpFotos.add(imageView, number % 6, number / 6);
    }
}
