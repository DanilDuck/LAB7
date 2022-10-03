package exceptions;

public class ConnectionTimeoutException extends ConnectionException{
    public ConnectionTimeoutException(){
        super("response timed out");
    }
}
