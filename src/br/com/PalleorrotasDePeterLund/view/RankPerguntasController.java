/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.dao.RespostaDAO;
import br.com.PalleorrotasDePeterLund.model.Pontuacao;
import br.com.PalleorrotasDePeterLund.model.entity.Questao;
import br.com.PalleorrotasDePeterLund.model.entity.Resposta;
import br.com.PalleorrotasDePeterLund.model.entity.Usuario;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class RankPerguntasController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private TableView<Pontuacao> tvUsuario;

    @FXML
    private TableColumn<Pontuacao, String> tcNome;
    @FXML
    private TableColumn<Pontuacao, Integer> tcPontuacao;
    @FXML
    private TableColumn<Pontuacao, Integer> tcColocao;
    @FXML
    private TextField tfNome;
    private List<Pontuacao> pontuacaos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Map<Usuario, List<Resposta>> usuarios = new RespostaDAO().pegarTodos().stream().collect(Collectors.groupingBy((Resposta t) -> t.getId().getUsuario()));
        pontuacaos = new ArrayList<>();
        for (Usuario usuario : usuarios.keySet()) {
            Pontuacao pontuacao = new Pontuacao();
            pontuacao.setUsuario(usuario);
            pontuacao.setPontuacao(usuarios.get(usuario).stream().filter((Resposta t) -> t.getAlternativa().isCorreta()).map(Resposta::getId).map(Resposta.RespostaID::getQuestao).mapToInt(Questao::getPontuacao).sum());
            pontuacaos.add(pontuacao);
        }
        pontuacaos.sort((Pontuacao o1, Pontuacao o2) -> Integer.compare(o1.getPontuacao(), o2.getPontuacao()));
        for (Pontuacao pontuacao : pontuacaos) {
            pontuacao.setColocacao(pontuacaos.indexOf(pontuacao) + 1);
        }
        tvUsuario.getItems().setAll(pontuacaos);
        tcNome.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        tcColocao.setCellValueFactory(new PropertyValueFactory<>("colocacao"));
        tcPontuacao.setCellValueFactory(new PropertyValueFactory<>("pontuacao"));
        tcColocao.setCellFactory((TableColumn<Pontuacao, Integer> param) -> new TableCell<Pontuacao, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                if (empty) {
                    setText("");
                } else {
                    setText(item + "º");
                }
            }
        });
    }

    @FXML
    private void tfNomeKeyEvent(KeyEvent keyEvent) {
        tvUsuario.getItems().setAll(pontuacaos.stream().filter((Pontuacao t) -> t.getUsuario().getNome().toLowerCase().contains(tfNome.getText().toLowerCase())).collect(Collectors.toList()));
    }
}
