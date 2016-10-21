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
import javax.persistence.Table;

/**
 *
 * @author lucas
 */
@Entity
@Table(name = "imagem_360")
public class Imagem360 implements Serializable {

    @Embeddable
    public static class Imagem360Id implements Serializable {

        @ManyToOne
        private Imagem principal;
        @ManyToOne
        private Imagem composicao;

        public Imagem getPrincipal() {
            return principal;
        }

        public void setPrincipal(Imagem principal) {
            this.principal = principal;
        }

        public Imagem getComposicao() {
            return composicao;
        }

        public void setComposicao(Imagem composicao) {
            this.composicao = composicao;
        }

        public Imagem360Id() {
        }

        public Imagem360Id(Imagem principal, Imagem composicao) {
            this.principal = principal;
            this.composicao = composicao;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 43 * hash + Objects.hashCode(this.principal);
            hash = 43 * hash + Objects.hashCode(this.composicao);
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
            final Imagem360Id other = (Imagem360Id) obj;
            if (!Objects.equals(this.principal, other.principal)) {
                return false;
            }
            if (!Objects.equals(this.composicao, other.composicao)) {
                return false;
            }
            return true;
        }

    }

    @EmbeddedId
    private Imagem360Id id;
    private int pos;

    public Imagem360() {
    }

    public Imagem360(Imagem360Id id, int pos) {
        this.id = id;
        this.pos = pos;
    }
    
    public Imagem360Id getId() {
        return id;
    }

    public void setId(Imagem360Id id) {
        this.id = id;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final Imagem360 other = (Imagem360) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
