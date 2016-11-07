/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.control;

import javafx.scene.image.Image;

/**
 *
 * @author lucas
 */
public class ImageFactory {

    public static final String VIEW = "/br.com.PalleorrotasDePeterLund.view/".replace(".", "/");

    public static Image loadImage(String image) {
        return new Image(ImageFactory.class.getResourceAsStream(VIEW + "image/" + image));
    }
}
