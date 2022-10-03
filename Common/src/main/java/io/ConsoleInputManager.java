package io;

import auth.User;
import data.*;

import java.util.Scanner;


public class ConsoleInputManager extends InputManagerAbstract{
    public ConsoleInputManager(){
        super(new Scanner(System.in));
        getScanner().useDelimiter("\n");
    }
    @Override
    public String readName(){
        return new Question<String>("enter name:", super::readName).getAnswer();
    }
    @Override
    public int readXCoord(){
        return new Question<Integer>("enter x:", super::readXCoord).getAnswer();
    }

    @Override
    public float readYCoord(){
        return new Question<Float>("enter y:", super::readYCoord).getAnswer();
    }

    @Override
    public Coordinates readCoords(){
        System.out.println("enter coordinates");
        int x = readXCoord();
        float y = readYCoord();
        Coordinates coord = new Coordinates(x,y);
        return coord;
    }

    @Override
    public float readHeight(){
        return new Question<Float>("enter height:",super::readHeight).getAnswer();
    }

    @Override
    public Color readEyeColor(){
        return new Question<Color>("enter eye color(GREEN,BLACK,BLUE,ORANGE,WHITE):", super::readEyeColor).getAnswer();
    }

    @Override
    public Country readCountry(){
        return new Question<Country>("enter country(GERMANY,FRANCE,SOUTH_KOREA):", super::readCountry).getAnswer();
    }

    @Override
    public String readNationality(){
        return new Question<String>("enter nationality:", super::readNationality).getAnswer();
    }
    public String readPassportID() {
        return new Question<String>("enter PassportId:", super::readPassportID).getAnswer();
    }
    @Override
    public Integer readXLocation(){
        return new Question<Integer>("enter x:", super::readXLocation).getAnswer();
    }
    @Override
    public double readYLocation(){
        return new Question<Double>("enter y:", super::readYLocation).getAnswer();
    }
    @Override
    public int readZLocation(){
        return new Question<Integer>("enter z:", super::readZLocation).getAnswer();
    }
    public Location readLocation(){
        System.out.println("enter location");
        int x = readXLocation();
        double y = readYLocation();
        int z = readZLocation();
        Location location = new Location(x,y,z);
        return location;
    }

    @Override
    public Person readPerson()  {
        Person person;
        String name = readName();
        Coordinates coords = readCoords();
        Float height = readHeight();
        String passportID = null;
        passportID = readPassportID();
        Color color = readEyeColor();
        Country country = readCountry();
        Location location = readLocation();
        person = new Person(name, coords, height, passportID, color, country,location);
        return person;
    }
    @Override
    public String readLogin() {
        return new Question<String>("enter login:", super::readLogin).getAnswer();
    }

    @Override
    public String readPassword() {
        return new Question<String>("enter password:", super::readPassword).getAnswer();
    }
    @Override
    public User readUser() {
        String login = readLogin();
        String password = readPassword();
        return new User(login, password);
    }
}
