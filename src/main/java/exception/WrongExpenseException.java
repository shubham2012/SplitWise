package exception;

public class WrongExpenseException extends Exception {

    private int code;

    public WrongExpenseException() {
    }

    public WrongExpenseException(int code, String message) {
        super(message);
        this.code = code;
    }
}
