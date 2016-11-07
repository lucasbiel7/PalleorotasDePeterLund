/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.control.dao;

import br.com.PalleorrotasDePeterLund.model.GenericaDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Gruta;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author lucas
 */
public class GrutaDAO extends GenericaDAO<Gruta> {

    public List<Gruta> pegarPorMuseu(boolean b) {
        entitys = criteria.add(Restrictions.eq("museu", b)).list();
        closeSession();
        return entitys;
    }
}
