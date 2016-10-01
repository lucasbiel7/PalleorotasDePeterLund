/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.control;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author lucas
 */
public class FxManager {

    public static final String VIEW = "/br.com.PalleorrotasDePeterLund.control/".replace("control", "view").replace(".", "/");

    public enum Tipo {
        EXIT_ON_CLOSE, MAXIMIZED, MODAL, UNRESIZABLE;
    }

    //Carregar componentes fxmls
    public static Parent carregarComponente(String fxml) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(FxManager.class.getResource(VIEW + fxml + ".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FxManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parent;
    }

    public static Parent carregarComponente(String fxml, Object data) {
        Parent parent = carregarComponente(fxml);
        parent.setUserData(data);
        return parent;
    }

    //Criar stages 
    public static Stage carregarJanela(Parent parent, String title, Tipo... tipos) {
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle(title);
        
        for (Tipo tipo : tipos) {
            switch (tipo) {
                case EXIT_ON_CLOSE:
                    stage.setOnCloseRequest((WindowEvent event) -> {
                        Platform.exit();
                        System.exit(0);
                    });
                    break;
                case MAXIMIZED:
                    stage.setMaximized(true);
                    break;
                case MODAL:
                    stage.initModality(Modality.APPLICATION_MODAL);
                    break;
                case UNRESIZABLE:
                    stage.setResizable(false);
                    break;
            }
        }

        return stage;
    }

}
