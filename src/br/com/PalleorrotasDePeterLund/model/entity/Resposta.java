/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.PalleorrotasDePeterLund.model.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author lucas
 */
@Entity
public class Resposta implements Serializable {

    @Embeddable
    public static class RespostaID implements Serializable {

        @ManyToOne
        private Questao questao;
        @ManyToOne
        private Usuario usuario;

        public Questao getQuestao() {
            return questao;
        }

        public void setQuestao(Questao questao) {
            this.questao = questao;
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 89 * hash + Objects.hashCode(this.questao);
            hash = 89 * hash + Objects.hashCode(this.usuario);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final RespostaID other = (RespostaID) obj;
            if (!Objects.equals(this.questao, other.questao)) {
                return false;
            }
            if (!Objects.equals(this.usuario, other.usuario)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return super.toString(); //To change body of generated methods, choose Tools | Templates.
        }

    }
    @EmbeddedId
    private RespostaID id;
    @ManyToOne
    private Alternativa alternativa;
    private boolean corrigida;

    public RespostaID getId() {
        return id;
    }

    public void setId(RespostaID id) {
        this.id = id;
    }

    public Alternativa getAlternativa() {
        return alternativa;
    }

    public void setAlternativa(Alternativa alternativa) {
        this.alternativa = alternativa;
    }

}
