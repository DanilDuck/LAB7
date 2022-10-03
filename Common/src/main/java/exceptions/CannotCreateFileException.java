package exceptions;
/**
 * thrown when unable to create file
 */
public class CannotCreateFileException extends FileException{
    public CannotCreateFileException(){
        super("Can't create file");
    }
}
