
package br.com.remedios.service;

public class ServiceException extends Exception {
    private int codigoErro;

    public int getCodigoErro() {
        return codigoErro;
    }
    
   public ServiceException(int codigoErro, String message) {
        super(message);
        this.codigoErro = codigoErro;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    
    
    
    
    
}
