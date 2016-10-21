/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.control.dao;

import br.com.PalleorrotasDePeterLund.model.GenericaDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Imagem;
import br.com.PalleorrotasDePeterLund.model.entity.Imagem360;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author lucas
 */
public class Imagem360DAO extends GenericaDAO<Imagem360> {

    public List<Imagem360> pegarPorPrincipal(Imagem imagem) {
        entitys = criteria.add(Restrictions.eq("id.principal", imagem)).addOrder(Order.asc("pos")).list();
        closeSession();
        return entitys;
    }

}
