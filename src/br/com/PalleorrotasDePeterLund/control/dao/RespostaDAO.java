/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.control.dao;

import br.com.PalleorrotasDePeterLund.model.GenericaDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Resposta;
import br.com.PalleorrotasDePeterLund.model.entity.Usuario;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author lucas
 */
public class RespostaDAO extends GenericaDAO<Resposta> {

    public List<Resposta> pegarPorUsuario(Usuario usuario) {
        entitys = criteria.add(Restrictions.eq("id.usuario", usuario)).list();
        closeSession();
        return entitys;
    }

}
