package file;

import exceptions.CannotCreateFileException;
import exceptions.EmptyPathException;
import exceptions.FileWrongPermissionsException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ReaderWriter {
        /**
         * sets path to file
         * @param pth
         */
        public void setPath(String pth);

        /**
         * reads data
         * @return
         */
        public String read() ;

        /**
         * saves data
         * @param data
         */
        public boolean write(String data) ;
}

