package connection;



import java.io.Serializable;

public interface Response extends Serializable {
    public String getMessage();
    public Status getStatus();
    enum Status {
        ERROR,
        FINE,
        EXIT,
        AUTH_SUCCESS
    }
}
