/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.control;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author lucas
 */
public class ImageManipulation {

    public static final int MAX_WIDTH = 600;
    public static final int MAX_HEIGHT = 400;

    public static byte[] imageReductor(byte[] imagem,String extensao) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagem));
            int factorWidth = bufferedImage.getWidth() / MAX_WIDTH;
            int factorHeight = bufferedImage.getHeight() / MAX_HEIGHT;
            int bestFactor = factorHeight > factorWidth ? factorHeight : factorWidth;
            Image image=bufferedImage.getScaledInstance(bufferedImage.getWidth() / bestFactor, bufferedImage.getHeight() / bestFactor, Image.SCALE_FAST);
            BufferedImage imagemConvertida=new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
            imagemConvertida.createGraphics().drawImage(image, null, null);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(imagemConvertida,extensao,out);
            bufferedImage.flush();
            imagemConvertida.flush();
            return out.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(ImageManipulation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imagem;
    }
}
