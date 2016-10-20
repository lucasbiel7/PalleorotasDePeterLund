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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class GerenciarAplicativoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ScrollPane spContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btPerfilActionEvent(ActionEvent ae) {
        spContainer.setContent(FxManager.carregarComponente("GerenciarUsuario", Sessao.usuario));
    }

    @FXML
    private void btGrutasActionEvent(ActionEvent ae) {
        spContainer.setContent(FxManager.carregarComponente("GerenciarGruta", Sessao.usuario));
    }

    @FXML
    private void btPeterLandActionEvent(ActionEvent ae) {
        
    }

}
