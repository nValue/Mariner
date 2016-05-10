package co.com.realtech.mariner.util.tree;

import co.com.realtech.mariner.model.entity.MarModulos;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilidades de manejo de arreglos asociados a las entidades de árbol de base de datos.
 * 
 * @author  Andres Rivera
 * @version 1.0
 * @since   JDK1.7
 */
public class ModelTreeUtil {
    
    /**
     * Recorrido de llenado arbol de modulos.
     * 
     * @param modulos
     * @param newModule
     * @param level
     */ 
    public void walkArray(List<MarModulos> modulos, MarModulos newModule, Integer level) {
        if (newModule.getModIdPadre() == null) {
            if (modulos == null) {
                modulos = new ArrayList<>();
            }
            boolean valMod=false;
            for(MarModulos pModVal:modulos){
                if(pModVal.getModId().equals(newModule.getModId())){
                    valMod=true;
                }
            }             
            if(!valMod){
                newModule.setModulosMenu(new ArrayList<MarModulos>());
                modulos.add(newModule);
            }
        } else {
            bucleGeneral:
            for (int t = 0; t < modulos.size(); t++) {                
                if (modulos.get(t).getModulosMenu() == null) {
                    modulos.get(t).setModulosMenu(new ArrayList<MarModulos>());
                }
                if (modulos.get(t).getModId().equals(newModule.getModIdPadre().getModId())) {
                    if (modulos.get(t).getModulosMenu() == null) {
                        modulos.get(t).setModulosMenu(new ArrayList<MarModulos>());
                    }
                    boolean validacion = false;
                    
                    for (int j = 0; j < modulos.get(t).getModulosMenu().size(); j++) {
                        if (modulos.get(t).getModulosMenu().get(j).getModId().equals(newModule.getModId())) {
                            validacion = true;
                        }
                    }
                    if (!validacion) {
                        modulos.get(t).getModulosMenu().add(newModule);
                    }
                }
                walkArray(modulos.get(t).getModulosMenu(), newModule, level + 1);
            }
        }
    }
}
