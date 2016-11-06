/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.dao.GlossarioDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Area;
import br.com.PalleorrotasDePeterLund.model.entity.Glossario;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class GlossarioController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private TextField tfPesquisa;
    @FXML
    private TextFlow tfContainer;
    @FXML
    private ScrollPane spContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarGlossario();
    }

    @FXML
    private void tfPesquisaKeyEvent(KeyEvent keyEvent) {
        carregarGlossario();
    }

    public void carregarGlossario() {
        tfContainer.getChildren().clear();
        List<Glossario> glossarios = new GlossarioDAO().pegarPorPesquisa(tfPesquisa.getText());
        Map<Area, List<Glossario>> glossarioOrganizado = glossarios.stream().collect(Collectors.groupingBy(Glossario::getArea));
        for (Area area : glossarioOrganizado.keySet()) {
            Text lbArea = new Text(area.getNome() + "\n");
            lbArea.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.EXTRA_BOLD, 25));
            tfContainer.getChildren().add(lbArea);
            for (Glossario glossario : glossarioOrganizado.get(area)) {
                Text lbPalavra = new Text(glossario.getPalavra() + ": ");
                lbPalavra.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 14));
                Text lbSignificado = new Text(glossario.getSignificado() + "\n");
                lbSignificado.setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, 14));
                tfContainer.getChildren().addAll(lbPalavra, lbSignificado);
            }
            tfContainer.getChildren().add(new Text("\n\n"));
        }
    }

}
