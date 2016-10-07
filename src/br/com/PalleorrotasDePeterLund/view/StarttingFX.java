/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.FacebookAuth;
import br.com.PalleorrotasDePeterLund.control.FxManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author lucas
 */
public class StarttingFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        FxManager.carregarJanela(FxManager.carregarComponente("Loader"), "Carregando", FxManager.Tipo.EXIT_ON_CLOSE, FxManager.Tipo.UNRESIZABLE,FxManager.Tipo.UNDECORATED).show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
