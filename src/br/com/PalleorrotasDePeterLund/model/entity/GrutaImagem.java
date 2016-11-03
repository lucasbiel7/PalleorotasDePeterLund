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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author lucas
 */
@Entity
@Table(name = "gruta_imagem")
public class GrutaImagem implements Serializable {

    @Embeddable
    public static class GrutaImagenID implements Serializable {

        @ManyToOne
        private Gruta gruta;
        @ManyToOne
        private Imagem imagem;

        public GrutaImagenID(Gruta gruta, Imagem imagem) {
            this.gruta = gruta;
            this.imagem = imagem;
        }

        public GrutaImagenID() {
        }

        public Gruta getGruta() {
            return gruta;
        }

        public void setGruta(Gruta gruta) {
            this.gruta = gruta;
        }

        public Imagem getImagem() {
            return imagem;
        }

        public void setImagem(Imagem imagem) {
            this.imagem = imagem;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 17 * hash + Objects.hashCode(this.gruta);
            hash = 17 * hash + Objects.hashCode(this.imagem);
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
            final GrutaImagenID other = (GrutaImagenID) obj;
            if (!Objects.equals(this.gruta, other.gruta)) {
                return false;
            }
            if (!Objects.equals(this.imagem, other.imagem)) {
                return false;
            }
            return true;
        }

    }
    @EmbeddedId
    private GrutaImagenID id;
    @Lob
    private String legenda;
    private boolean fotoTexto;

    public boolean isFotoTexto() {
        return fotoTexto;
    }

    public void setFotoTexto(boolean fotoTexto) {
        this.fotoTexto = fotoTexto;
    }

    
    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    public GrutaImagenID getId() {
        return id;
    }

    public void setId(GrutaImagenID id) {
        this.id = id;
    }

    public GrutaImagem(GrutaImagenID id) {
        this.id = id;
    }

    public GrutaImagem() {
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
        final GrutaImagem other = (GrutaImagem) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
