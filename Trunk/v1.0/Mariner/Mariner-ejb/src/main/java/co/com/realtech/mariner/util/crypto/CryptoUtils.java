package co.com.realtech.mariner.util.crypto;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * Utilidades de encriptacion de datos basado en AES.
 *
 * @author Andres Rivera
 * @since JDK1.7
 * @version 1.0
 */
public class CryptoUtils {

    final static String keyPassword = "nv4lu32014";

    /**
     * Encrita el texto de ingresado
     *
     * @param sinCifrar
     * @return
     * @throws Exception
     */
    public static String encrypt(String sinCifrar) throws Exception {
        BasicTextEncryptor texEnc = new BasicTextEncryptor();
        texEnc.setPassword(keyPassword);
        return texEnc.encrypt(sinCifrar);
    }

    /**
     * Desencripta el texto ingresado.
     *
     * @param cifrado
     * @return
     * @throws Exception
     */
    public static String decrypt(String cifrado) throws Exception {
        BasicTextEncryptor texEnc = new BasicTextEncryptor();
        texEnc.setPassword(keyPassword);
        return texEnc.decrypt(cifrado);
    }
}
