/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.ejb.dao.entity_based.modulo;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.MarModulos;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Bean de sessión encargado de traer toda la información concerniente a los módulos de la aplicación
 * @author fabianagudelo
 */
@Stateless
public class ModulosDAOBean extends GenericDAOBean implements ModulosDAOBeanLocal {

    /**
     * Obtiene los módulos asociados a un usuario.
     * @param usuario
     * @return
     * @throws Exception 
     */
    @Override
    public List<MarModulos> obtenerModulosDeUsuario(MarUsuarios usuario) throws Exception{
        List<MarModulos> modulos = new ArrayList<>();
        try {
            
        } catch (Exception e) {
            throw e;
        }
        return modulos;
    }
    
}
