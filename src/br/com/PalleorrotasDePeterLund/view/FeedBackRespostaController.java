/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.FxManager;
import br.com.PalleorrotasDePeterLund.model.entity.Resposta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class FeedBackRespostaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Label lbTexto;

    private Resposta resposta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            resposta = (Resposta) apPrincipal.getUserData();
            if (resposta.getAlternativa().isCorreta()) {
                lbTexto.setText("Parabéns!\n"
                        + "Você acertou faça mais questões para \n"
                        + "conquistar mais pontos!");
            } else {
                lbTexto.setText("Ops, parece que você errou!\n"
                        + "Leia as informações espalhadas pelo aplicativo\n"
                        + " e consiga seus pontos!");
            }
        });
    }

    @FXML
    private void btProximaActionEvent(ActionEvent ae) {
        ((ScrollPane) apPrincipal.getParent().getParent().getParent()).setContent(FxManager.carregarComponente("ResponderQuestao"));
    }

}
