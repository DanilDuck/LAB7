package connection;

import auth.User;
import data.Person;

import java.io.Serializable;

public class CommandMsg implements Request,Serializable{
    private final String commandName;
    private final String commandStringArgument;
    private final Person person;
    private User user;

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    private Request.Status status;
    public CommandMsg(String commandNm, String commandSA, Person w) {
        commandName = commandNm;
        commandStringArgument = commandSA;
        person = w;
        user = null;
        status = Status.DEFAULT;
    }
    public CommandMsg(String commandNm, String commandSA, Person w, User usr) {
        commandName = commandNm;
        commandStringArgument = commandSA;
        person = w;
        user = usr;
        status = Status.DEFAULT;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return Command name.
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * @return Command string argument.
     */
    public String getStringArg() {
        return commandStringArgument;
    }

    /**
     * @return Command object argument.
     */
    public Person getPerson() {
        return person;
    }

    @Override
    public Status getStatus() {
        return status;
    }
}
