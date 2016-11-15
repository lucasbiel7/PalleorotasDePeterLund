/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.FxManager;
import br.com.PalleorrotasDePeterLund.control.Sessao;
import br.com.PalleorrotasDePeterLund.control.dao.UsuarioDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Usuario;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class LoaderController implements Initializable {

    @FXML
    private Label lbCarregando;
    private Timeline loader;
    @FXML
    private AnchorPane apPrincipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loader = new Timeline(new KeyFrame(Duration.seconds(2), (ActionEvent event) -> {
            lbCarregando.setText(lbCarregando.getText() + ".");
        }));
        loader.setCycleCount(4);
        loader.play();
        Thread thread = new Thread(() -> {
            List<Usuario> usuarios = new UsuarioDAO().pegarTodos();
            //Login Automatico
            Sessao.usuario=usuarios.get(0);
            
        });
        thread.start();
        loader.setOnFinished((ActionEvent event) -> {
            try {
                thread.join();
                ((Stage) apPrincipal.getScene().getWindow()).close();
                FxManager.carregarJanela(FxManager.carregarComponente("Inicio"), "Ínicio", FxManager.Tipo.MAXIMIZED).show();
            } catch (InterruptedException ex) {
                Logger.getLogger(LoaderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
