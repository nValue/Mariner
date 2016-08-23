package co.com.realtech.mariner.util.login;

import co.com.realtech.mariner.model.entity.generic.ContrasenaEntidad;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;

/**
 *
 * @author fabianagudelo
 */
public class LoginUtils {
    
    public static boolean validarPatronContrasena(String password, ContrasenaEntidad contEnt){
        boolean valido;
        //Valida el tamaño mínimo
        if(password.length() < contEnt.getLongitudMin()){
            return false;
        }
        //Valida las mayúsculas, minúsculas, números y símbolos mínimos requeridos
        char ch;
        int mayusculas = 0;
        int minusculas = 0;
        int numeros = 0;
        int simbolos = 0;
        for (int ii = 0; ii < password.length(); ii++) {
            ch = password.charAt(ii);
            if ( Character.isLowerCase(ch) ){
                minusculas++;
            }else if ( Character.isUpperCase(ch) ){
                mayusculas++;
            }else if ( Character.isDigit(ch) ){
                numeros++;
            }else{
                simbolos++;
            }
        }
        valido = !(mayusculas < contEnt.getMayusculasMin()||
                minusculas < contEnt.getMinusculasMin() ||
                numeros < contEnt.getNumerosMin() ||
                simbolos < contEnt.getSimbolosMin());
        return valido;
    }
    
    /**
     * Obtiene el parámetro de las constantes y lo mapea a la entidad
     * @return 
     */
    public static ContrasenaEntidad obtenerEntidadDeConstante(){
        String pattern = ConstantesUtils.cargarConstante("VUR-PATRON-CLAVES");
        String[] patrones = pattern.split(",");
        ContrasenaEntidad contEnt = new ContrasenaEntidad();
        if (patrones.length > 0) {
            contEnt.setLongitudMin(Integer.parseInt(patrones[0]));
            if (patrones.length > 1) {
                contEnt.setMinusculasMin(Integer.parseInt(patrones[1]));
                if (patrones.length > 2) {
                    contEnt.setMayusculasMin(Integer.parseInt(patrones[2]));
                    if (patrones.length > 3) {
                        contEnt.setNumerosMin(Integer.parseInt(patrones[3]));
                        if (patrones.length > 4) {
                        contEnt.setSimbolosMin(Integer.parseInt(patrones[4]));
                    } else {
                        contEnt.setSimbolosMin(0);
                    }   
                    } else {
                        contEnt.setNumerosMin(0);
                    }
                } else {
                    contEnt.setMayusculasMin(0);
                }
            } else {
                contEnt.setMinusculasMin(0);
            }
        } else {
            contEnt.setLongitudMin(0);
        }
        return contEnt;
    }
    
    
}
