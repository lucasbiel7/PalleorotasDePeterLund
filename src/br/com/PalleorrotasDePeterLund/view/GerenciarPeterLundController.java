/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.dao.PeterLundDAO;
import br.com.PalleorrotasDePeterLund.model.entity.PeterLund;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class GerenciarPeterLundController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TextField tfTitulo;
    @FXML
    private TextArea taConteudo;
    @FXML
    private ImageView ivFoto;
    @FXML
    private TextField tfDescricaoFoto;

    private PeterLund peterLund;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        List<PeterLund> peterLunds = new PeterLundDAO().pegarTodos();
        if (peterLunds.isEmpty()) {
            peterLund = new PeterLund();
            new PeterLundDAO().cadastrar(peterLund);
        } else {
            peterLund = peterLunds.get(0);
            tfTitulo.setText(peterLund.getTitulo());
            taConteudo.setText(peterLund.getConteudo());
            if (peterLund.getFoto() != null) {
                ivFoto.setImage(new Image(new ByteArrayInputStream(peterLund.getFoto())));
            }
            tfDescricaoFoto.setText(peterLund.getDescricaoFoto());
        }
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent ae) {
        peterLund.setTitulo(tfTitulo.getText());
        peterLund.setConteudo(taConteudo.getText());
        peterLund.setDescricaoFoto(tfDescricaoFoto.getText());
        new PeterLundDAO().editar(peterLund);
    }

    @FXML
    private void ivFotoDragEventOver(DragEvent de) {
        if (de.getDragboard().hasFiles()) {
            de.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    private void ivFotoDragEventDropped(DragEvent de) {
        if (de.getDragboard().hasFiles()) {
            for (File file : de.getDragboard().getFiles()) {
                if (file.getName().matches("^(.*\\.(jpg|png|JPG|PNG))$")) {
                    try {
                        String extensao = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                        peterLund.setFoto(Files.readAllBytes(file.toPath()));
                    } catch (IOException ex) {
                        Logger.getLogger(GerenciarPeterLundController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        ivFoto.setImage(new Image(new ByteArrayInputStream(peterLund.getFoto())));
    }
}
