package file;

import exceptions.*;

import java.io.*;
/**
 * Operates the main collection file for saving/loading.
 */
public class FileManager implements ReaderWriter {
    private String path;
    /**
     * Constructor, just save variable for all class.
     * @param pth Path to collection file.
     */
    public FileManager(){}
    public FileManager(String pth) {
        path = pth;
    }

    public void setPath(String pth) {
        path = pth;
    }

    public String read() {
        String string = "";
        BufferedReader reader;
        int buffer;
        try {
            File file = new File(path);
            if (!file.exists()) throw new FileNotExistsException();
            if (!file.canRead()) throw new FileWrongPermissionsException("cannot read file");
            reader = new BufferedReader(new FileReader(file.getPath()));
            while ( (buffer = reader.read()) != -1) {
                string += (char)buffer;
            }
            reader.close();
        } catch (FileException | FileNotFoundException e) {
            System.err.println("Error:"+ e.getMessage());
        } catch (IOException e) {
            System.err.println("Error: cannot access file");
        }
        return string;
    }

    private void create(File file) throws CannotCreateFileException {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new CannotCreateFileException();
        }
    }

    public boolean write(String str){
        boolean r = true;
        try {
            if (path == null) throw new EmptyPathException();
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("file " + path + " doesnt exist, trying to create new file\n");
                create(file);
            }
            ;
            if (!file.canWrite()) throw new FileWrongPermissionsException("Error: cannot write file");
            Writer writer = new OutputStreamWriter(new FileOutputStream(path));
            writer.write(str);
            writer.close();
        }catch (FileException e){
            System.err.println(e.getMessage());
            r = false;
        }catch (IOException e){
            r = false;
            System.err.println("Error: cannot access file");
        }
        return r;
    }
}