/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.FxManager;
import br.com.PalleorrotasDePeterLund.control.ImageFactory;
import br.com.PalleorrotasDePeterLund.control.Message;
import br.com.PalleorrotasDePeterLund.control.Sessao;
import br.com.PalleorrotasDePeterLund.control.dao.GrutaDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Gruta;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class InicioController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Hyperlink hlLogin;
    @FXML
    private HBox hbUsuario;
    @FXML
    private Label lbUsuario;
    @FXML
    private ImageView ivFoto;
    @FXML
    private SplitMenuButton smbGrutas;
    @FXML
    private ScrollPane spConteudo;

    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        atualizarLogin();
        for (Gruta gruta : new GrutaDAO().pegarPorMuseu(false)) {
            MenuItem menuItem = new MenuItem(gruta.getNome());
            
            ImageView imageView=new ImageView(ImageFactory.loadImage("caverna.png"));
            imageView.setFitHeight(35);
            imageView.setPreserveRatio(true);
            menuItem.setGraphic(imageView);
            menuItem.setOnAction((ActionEvent event) -> {
                apPrincipal.getScene().setRoot(FxManager.carregarComponente("MostrarConteudo", FxManager.carregarComponente("VisualizarGruta", gruta)));
            });
            smbGrutas.getItems().add(menuItem);
        }
        Platform.runLater(() -> {
            apPrincipal.getScene().getWindow().setOnCloseRequest((WindowEvent event) -> btSairActionEvent(null));
            stage = (Stage) apPrincipal.getScene().getWindow();
        });
        spConteudo.setContent(FxManager.carregarComponente("PeterLund"));

    }

    @FXML
    private void btSairActionEvent(ActionEvent ae) {
        stage.close();
        FxManager.carregarJanela(FxManager.carregarComponente("Sobre"), "Sobre o aplicativo", FxManager.Tipo.EXIT_ON_CLOSE, FxManager.Tipo.UNDECORATED).show();
    }

    @FXML
    private void btLoginActionEvent(ActionEvent ae) {
        if (Sessao.usuario == null) {
            FxManager.carregarJanela(FxManager.carregarComponente("Login"), "Autenticação", FxManager.Tipo.MODAL, FxManager.Tipo.UNRESIZABLE).showAndWait();
            atualizarLogin();
        } else {
            Sessao.usuario = null;
            atualizarLogin();
        }
    }

    @FXML
    private void lbUsuarioMouseEvent(MouseEvent mouseEvent) {
        if (Sessao.usuario != null) {
            apPrincipal.getScene().setRoot(FxManager.carregarComponente("MostrarConteudo", FxManager.carregarComponente("GerenciarAplicativo")));
        }
    }

    @FXML
    private void btQuestaoActionEvent(ActionEvent ae) {
        if (Sessao.usuario != null) {
            apPrincipal.getScene().setRoot(FxManager.carregarComponente("MostrarConteudo", FxManager.carregarComponente("ResponderQuestao")));
        } else {
            Message.mostrarMessage("Usuário não autenticado", "É necessária fazer login para acessar está área!", Message.Tipo.ERRO);
        }
    }

    @FXML
    private void btCuriosidadesActionEvent(ActionEvent ae) {

    }

    @FXML
    private void btGlossarioActionEvent(ActionEvent ae) {
        apPrincipal.getScene().setRoot(FxManager.carregarComponente("MostrarConteudo", FxManager.carregarComponente("Glossario")));
    }

    @FXML
    private void btMapaActionEvent(ActionEvent ae) {
        apPrincipal.getScene().setRoot(FxManager.carregarComponente("MostrarConteudo", FxManager.carregarComponente("MapaInterativo")));
    }

    @FXML
    private void btGeologiaRegionalActionEvent(ActionEvent ae) {
        apPrincipal.getScene().setRoot(FxManager.carregarComponente("MostrarConteudo", FxManager.carregarComponente("GeologiaRegional")));
    }

    @FXML
    private void btFormacaoGrutaActionEvent(ActionEvent ae) {
        apPrincipal.getScene().setRoot(FxManager.carregarComponente("MostrarConteudo", FxManager.carregarComponente("FormacaoGruta")));
    }

    @FXML
    private void btMuseuActionEvent(ActionEvent ae) {
        List<Gruta> grutas = new GrutaDAO().pegarPorMuseu(true);
        if (!grutas.isEmpty()) {
            apPrincipal.getScene().setRoot(FxManager.carregarComponente("MostrarConteudo", FxManager.carregarComponente("VisualizarGruta", grutas.get(0))));
        } else {
            Message.mostrarMessage("Museu não encontrado", "Não foi possível encontrar nenhum museu\n em nossa base de dados", Message.Tipo.ERRO);
        }
    }

    @FXML
    private void miLuziaActionEvent(ActionEvent ae) {
        apPrincipal.getScene().setRoot(FxManager.carregarComponente("MostrarConteudo", FxManager.carregarComponente("Luzia")));
    }
    
    @FXML
    private void miPovoLuziaActionEvent(ActionEvent ae) {
        apPrincipal.getScene().setRoot(FxManager.carregarComponente("MostrarConteudo", FxManager.carregarComponente("PovoDeLuzia")));
    }
    
    private void atualizarLogin() {
        if (Sessao.usuario != null) {
            lbUsuario.setText(Sessao.usuario.getNome());
            if (Sessao.usuario.getFoto() != null) {
                ivFoto.setImage(new Image(new ByteArrayInputStream(Sessao.usuario.getFoto())));
            }
            hlLogin.setText("Logout");
        } else {
            lbUsuario.setText("");
            ivFoto.setImage(null);
            hlLogin.setText("Login");
        }
    }

}
