package co.com.realtech.mariner.model.entity.generic;

/**
 * Entidad que obtiene las cantidades necesarias para la validación de las contraseñas.
 * @author fabianagudelo
 */
public class ContrasenaEntidad {
    
    private int longitudMin;
    private int minusculasMin;
    private int mayusculasMin;
    private int numerosMin;
    private int simbolosMin;

    public int getLongitudMin() {
        return longitudMin;
    }

    public void setLongitudMin(int longitudMin) {
        this.longitudMin = longitudMin;
    }

    public int getMinusculasMin() {
        return minusculasMin;
    }

    public void setMinusculasMin(int minusculasMin) {
        this.minusculasMin = minusculasMin;
    }

    public int getMayusculasMin() {
        return mayusculasMin;
    }

    public void setMayusculasMin(int mayusculasMin) {
        this.mayusculasMin = mayusculasMin;
    }

    public int getNumerosMin() {
        return numerosMin;
    }

    public void setNumerosMin(int numerosMin) {
        this.numerosMin = numerosMin;
    }

    public int getSimbolosMin() {
        return simbolosMin;
    }

    public void setSimbolosMin(int simbolosMin) {
        this.simbolosMin = simbolosMin;
    }
    
    
    
    
}
