package io;

import file.FileManager;

import java.util.Scanner;
/**
 * Operates input
 */
public class FileInputManager extends InputManagerAbstract{
    public FileInputManager(String path){
        super(new Scanner(new FileManager(path).read()));
        getScanner().useDelimiter("\n");
    }
}