/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.control;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import org.controlsfx.control.Notifications;

/**
 *
 * @author lucas
 */
public class Message {

    public static String caixaDeTexto(String titulo, String msg) {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle(titulo);
        textInputDialog.setContentText(msg);
        return textInputDialog.showAndWait().orElse("");
    }

    public enum Tipo {
        INFORMACAO, ERRO, ALERTA,
    }

    public static void mostrarMessage(String title, String msg, Tipo tipo) {
        Notifications notifications = Notifications.create();
        notifications.title(title);
        notifications.text(msg);
        switch (tipo) {
            case ALERTA:
                notifications.showWarning();
                break;
            case ERRO:
                notifications.showError();
                break;
            case INFORMACAO:
                notifications.showInformation();
                break;
        }
    }

    public static boolean mostrarConfirmacao(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO);
        alert.setTitle(titulo);
        return alert.showAndWait().orElse(ButtonType.NO).equals(ButtonType.YES);
    }
}
