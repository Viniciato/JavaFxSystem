package Model;

/**
 * Created by Nadin on 17/11/15.
 */
public class InvalidCpfException extends RuntimeException {
    public InvalidCpfException(String mensagem){
        super(mensagem);
    }
}
