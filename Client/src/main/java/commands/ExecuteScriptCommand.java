package commands;

import exceptions.CommandException;
import exceptions.FileException;
import exceptions.MissedCommandArgumentException;
import exceptions.RecursiveScriptExecuteException;

public class ExecuteScriptCommand extends CommandImpl {
    ClientCommandManager commandManager;
    public ExecuteScriptCommand(ClientCommandManager cm){
        super("execute_script",CommandType.NORMAL);
        commandManager = cm;
    }
    @Override
    public String execute(){
        if(!hasStringArg()) throw new MissedCommandArgumentException();
        if (commandManager.getStack().contains(getStringArg())) throw new RecursiveScriptExecuteException();
        commandManager.getStack().add(getStringArg());
        ClientCommandManager process = new ClientCommandManager(commandManager.getClient());
        try{
            process.fileMode(getStringArg());
        } catch(FileException e){
            throw new CommandException("cannot find script file");
        }
        commandManager.getStack().pop();
        return "script successfully executed";
    }
}

