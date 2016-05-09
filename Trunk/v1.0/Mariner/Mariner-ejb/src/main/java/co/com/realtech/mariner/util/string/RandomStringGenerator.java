package co.com.realtech.mariner.util.string;

/**
 * Generador de String Random.
 *
 * @author http://syntx.io/how-to-generate-a-random-string-in-java/
 * @version 1.0
 * @since JDK1.7
 */
public class RandomStringGenerator {

    public static enum Mode {
        ALPHA, ALPHANUMERIC, NUMERIC
    }

    /**
     * Generar secuencia random alfanumerica
     *
     * @param length
     * @param mode
     * @return
     */
    public static String generateRandomString(int length, Mode mode){
        StringBuilder buffer = new StringBuilder();
        String characters = "";

        switch (mode) {
            case ALPHA:
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
                break;
            case ALPHANUMERIC:
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                break;
            case NUMERIC:
                characters = "1234567890";
                break;
        }
        int charactersLength = characters.length();
        for (int i = 0; i < length; i++) {
            double index = Math.random() * charactersLength;
            buffer.append(characters.charAt((int) index));
        }
        return buffer.toString();
    }
}
