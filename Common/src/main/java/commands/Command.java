package commands;

import connection.Request;
import connection.Response;
import exceptions.*;

/**
 * Command callback interface
 */
public interface Command {
    public Response run() throws ConnectionException, FileException, InvalidDataException, CommandException, CollectionException;
    public String getName();
    public CommandType getType();
    public void setArgument(Request a);
}
