/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.control;

import br.com.PalleorrotasDePeterLund.control.dao.UsuarioDAO;
import br.com.PalleorrotasDePeterLund.model.Perfil;
import br.com.PalleorrotasDePeterLund.model.entity.Usuario;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javax.imageio.ImageIO;

/**
 *
 * @author lucas
 */
public class FacebookAuth {

    public String tokenCode;
    public long expireDate;

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }

    public void acess() {
        try {
            Facebook facebook = new FacebookFactory().getInstance();
            facebook.setOAuthAppId(Application.ID_APP, Application.SECRET_APP);
            facebook.setOAuthCallbackURL("https://www.facebook.com/connect/login_success.html");
            facebook.setOAuthPermissions("public_profile,email,user_about_me,user_birthday");
            facebook.setOAuthAccessToken(new AccessToken(getTokenCode(), getExpireDate()));
            Usuario usuario = new UsuarioDAO().pegarPorFacebooId(facebook.getMe().getId());
            if (usuario == null) {
                usuario = new Usuario();
                usuario.setPerfil(Perfil.COMUM);
                usuario.setFbId(facebook.getMe().getId());
            }

            usuario.setNome(facebook.getMe().getName());
            if (facebook.getMe().getBirthday() != null) {
                usuario.setDataDeNascimento(new SimpleDateFormat(facebook.getMe().BIRTHDAY_DATE_FORMAT).parse(facebook.getMe().getBirthday()));
            }
            if (facebook.getMe().getEmail() != null) {
                usuario.setEmail(facebook.getMe().getEmail());
            }
            if (facebook.getPictureURL() != null) {
                if (usuario.getFoto() == null) {
                    BufferedImage bufferedImage = ImageIO.read(facebook.getPictureURL());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "jpg", baos);
                    baos.flush();
                    usuario.setFoto(baos.toByteArray());
                }
            }
            if (usuario.getId() == null) {
                new UsuarioDAO().cadastrar(usuario);
                Platform.runLater(() -> {
                    Message.mostrarMessage("Dados registrados", "Parabéns registrado com sucesso!", Message.Tipo.INFORMACAO);
                });
            } else {
                new UsuarioDAO().editar(usuario);
                Platform.runLater(() -> {
                    Message.mostrarMessage("Dados sicronizados", "Os dados da sua conta no \nfacebook foram sicronizados", Message.Tipo.INFORMACAO);
                });
            }
            Sessao.usuario = usuario;
        } catch (FacebookException ex) {
            Logger.getLogger(FacebookAuth.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FacebookAuth.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacebookAuth.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
