/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.Message;
import br.com.PalleorrotasDePeterLund.control.dao.AlternativaDAO;
import br.com.PalleorrotasDePeterLund.control.dao.QuestaoDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Alternativa;
import br.com.PalleorrotasDePeterLund.model.entity.Questao;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private List<Alternativa> alternativas;
    private Alternativa alternativa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Carregar questões
        tvQuestoes.getItems().setAll(new QuestaoDAO().pegarTodos());
        spPontuacao.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 10));
        tvQuestoes.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Questao> observable, Questao oldValue, Questao newValue) -> {
            questao = newValue;
            if (newValue == null) {
                questao = new Questao();
                alternativas.clear();
            } else {
                alternativas = new AlternativaDAO().pegarPorQuestao(questao);
            }
            carregarQuestao();
        });
        tvAlternativas.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Alternativa> observable, Alternativa oldValue, Alternativa newValue) -> {
            alternativa = newValue;
            if (newValue == null) {
                alternativa = new Alternativa();
            }
            carregarAlternativa();
        });
        //Configurando tabelas
        //Alternativa
        tcAConteudo.setCellValueFactory(new PropertyValueFactory("conteudo"));
        tcCorreta.setCellValueFactory(new PropertyValueFactory("correta"));
        tcCorreta.setCellFactory((TableColumn<Alternativa, Boolean> param) -> new TableCell<Alternativa, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                if (empty) {
                    setText("");
                    setGraphic(null);
                } else {
                    CheckBox cbCorreta = new CheckBox();
                    cbCorreta.setSelected(item);
                    setGraphic(cbCorreta);
                }
            }
        });
        //Questao
        tcConteudo.setCellValueFactory(new PropertyValueFactory<>("enunciado"));
        tcPontuacao.setCellValueFactory(new PropertyValueFactory<>("pontuacao"));
        questao = new Questao();
        alternativa = new Alternativa();
        alternativas = new ArrayList<>();
    }

    @FXML

    private void btAdicionarAlternativaActionEvent(ActionEvent ae) {
        if (rbAlternativaCorreta.isSelected()) {
            for (Alternativa outradAlternativas : alternativas) {
                outradAlternativas.setCorreta(false);
            }
        }
        alternativa.setConteudo(tfAlternativa.getText());
        alternativa.setQuestao(questao);
        alternativa.setCorreta(rbAlternativaCorreta.isSelected());
        if (!alternativas.contains(alternativa) || alternativa.getId() == null) {
            alternativas.add(alternativa);
        }
        Message.mostrarMessage("Adicionado alternativa", "Alternativa foi adicionada com sucesso!", Message.Tipo.INFORMACAO);
        alternativa = new Alternativa();
        carregarAlternativa();
        tvAlternativas.getItems().setAll(alternativas);
    }

    @FXML
    private void miExcluirAlternativaActionEvent(ActionEvent ae) {
        alternativa = tvAlternativas.getSelectionModel().getSelectedItem();
        if (alternativa != null) {
            if (alternativa.getId() != null) {
                new AlternativaDAO().excluir(alternativa);
            }
            alternativas.remove(alternativa);
        }
        alternativa = new Alternativa();
        carregarAlternativa();
        tvAlternativas.getItems().setAll(alternativas);
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent ae) {
        questao.setEnunciado(taConteudo.getText());
        questao.setPontuacao(spPontuacao.getValue());
        if (questao.getId() == null) {
            new QuestaoDAO().cadastrar(questao);
        } else {
            new QuestaoDAO().editar(questao);
        }
        for (Alternativa alternativa : alternativas) {
            if (alternativa.getId() == null) {
                new AlternativaDAO().cadastrar(alternativa);
            } else {
                new AlternativaDAO().editar(alternativa);
            }
        }
        Message.mostrarMessage("Questão salva", "Todos os dados da questão foram salvos com sucesso!", Message.Tipo.INFORMACAO);
        tvQuestoes.getItems().setAll(new QuestaoDAO().pegarTodos());
        questao = new Questao();
        alternativas = new ArrayList<>();
        carregarQuestao();
    }

    @FXML
    private void miExcluiQuestaoActionEvent(ActionEvent ae) {
        if (questao != null) {
            System.out.println("excluir");
            new QuestaoDAO().excluir(questao);
            Message.mostrarMessage("Questão excluida", "Todos os registros ligado a está questão foram excluidos", Message.Tipo.INFORMACAO);
            tvQuestoes.getItems().setAll(new QuestaoDAO().pegarTodos());
            questao = new Questao();
            alternativas = new ArrayList<>();
            carregarQuestao();
        }
    }

    private void carregarQuestao() {
        taConteudo.setText(questao.getEnunciado());
        spPontuacao.getValueFactory().setValue(questao.getPontuacao());
        if (questao.getId() != null) {
            tvAlternativas.getItems().setAll(alternativas);
        } else {
            tvAlternativas.getItems().clear();
        }
    }

    private void carregarAlternativa() {
        tfAlternativa.setText(alternativa.getConteudo());
        rbAlternativaCorreta.setSelected(alternativa.isCorreta());
    }

}
