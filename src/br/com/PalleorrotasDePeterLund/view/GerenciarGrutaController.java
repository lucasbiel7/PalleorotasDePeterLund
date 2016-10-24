/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.FxManager;
import br.com.PalleorrotasDePeterLund.control.ImageManipulation;
import br.com.PalleorrotasDePeterLund.control.Message;
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
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Button btRemoverFoto;
    @FXML
    private GridPane gpFoto;
    @FXML
    private Pagination pgImagem;
    @FXML
    private SplitMenuButton smb;
    private Gruta gruta;
    private List<Imagem> images;

    private FileChooser fcImagem;

    private ContextMenu cmMenu;

    private MenuItem miAdicionarFotos;
    private boolean removerFoto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fcImagem = new FileChooser();
        gruta = new Gruta();
        cmMenu = new ContextMenu();
        miAdicionarFotos = new MenuItem("Adicionar conjunto de imagens");
        cmMenu.getItems().add(miAdicionarFotos);
        tvGruta.getItems().setAll(new GrutaDAO().pegarTodos());
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tvGruta.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Gruta> observable, Gruta oldValue, Gruta newValue) -> {
            if (newValue == null) {
                gruta = new Gruta();
                images.clear();
            } else {
                gruta = newValue;
                images = new GrutaImagemDAO().pegarPorGruta(gruta).stream().map(GrutaImagem::getId).map(GrutaImagem.GrutaImagenID::getImagem).collect(Collectors.toList());
            }
            carregarGruta();
        });
        images = new ArrayList<>();
        carregarFotos();
        pgImagem.currentPageIndexProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            carregarFotos();
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
            if (new GrutaImagemDAO().pegarPorId(grutaImagem.getId()) == null) {
                new GrutaImagemDAO().cadastrar(grutaImagem);
            }
        }
        Message.mostrarMessage("Salvo com sucesso", "Dados da gruta foram salvos com sucesso", Message.Tipo.INFORMACAO);
        gruta = new Gruta();
        images.clear();
        carregarGruta();
        tvGruta.getItems().setAll(new GrutaDAO().pegarTodos());
    }

    @FXML
    private void btNovoActionEvent(ActionEvent ae) {
        gruta = new Gruta();
        images.clear();
        carregarGruta();
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
                            String extensao = arquivo.getName().substring(arquivo.getName().lastIndexOf(".") + 1);
                            System.out.println(extensao);
                            imagem.setImagem(ImageManipulation.imageReductor(Files.readAllBytes(arquivo.toPath()), extensao));
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
        removerFoto = !removerFoto;
        btRemoverFoto.setText(removerFoto ? "Clique nas fotos para remover\n"
                + "Ao concluir clique aqui" : "Remover foto");

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
        final ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setFitHeight(gpFoto.getWidth() / 4 - 40);
        imageView.setFitWidth(gpFoto.getWidth() / 4 - 40);
        imageView.setImage(new Image(new ByteArrayInputStream(image.getImagem())));
        imageView.setOnMouseReleased((MouseEvent event) -> {
            if (event.isPopupTrigger()) {
                cmMenu.show(imageView, event.getSceneX(), event.getSceneY());
                miAdicionarFotos.setOnAction((ActionEvent event1) -> {
                    FxManager.carregarJanela(FxManager.carregarComponente("GerenciarImagem360", image), "Manipular imagens 360", FxManager.Tipo.MODAL).showAndWait();
                });
            } else if (removerFoto) {
                if (image.getId() != null) {
                    new ImagemDAO().excluir(image);
                }
                images.remove(image);
                Message.mostrarMessage("Imagem removida", "A imagem foi excluida com sucesso!", Message.Tipo.INFORMACAO);
                carregarFotos();
            }
        });
        gpFoto.add(imageView, numberOfObject % 4, numberOfObject / 4);
    }

}
