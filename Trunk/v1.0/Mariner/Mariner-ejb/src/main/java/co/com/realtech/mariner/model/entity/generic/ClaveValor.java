/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.entity.generic;

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
    
}
