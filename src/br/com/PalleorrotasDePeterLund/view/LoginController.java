/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.FxManager;
import br.com.PalleorrotasDePeterLund.control.Sessao;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btLoginActionEvent(ActionEvent ae) {

    }

    @FXML
    private void btRegistrarActionEvent(ActionEvent ae) {
        FxManager.carregarJanela(FxManager.carregarComponente("GerenciarUsuario"), "Novo usuário", FxManager.Tipo.MODAL).showAndWait();
    }

    @FXML
    private void btLoginFacebookActionEvent(ActionEvent ae) {
        FxManager.carregarJanela(FxManager.carregarComponente("AutenticacaoFacebook"), "Autenticar com Facebook", FxManager.Tipo.MODAL).showAndWait();
        if (Sessao.usuario != null) {
            ((Stage) apPrincipal.getScene().getWindow()).close();
        }
    }
}
