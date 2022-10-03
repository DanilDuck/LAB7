package exception;

import exceptions.CommandException;

public class ServerOnlyCommandException extends CommandException {
    public ServerOnlyCommandException(){
        super("this command is only for server");
    }
}
