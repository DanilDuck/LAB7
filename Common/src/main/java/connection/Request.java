package connection;

import auth.User;
import data.Person;

import java.io.Serializable;

public interface Request extends Serializable {
    String getStringArg();

    Person getPerson();

    String getCommandName();

    User getUser();

    void setUser(User usr);

    Status getStatus();

    void setStatus(Status s);
    enum Status {
        DEFAULT,
        SENT_FROM_CLIENT,
        RECEIVED_BY_SERVER
    }
}
