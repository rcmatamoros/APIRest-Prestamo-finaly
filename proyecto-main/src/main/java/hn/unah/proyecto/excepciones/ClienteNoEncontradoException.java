package hn.unah.proyecto.excepciones;

public class ClienteNoEncontradoException extends RuntimeException{
    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
