/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.Message;
import br.com.PalleorrotasDePeterLund.control.dao.AreaDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Area;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class GerenciarAreaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private TextField tfNome;
    @FXML
    private TableView<Area> tvArea;
    @FXML
    private TableColumn<Area, String> tcNome;
    private Area area;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        area = new Area();
        tvArea.getItems().setAll(new AreaDAO().pegarTodos());
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tvArea.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Area> observable, Area oldValue, Area newValue) -> {
            if (newValue == null) {
                area = new Area();
            } else {
                area = newValue;
            }
            carregarArea();
        });
    }

    @FXML
    private void btNovoActionEvent(ActionEvent ae) {
        area = new Area();
        carregarArea();
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent ae) {
        area.setNome(tfNome.getText());
        if (area.getId() == null) {
            new AreaDAO().cadastrar(area);
        } else {
            new AreaDAO().editar(area);
        }
        Message.mostrarMessage("Salvo alterações", "Alteração salvas na área " + area.getNome(), Message.Tipo.INFORMACAO);
        tvArea.getItems().setAll(new AreaDAO().pegarTodos());
        area = new Area();
        carregarArea();
    }

    private void carregarArea() {
        tfNome.setText(area.getNome());
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent ae) {
        if (area != null) {
            if (Message.mostrarConfirmacao("Excluir", "Deseja excluir a área " + area.getNome() + "?")) {
                new AreaDAO().excluir(area);
                tvArea.getItems().setAll(new AreaDAO().pegarTodos());
                area = new Area();
                carregarArea();
            }
        } else {
            Message.mostrarMessage("Selecione", "É necessário seleciona a área que deseja exclur!", Message.Tipo.ERRO);
        }
    }
}
