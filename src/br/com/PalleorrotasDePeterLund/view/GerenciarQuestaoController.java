/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.dao.QuestaoDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Alternativa;
import br.com.PalleorrotasDePeterLund.model.entity.Questao;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class GerenciarQuestaoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TextArea taConteudo;
    @FXML
    private Spinner<Integer> spPontuacao;
    @FXML
    private TextField tfAlternativa;
    @FXML
    private RadioButton rbAlternativaCorreta;
    @FXML
    private TableView<Questao> tvQuestoes;
    @FXML
    private TableColumn<Questao, String> tcConteudo;
    @FXML
    private TableColumn<Questao, Integer> tcPontuacao;
    @FXML
    private TableView<Alternativa> tvAlternativas;
    @FXML
    private TableColumn<Alternativa, String> tcAConteudo;
    @FXML
    private TableColumn<Alternativa, Boolean> tcCorreta;

    private Questao questao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvQuestoes.getItems().setAll(new QuestaoDAO().pegarTodos());
        tvQuestoes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Questao>() {
            @Override
            public void changed(ObservableValue<? extends Questao> observable, Questao oldValue, Questao newValue) {
                questao = newValue;
                if (newValue == null) {
                    questao = new Questao();
                }
                carregarQuestao();
            }

        });
    }

    @FXML
    private void btAdicionarAlternativaActionEvent(ActionEvent ae) {

    }

    @FXML
    private void btExcluirAlternativaActionEvent(ActionEvent ae) {

    }

    @FXML
    private void btSalvarActionEvent(ActionEvent ae) {

    }

    private void carregarQuestao() {
        
    }
}
