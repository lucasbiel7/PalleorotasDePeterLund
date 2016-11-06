/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.control.dao;

import br.com.PalleorrotasDePeterLund.model.GenericaDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Glossario;
import java.util.List;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author lucas
 */
public class GlossarioDAO extends GenericaDAO<Glossario> {

    public GlossarioDAO() {
        criteria = criteria.addOrder(Order.asc("palavra"));
    }

    public List<Glossario> pegarPorPesquisa(String text) {
        entitys = criteria.add(Restrictions.ilike("palavra", text, MatchMode.ANYWHERE)).list();
        closeSession();
        return entitys;
    }

}
