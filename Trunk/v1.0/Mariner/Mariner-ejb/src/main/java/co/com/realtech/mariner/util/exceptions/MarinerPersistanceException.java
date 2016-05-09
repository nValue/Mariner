package co.com.realtech.mariner.util.exceptions;

import javax.ejb.ApplicationException;

/**
 * Excepcion personalizada para manejo de transacciones JPA.
 *
 * @author Andres Rivera
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class MarinerPersistanceException extends Exception {
    
    public MarinerPersistanceException() {
        super();
    }
    
    public MarinerPersistanceException(String msg) {
        super(msg);
    }
    
    public MarinerPersistanceException(Throwable ext) {
        super(ext);
    }
    
    public MarinerPersistanceException(Throwable ext, String msg) {
        super(msg, ext);
    }
    
}
