package co.com.realtech.mariner.util.primefaces.dialogos;

/**
 * Tipos de Efectos disponibles en PrimeFaces
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.6
 */
public enum Effects {

    Explode("explode"), Clip("clip"), Drop("drop"), Fold("fold"), Puff("puff"),
    Slide("slide"), Bounce("bounce"), Pulsate("pulsate"), Shake("shake");

    private String tipoEfecto;

    private Effects(String tipoEfecto) {
        this.tipoEfecto = tipoEfecto;
    }

    public String getTipoEfecto() {
        return tipoEfecto;
    }

    public void setTipoEfecto(String tipoEfecto) {
        this.tipoEfecto = tipoEfecto;
    }
}
