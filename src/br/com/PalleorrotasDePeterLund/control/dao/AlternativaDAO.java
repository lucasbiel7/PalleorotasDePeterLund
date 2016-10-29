/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.control.dao;

import br.com.PalleorrotasDePeterLund.model.GenericaDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Alternativa;
import br.com.PalleorrotasDePeterLund.model.entity.Questao;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author lucas
 */
public class AlternativaDAO extends GenericaDAO<Alternativa> {

    public List<Alternativa> pegarPorQuestao(Questao questao) {
        entitys = criteria.add(Restrictions.eq("questao", questao)).list();
        closeSession();
        return entitys;
    }

}
