/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.control.dao;

import br.com.PalleorrotasDePeterLund.model.GenericaDAO;
import br.com.PalleorrotasDePeterLund.model.entity.Gruta;
import br.com.PalleorrotasDePeterLund.model.entity.GrutaImagem;
import br.com.PalleorrotasDePeterLund.model.entity.Imagem;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author lucas
 */
public class GrutaImagemDAO extends GenericaDAO<GrutaImagem> {

    public List<GrutaImagem> pegarPorGruta(Gruta gruta) {
        entitys = criteria.add(Restrictions.eq("id.gruta", gruta)).list();
        closeSession();
        return entitys;
    }

    public List<GrutaImagem> pegarPorGrutaTexto(Gruta gruta, boolean fotoTexto) {
        entitys = criteria.add(Restrictions.eq("id.gruta", gruta)).add(Restrictions.eq("fotoTexto", fotoTexto)).list();
        closeSession();
        return entitys;
    }

}
