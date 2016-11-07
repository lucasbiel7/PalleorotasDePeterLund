/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.FxManager;
import br.com.PalleorrotasDePeterLund.control.Message;
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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextAlignment;

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
            lbConteudo.setContentDisplay(ContentDisplay.CENTER);
            lbConteudo.setAlignment(Pos.CENTER);
            lbConteudo.setTextAlignment(TextAlignment.CENTER);
            lbConteudo.prefWidthProperty().bind(vbConteudo.widthProperty());
            btConfirmarResposta.setVisible(false);
        } else {
            questao = questaos.get(new Random().nextInt(questaos.size()));
            lbConteudo.setText(questao.getEnunciado());
            List<Alternativa> alternativas = new AlternativaDAO().pegarPorQuestao(questao);
            ToggleGroup group = new ToggleGroup();
            for (Alternativa alternativa : alternativas) {
                final RadioButton radioButton = new RadioButton(alternativa.getConteudo());
                radioButton.setToggleGroup(group);
                radioButton.setFont(Font.font("system", FontPosture.REGULAR, 24));
                radioButton.setOnAction((ActionEvent event) -> {
                    if (radioButton.isSelected()) {
                        ResponderQuestaoController.this.alternativa = alternativa;
                    }
                });
                vbConteudo.getChildren().add(radioButton);
            }
        }
    }

    @FXML
    private void btConfirmarRespostaActionEvent(ActionEvent ae) {
        if (alternativa != null) {
            Resposta resposta = new Resposta();
            resposta.setId(new Resposta.RespostaID(questao, Sessao.usuario));
            resposta.setAlternativa(alternativa);
            new RespostaDAO().cadastrar(resposta);
            ((ScrollPane) apPrincipal.getParent().getParent().getParent()).setContent(FxManager.carregarComponente("FeedBackResposta", resposta));
        } else {
            Message.mostrarMessage("Alternativa inválida", "Para continuar e necessário selecionar uma alternativa!!", Message.Tipo.ERRO);
        }
    }

    @FXML
    private void btRankingActionEvent(ActionEvent ae) {
        ((ScrollPane) apPrincipal.getParent().getParent().getParent()).setContent(FxManager.carregarComponente("RankPerguntas"));
    }
}
