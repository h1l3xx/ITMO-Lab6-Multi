package Human;

public class PoorHealthException extends RuntimeException{
    PoorHealthException(){
        super("Я не могу продолжить работу - иду домой!");
    }
}
