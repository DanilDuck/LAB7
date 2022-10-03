package commands;

import connection.AnswerMsg;

import connection.Request;

import connection.Response;
import data.Person;
import exceptions.*;



public abstract class  CommandImpl implements Command{
    private final CommandType type;
    private final String name;
    private Request arg;
    public CommandImpl(String n, CommandType t){
        name = n;
        type = t;
    }
    public CommandType getType(){
        return type;
    }
    public String getName(){
        return name;
    }

    /**
     * custom execute command
     * @return
     * @throws InvalidDataException
     * @throws CommandException
     * @throws FileException
     * @throws ConnectionException
     */
    public String execute() throws InvalidDataException,CommandException, FileException, ConnectionException,CollectionException{
        return "";
    }

    /**
     * wraps execute into response
     * @return
     */
    public Response run() throws InvalidDataException, CommandException, FileException, ConnectionException, CollectionException {
        AnswerMsg res = new AnswerMsg();
        res.info(execute());
        return res;
    }
    public Request getArgument(){
        return arg;
    }
    public void setArgument(Request req){
        arg=req;
    }
    public boolean hasStringArg(){
        return arg!=null && arg.getStringArg()!=null && !arg.getStringArg().equals("");
    }
    public boolean hasWorkerArg(){
        return arg!=null && arg.getPerson()!=null;
    }

    public String getStringArg(){
        return getArgument().getStringArg();
    }

    public Person getPersonArg(){
        return getArgument().getPerson();
    }
}

