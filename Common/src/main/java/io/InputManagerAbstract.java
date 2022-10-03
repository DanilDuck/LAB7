package io;

import auth.User;
import commands.CommandWrapper;
import connection.CommandMsg;
import data.*;
import exceptions.EmptyStringException;
import exceptions.InvalidDataException;
import exceptions.InvalidEnumException;
import exceptions.InvalidNumberException;


import java.util.*;

/**
 * basic of InputManager
 */
public class InputManagerAbstract implements InputManager{
    private Scanner scanner;
    private HashSet<String> passportId;
    public InputManagerAbstract(Scanner scanner1){
        scanner = scanner1;
        scanner.useDelimiter("\n");
    }
    public Scanner getScanner(){
        return scanner;
    }
    public void setScanner(Scanner sc){
        scanner = sc;
    }
    public String readName() throws EmptyStringException {
        String s = scanner.nextLine().trim();
        if (s.equals("")){
            throw new EmptyStringException();
        }
        return s;
    }
    public int readXCoord() throws InvalidNumberException {
        int x;
        try {
            x = Integer.parseInt(scanner.nextLine());
        }catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        return x;
    }
    public float readYCoord() throws InvalidNumberException{
        Float y;
        try{
            y = Float.parseFloat(scanner.nextLine());
        }
        catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        return y;
    }
    public Coordinates readCoords() throws InvalidNumberException{
        int x = readXCoord();
        float y = readYCoord();
        Coordinates coord = new Coordinates(x,y);
        return coord;
    }
    public float readHeight() throws InvalidNumberException{
        Float s;
        try{
            s = Float.parseFloat(scanner.nextLine().trim());
        }
        catch(NumberFormatException e){
            throw new InvalidNumberException();
        }

        if(s<0) throw new InvalidNumberException("must be greater than 0");
        return s;
    }
    public Color readEyeColor() throws InvalidEnumException {
        String s = scanner.nextLine().trim();
        if(s.equals("")){
            return null;
        }
        else {
            try{
                return Color.valueOf(s);
            } catch(IllegalArgumentException e){
                throw new InvalidEnumException();
            }
        }
    }
    public Country readCountry() throws InvalidEnumException {
        String s = scanner.nextLine().trim();
        if(s.equals("")){
            return null;
        }
        else {
            try{
                return Country.valueOf(s);
            } catch(IllegalArgumentException e){
                throw new InvalidEnumException();
            }
        }
    }
    public String readNationality() throws EmptyStringException {
        String s = scanner.nextLine().trim();
        if (s.equals("")) {
            throw new EmptyStringException();
        }
        return s;
    }
    public String readPassportID() throws InvalidNumberException{
        String y;
        y = scanner.nextLine();
        if(passportId == null) passportId = new HashSet<String>();
        else{
            for (String passport: passportId) {
                if(passport.equals(y)){
                    throw new InvalidNumberException("This passportID already exists");
                }
            }
        }
        if (y.length() < 10){
            throw new InvalidNumberException("Passport >=10");
        }
        else{
            passportId.add(y);
        }
        return y;
    }
    public void setPassportId(HashSet<String> passportId){
        this.passportId = passportId;
    }
    public Integer readXLocation() throws InvalidNumberException{
        Integer x;
        try{
            x = Integer.parseInt(scanner.nextLine());
        }
        catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        return x;
    }
    public double readYLocation() throws InvalidNumberException{
        double y;
        try{
            y = Double.parseDouble(scanner.nextLine());
        }
        catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        return y;
    }

    public int readZLocation() throws InvalidNumberException{
        Integer z;
        try{
            z = Integer.parseInt(scanner.nextLine());
        }
        catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        return z;
    }
    public Location readLocation() throws InvalidNumberException {
        Integer x = readXLocation();
        Double y = readYLocation();
        Integer z = readZLocation();
        Location location = new Location(x,y,z);
        return location;
    }
    public Person readPerson() throws InvalidDataException {
        Person person = null;
        String name = readName();
        Coordinates coords = readCoords();
        Float height = readHeight();
        String passportID = readPassportID();
        Color color = readEyeColor();
        Country country = readCountry();
        Location location = readLocation();
        person = new Person(name, coords, height, passportID, color, country,location);
        return person;

    }
    private String readLine() {
        return scanner.nextLine();
    }
    public User readUser() throws InvalidDataException {
        return new User(readPassword(), readLogin());
    }
    public CommandMsg readCommand(){
        String cmd = readLine();
        String arg = null;
        Person worker = null;
        User user = null;
        if (cmd.contains(" ")) { //if command has argument
            String[] arr = cmd.split(" ", 2);
            cmd = arr[0];
            arg = arr[1];
        }
        if (cmd.equals("add") || cmd.equals("add_if_min") || cmd.equals("add_if_max") || cmd.equals("update")) {
            try {
                worker = readPerson();
            } catch (InvalidDataException ignored) {
            }
        } else if (cmd.equals("login") || cmd.equals("register")) {
            try {
                user = readUser();
            } catch (InvalidDataException ignored) {
            }
            return new CommandMsg(cmd, null, null, user);
        }
        return new CommandMsg(cmd, arg, worker);
    }
    public String readPassword() throws InvalidDataException {
        String s = readLine();
        if (s.equals("")) throw new EmptyStringException();
        return s;
    }

    public String readLogin() throws InvalidDataException {
        String s = readLine();
        if (s.equals("")) throw new EmptyStringException();
        return s;
    }
}
