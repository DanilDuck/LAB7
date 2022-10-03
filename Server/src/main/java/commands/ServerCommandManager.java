package commands;



import auth.User;
import auth.UserManager;
import connection.AnswerMsg;
import connection.Request;
import connection.Response;
import data.Person;
import exceptions.AuthException;
import exceptions.CommandException;
import exceptions.ConnectionException;
import file.ReaderWriter;
import server.*;
import log.*;

import collection.CollectionManager;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class ServerCommandManager extends CommandManager{
    private final Server server;
    private CollectionManager<Person> collectionManager;
    private ReaderWriter fileManager;

    private final UserManager userManager;

    public ServerCommandManager(Server  serv){
        server = serv;
        userManager = server.getUserManager();
        collectionManager = server.getCollectionManager();
        addCommand(new ExitCommand());
        addCommand(new HelpCommand());
        addCommand(new InfoCommand(collectionManager));
        addCommand(new AddCommand(collectionManager));
        addCommand(new AddIfMaxCommand(collectionManager));
        addCommand(new UpdateCommand(collectionManager));
        addCommand(new ClearCommand(collectionManager));
        addCommand(new ShowCommand(collectionManager));
        addCommand(new PrintAscendingCommand(collectionManager));
        addCommand(new UpdateCommand(collectionManager));
        addCommand(new RemoveGreaterCommand(collectionManager));
        addCommand(new RemoveByIdCommand(collectionManager));
        addCommand(new PrintFieldAscendingEyeColor(collectionManager));
        addCommand(new FilterContainsNameCommand(collectionManager));

        addCommand(new LoginCommand(userManager));
        addCommand(new RegisterCommand(userManager));
        addCommand(new ShowUsersCommand(userManager));

    }
    public Server getServer(){
        return server;
    }
    @Override
    public Response runCommand(Request msg) {
        AnswerMsg res = new AnswerMsg();
        User user = msg.getUser();
        String cmdName = msg.getCommandName();
        boolean isGeneratedByServer = (msg.getStatus() != Request.Status.RECEIVED_BY_SERVER);
        try {
            Command cmd = getCommand(msg);
            //allow to execute a special command without authorization
            if (cmd.getType() != CommandType.AUTH && cmd.getType() != CommandType.SPECIAL) {
                if (isGeneratedByServer) {
                    user = server.getHostUser();
                    msg.setUser(user);
                }
                if (user == null) throw new AuthException();
                if (!userManager.isValid(user)) throw new AuthException();

                //link user to worker
                Person worker = msg.getPerson();
                if (worker != null) worker.setUser(user);
            }

            //executing command
            res = (AnswerMsg) super.runCommand(msg);
        } catch (ConnectionException | CommandException e) {
            res.error(e.getMessage());
        }
        String message = "";

        //format user and current command
        if (user != null) message += "[" + user.getLogin() + "] ";
        if (cmdName != null) message += "[" + cmdName + "] ";

        //format multiline output
        if (res.getMessage().contains("\n")) message += "\n";
        switch (res.getStatus()) {
            case EXIT:
                Log.logger.fatal(message + "shutting down...");
                server.close();
                break;
            case ERROR:
                Log.logger.error(message + res.getMessage());
                break;
            case AUTH_SUCCESS: //check if auth command was invoked by server terminal
                if (isGeneratedByServer) server.setHostUser(user);
            default:
                Log.logger.info(message + res.getMessage());
                break;
        }
        return res;
    }
}
