/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.view;

import br.com.PalleorrotasDePeterLund.control.Application;
import br.com.PalleorrotasDePeterLund.control.FacebookAuth;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.w3c.dom.Document;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class AutenticacaoFacebookController implements Initializable {

    @FXML
    private WebView webView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            final FacebookAuth facebookAuth = new FacebookAuth();
            File file = new File("teste.html");
            file.createNewFile();
            webView.getEngine().load("https://www.facebook.com/v2.7/dialog/oauth?client_id=" + Application.ID_APP + "&redirect_uri=https://www.facebook.com/connect/login_success.html&scope=public_profile,email,user_about_me,user_birthday&response_type=token");
            webView.getEngine().documentProperty().addListener((ObservableValue<? extends Document> observable, Document oldValue, Document newValue) -> {
                if (newValue != null) {
                    String uri = newValue.getBaseURI();
                    if (uri.contains("https://www.facebook.com/connect/login_success.html")) {
                        facebookAuth.setTokenCode(uri.substring(uri.indexOf("=") + 1));
                        String acessToken = uri.substring(uri.indexOf("access_token="), uri.indexOf("&")).replace("access_token=", "");
                        String expiresIn = uri.substring(uri.indexOf("expires_in=")).replace("expires_in=", "");
                        facebookAuth.setExpireDate(Long.parseLong(expiresIn));
                        facebookAuth.setTokenCode(acessToken);
                        ((Stage)webView.getScene().getWindow()).close();
                        facebookAuth.acess();
                    }
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(AutenticacaoFacebookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
