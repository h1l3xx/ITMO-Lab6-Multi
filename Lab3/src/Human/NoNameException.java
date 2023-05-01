package Human;

public class NoNameException extends Exception{
    public NoNameException() {
        super("Нельзя создать персонажа без имени.");
    }
}
