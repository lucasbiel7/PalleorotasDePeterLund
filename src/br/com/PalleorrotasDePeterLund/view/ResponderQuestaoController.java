/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.Sessao;
import br.com.PalleorrotasDePeterLund.control.dao.AlternativaDAO;
import br.com.PalleorrotasDePeterLund.control.dao.QuestaoDAO;
import br.com.PalleorrotasDePeterLund.control.dao.RespostaDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Alternativa;
import br.com.PalleorrotasDePeterLund.model.entity.Questao;
import br.com.PalleorrotasDePeterLund.model.entity.Resposta;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class ResponderQuestaoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbConteudo;
    @FXML
    private VBox vbConteudo;
    @FXML
    private Button btConfirmarResposta;

    private Questao questao;
    private Alternativa alternativa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Questao> questaos = new QuestaoDAO().pegarTodos();
        List<Resposta> respostas = new RespostaDAO().pegarPorUsuario(Sessao.usuario);
        questaos.removeAll(respostas.stream().map(Resposta::getId).map(Resposta.RespostaID::getQuestao).collect(Collectors.toList()));
        if (questaos.isEmpty()) {
            lbConteudo.setText("Parabéns!!\n"
                    + "Você já respondeu todas as questões!");
            lbConteudo.setAlignment(Pos.CENTER);
            btConfirmarResposta.setVisible(false);
        } else {
            questao = questaos.get(new Random().nextInt(questaos.size()));
            lbConteudo.setText(questao.getEnunciado());
            List<Alternativa> alternativas = new AlternativaDAO().pegarPorQuestao(questao);
            ToggleGroup group = new ToggleGroup();
            for (Alternativa alternativa : alternativas) {
                RadioButton radioButton = new RadioButton(alternativa.getConteudo());
                radioButton.setToggleGroup(group);
                vbConteudo.getChildren().add(radioButton);
            }
        }
    }

    @FXML
    private void btConfirmarRespostaActionEvent(ActionEvent ae) {

    }

    @FXML
    private void btRankingActionEvent(ActionEvent ae) {

    }
}
