/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.dao.Imagem360DAO;
import br.com.PalleorrotasDePeterLund.model.entity.Imagem;
import br.com.PalleorrotasDePeterLund.model.entity.Imagem360;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class GerenciarImagem360Controller implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    private Imagem imagem;

    private List<Imagem> imagens;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagens = new ArrayList<>();
        Platform.runLater(() -> {
            this.imagem = (Imagem) apPrincipal.getUserData();
            imagens = new Imagem360DAO().pegarPorPrincipal(imagem).stream().map(Imagem360::getId).map(Imagem360.Imagem360Id::getComposicao).collect(Collectors.toList());
        });
    }

    @FXML
    private void btRemoverFotosActionEvent(ActionEvent ae) {
        
    }

    @FXML
    private void btAdicionarFotosActionEvent(ActionEvent ae) {
        
    }

}
