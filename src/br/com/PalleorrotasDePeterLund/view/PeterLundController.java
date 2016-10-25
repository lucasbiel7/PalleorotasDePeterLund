/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.dao.PeterLundDAO;
import br.com.PalleorrotasDePeterLund.model.entity.PeterLund;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;
import org.controlsfx.control.InfoOverlay;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class PeterLundController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Label lbTitulo;
    @FXML
    private StackPane spOverLay;
    @FXML
    private WebView wvTexto;
    private InfoOverlay ioFoto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ImageView imageView = new ImageView();
        try {
            PeterLund peterLund = new PeterLundDAO().pegarTodos().get(0);
            lbTitulo.setText(peterLund.getTitulo());
            wvTexto.getEngine().loadContent("<html><head><style>"
                    + "body {"
                    + "background-color: #EFFEBA;"
                    + "color:black;"
                    + "}"
                    + "div{"
                    + "text-align: justify;"
                    + "}"
                    + "</style>"
                    + "<head><body><div>" + peterLund.getConteudo());
            if (peterLund.getFoto() != null) {
                imageView.setImage(new Image(new ByteArrayInputStream(peterLund.getFoto())));
            }
            ioFoto = new InfoOverlay(imageView, peterLund.getDescricaoFoto());
            spOverLay.getChildren().add(ioFoto);
        } catch (IndexOutOfBoundsException e) {
            lbTitulo.setText("Sem conteúdo adicionado na base de dados");
        }
    }

}
