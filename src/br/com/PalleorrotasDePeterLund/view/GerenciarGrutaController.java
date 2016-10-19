/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.dao.GrutaDAO;
import br.com.PalleorrotasDePeterLund.control.dao.GrutaImagemDAO;
import br.com.PalleorrotasDePeterLund.control.dao.ImagemDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Gruta;
import br.com.PalleorrotasDePeterLund.model.entity.GrutaImagem;
import br.com.PalleorrotasDePeterLund.model.entity.Imagem;
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
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class GerenciarGrutaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TextField tfNome;
    @FXML
    private TextArea taDescricaoMapa;
    @FXML
    private TableView<Gruta> tvGruta;
    @FXML
    private TableColumn<Gruta, String> tcNome;

    @FXML
    private GridPane gpFoto;
    @FXML
    private Pagination pgImagem;
    private Gruta gruta;
    private List<Imagem> images;

    private FileChooser fcImagem;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fcImagem = new FileChooser();
        gruta = new Gruta();
        tvGruta.getItems().setAll(new GrutaDAO().pegarTodos());
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tvGruta.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Gruta>() {
            @Override
            public void changed(ObservableValue<? extends Gruta> observable, Gruta oldValue, Gruta newValue) {
                if (newValue == null) {
                    gruta = new Gruta();
                    images.clear();
                } else {
                    gruta = newValue;
                    //Carregando fotos da gruta

                }
                carregarGruta();
            }
        });
        images = new ArrayList<>();
        carregarFotos();
        pgImagem.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                carregarFotos();
            }
        });
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent ae) {
        gruta.setNome(tfNome.getText());
        gruta.setDescricaoMapa(taDescricaoMapa.getText());
        if (gruta.getId() == null) {
            new GrutaDAO().cadastrar(gruta);
        } else {
            new GrutaDAO().editar(gruta);
        }
        for (Imagem image : images) {
            if (image.getId() == null) {
                new ImagemDAO().cadastrar(image);
            }
            GrutaImagem grutaImagem = new GrutaImagem(new GrutaImagem.GrutaImagenID(gruta, image));
            if (new GrutaImagemDAO().pegarPorId(grutaImagem) == null) {
                new GrutaImagemDAO().cadastrar(grutaImagem);
            }
        }
    }

    @FXML
    private void btNovoActionEvent(ActionEvent ae) {
        gruta = new Gruta();
    }

    @FXML
    private void btAdicionarFotosActionEvent(ActionEvent ae) {
        List<File> arquivos = fcImagem.showOpenMultipleDialog(apPrincipal.getScene().getWindow());
        if (arquivos != null) {
            Thread uploadImagem = new Thread(() -> {
                for (File arquivo : arquivos) {
                    if (arquivo.getName().matches(".*\\.(jpg|png|gif|JPG|PNG|GIF)")) {
                        Imagem imagem = new Imagem();
                        try {
                            imagem.setImagem(Files.readAllBytes(arquivo.toPath()));
                        } catch (IOException ex) {
                            Logger.getLogger(GerenciarGrutaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        final int size = images.size();
                        images.add(imagem);
                        Platform.runLater(() -> {
                            if (images.size() < 8) {
                                adicionarFoto(imagem, size);
                            }
                        });
                    }
                }
            });
            uploadImagem.start();
            Button button = (Button) ae.getSource();
            button.setDisable(true);
            button.setText("Fazendo upload das fotos");
            new Thread(() -> {
                try {
                    uploadImagem.join();

                } catch (InterruptedException ex) {
                    Logger.getLogger(GerenciarGrutaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Platform.runLater(() -> {
                    carregarFotos();
                    button.setText("Adicionar Fotos");
                    button.setDisable(false);
                });
            }).start();
        }
    }

    @FXML
    private void btRemoverFotosActionEvent(ActionEvent ae) {

    }

    void carregarGruta() {
        if (gruta.getId() == null) {
            tfNome.clear();
            taDescricaoMapa.clear();
        } else {
            tfNome.setText(gruta.getNome());
            taDescricaoMapa.setText(gruta.getNome());
            carregarFotos();
        }
    }

    private void carregarFotos() {
        gpFoto.getChildren().clear();
        pgImagem.setPageCount(Math.round(images.size() / 8));
        if (pgImagem.getPageCount() == 0) {
            pgImagem.setPageCount(1);
        }
        int numberOfObject = 0;
        int inicio = 8 * pgImagem.getCurrentPageIndex();
        for (Imagem image : images.subList(inicio, inicio + 8 > images.size() ? images.size() : inicio + 8)) {
            adicionarFoto(image, numberOfObject);
            numberOfObject++;
        }
    }

    private void adicionarFoto(Imagem image, int numberOfObject) {
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setFitHeight(gpFoto.getWidth() / 4 - 40);
        imageView.setFitWidth(gpFoto.getWidth() / 4 - 40);
        imageView.setImage(new Image(new ByteArrayInputStream(image.getImagem())));
        gpFoto.add(imageView, numberOfObject % 4, numberOfObject / 4);
    }
}