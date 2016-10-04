/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.control;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;

/**
 *
 * @author lucas
 */
public class FacebookAuth {

    public static String ID_APP = "1769034663357176";
    public static String SECRET_APP = "52155bdf681ce07fc2bc7a1ae895d145";

    public static void acess() {
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(ID_APP, SECRET_APP);
        
//        facebook.setOAuthAccessToken();
//        facebook.getOAuthAccessToken();
        

    }
}
