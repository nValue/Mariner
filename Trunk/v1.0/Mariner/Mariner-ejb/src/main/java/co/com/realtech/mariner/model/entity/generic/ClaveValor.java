/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.entity.generic;

import java.util.Objects;

/**
 * Clase que tiene dos constantes para ser mapeada y usada en SelectOneMenu's y otros procesos
 * @author fabianagudelo
 */
public class ClaveValor {
    
    private String clave;
    private String valor;
    
    public ClaveValor(){
        clave = "";
        valor = "";
    }

    public ClaveValor(String clave, String valor) {
        this.clave = clave;
        this.valor = valor;
    }
    
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final ClaveValor other = (ClaveValor) obj;
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        return true;
    }
    
    
    
}
