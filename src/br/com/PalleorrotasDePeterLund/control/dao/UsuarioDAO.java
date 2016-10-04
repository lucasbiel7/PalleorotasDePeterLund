/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.control.dao;

import br.com.PalleorrotasDePeterLund.model.GenericaDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Usuario;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author lucas
 */
public class UsuarioDAO extends GenericaDAO<Usuario> {

    public Usuario pegarPorEmail(String email) {
        entity = (Usuario) criteria.add(Restrictions.eq("email", email)).uniqueResult();
        closeSession();
        return entity;
    }

    public Usuario pegarPorFacebooId(String id) {
         entity = (Usuario) criteria.add(Restrictions.eq("fbId", id)).uniqueResult();
        closeSession();
        return entity;
    }
}
