/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.Formatter;
import br.com.PalleorrotasDePeterLund.control.Message;
import br.com.PalleorrotasDePeterLund.control.Seguranca;
import br.com.PalleorrotasDePeterLund.control.dao.UsuarioDAO;
import br.com.PalleorrotasDePeterLund.model.Perfil;
import br.com.PalleorrotasDePeterLund.model.entity.Usuario;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class GerenciarUsuarioController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfLogin;
    @FXML
    private PasswordField pfSenha;
    @FXML
    private DatePicker dpDataNascimento;
    @FXML
    private ImageView ivFoto;
    @FXML
    private Label lbFoto;
    @FXML
    private Label lbTitulo;

    private Usuario usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario = new Usuario();
        Platform.runLater(() -> {
            if (apPrincipal.getUserData() != null) {
                usuario = (Usuario) apPrincipal.getUserData();
                lbTitulo.setText("Editar perfil");
            }
            carregarUsuario();
        });

        lbFoto.setOnDragOver((DragEvent event) -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        });
        lbFoto.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                List<File> files = event.getDragboard().getFiles();
                if (!files.isEmpty()) {
                    try {
                        usuario.setFoto(Files.readAllBytes(files.get(0).toPath()));
                        ivFoto.setImage(new Image(new ByteArrayInputStream(usuario.getFoto())));
                    } catch (IOException ex) {
                        Logger.getLogger(GerenciarUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent ae) {
        usuario.setNome(tfNome.getText());
        usuario.setEmail(tfEmail.getText());
        usuario.setLogin(tfLogin.getText());
        usuario.setSenha(Seguranca.encriptar(pfSenha.getText()));
        usuario.setDataDeNascimento(Formatter.toDate(dpDataNascimento.getValue()));

        if (usuario.getId() == null) {
            usuario.setPerfil(Perfil.COMUM);
            new UsuarioDAO().cadastrar(usuario);
            Platform.runLater(() -> {
                Message.mostrarMessage("Cadastro de usuário", "Parabéns " + usuario.getNome() + "!\nCadastro realizado com sucesso", Message.Tipo.INFORMACAO);

            });
        } else {
            new UsuarioDAO().editar(usuario);
            Platform.runLater(() -> {
                Message.mostrarMessage("Editar perfil", "Olá " + usuario.getNome() + "!\nDados do perfil editado com sucesso!", Message.Tipo.INFORMACAO);
            });
        }
        btCancelarActionEvent(ae);
    }

    @FXML
    private void btCancelarActionEvent(ActionEvent ae) {
        ((Stage) apPrincipal.getScene().getWindow()).close();
    }

    private void carregarUsuario() {
        if (usuario != null) {
            tfNome.setText(usuario.getNome());
            tfLogin.setText(usuario.getLogin());
            tfEmail.setText(usuario.getEmail());
            dpDataNascimento.setValue(Formatter.toLocalDate(usuario.getDataDeNascimento()));
            if (usuario.getFoto() != null) {
                ivFoto.setImage(new Image(new ByteArrayInputStream(usuario.getFoto())));
            }
        }
    }

}
