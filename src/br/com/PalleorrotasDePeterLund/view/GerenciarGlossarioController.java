/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.Message;
import br.com.PalleorrotasDePeterLund.control.dao.AreaDAO;
import br.com.PalleorrotasDePeterLund.control.dao.GlossarioDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Area;
import br.com.PalleorrotasDePeterLund.model.entity.Glossario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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
public class GerenciarGlossarioController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TextField tfPalavra;
    @FXML
    private TextArea taSignificado;
    @FXML
    private ComboBox<Area> cbArea;
    @FXML
    private TableView<Glossario> tvGlossario;
    @FXML
    private TableColumn<Glossario, String> tcPalavra;
    @FXML
    private TableColumn<Glossario, Area> tcArea;

    private Glossario glossario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbArea.getItems().setAll(new AreaDAO().pegarTodos());
        glossario = new Glossario();
        tvGlossario.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Glossario> observable, Glossario oldValue, Glossario newValue) -> {
            if (newValue == null) {
                glossario = new Glossario();
            } else {
                glossario = newValue;
            }
            carregarGlossario();
        });
        tcPalavra.setCellValueFactory(new PropertyValueFactory<>("palavra"));
        tcArea.setCellValueFactory(new PropertyValueFactory<>("area"));
        tvGlossario.getItems().setAll(new GlossarioDAO().pegarTodos());
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent ae) {
        glossario.setPalavra(tfPalavra.getText());
        glossario.setSignificado(taSignificado.getText());
        glossario.setArea(cbArea.getSelectionModel().getSelectedItem());
        if (glossario.getId() == null) {
            new GlossarioDAO().cadastrar(glossario);
        } else {
            new GlossarioDAO().editar(glossario);
        }
        Message.mostrarMessage("Dados salvos", "Foi adicionado ao glossário a palavra " + glossario.getPalavra(), Message.Tipo.INFORMACAO);
        btNovoActionEvent(ae);
    }

    @FXML
    private void btNovoActionEvent(ActionEvent ae) {
        tvGlossario.getItems().setAll(new GlossarioDAO().pegarTodos());
        glossario = new Glossario();
        carregarGlossario();
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent ae) {
        if (glossario != null) {
            if (Message.mostrarConfirmacao("Excluir palavra", "Você realmente deseja excluir a palavra " + glossario.getPalavra() + " do glossário?")) {
                new GlossarioDAO().excluir(glossario);
                btNovoActionEvent(ae);
            }
        } else {
            Message.mostrarMessage("Selecione", "É necessário selecionar a palavra a ser removida do glossário", Message.Tipo.ERRO);
        }
    }

    private void carregarGlossario() {
        tfPalavra.setText(glossario.getPalavra());
        taSignificado.setText(glossario.getSignificado());
        cbArea.getSelectionModel().select(glossario.getArea());

    }
}
