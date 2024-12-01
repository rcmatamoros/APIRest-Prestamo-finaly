package hn.unah.proyecto.excepciones;

public class PrestamoNoEncontradoException extends RuntimeException{
    public PrestamoNoEncontradoException (String mensaje){
        super(mensaje);
    }
}
