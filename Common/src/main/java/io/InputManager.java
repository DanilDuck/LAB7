package io;

import commands.CommandWrapper;
import connection.CommandMsg;
import data.*;
import exceptions.EmptyStringException;
import exceptions.InvalidDataException;
import exceptions.InvalidEnumException;
import exceptions.InvalidNumberException;

import java.util.Scanner;

public interface InputManager {
    /**
     * reads name from input
     * @return
     * @throws EmptyStringException
     */
    String readName() throws EmptyStringException;
    /**
     * reads x from input
     * @return
     * @throws InvalidNumberException
     */
    int readXCoord() throws InvalidNumberException;
    /**
     * reads y from input
     * @return
     * @throws InvalidNumberException
     */
    float readYCoord() throws InvalidNumberException;
    /**
     * reads coordinates from input
     * @return
     * @throws InvalidNumberException
     */
    Coordinates readCoords() throws InvalidNumberException;
    /**
     * reads Height from input
     * @return
     * @throws InvalidNumberException
     */
    float readHeight() throws InvalidNumberException;
    /**
     * reads eyeColor from input
     * @return
     * @throws InvalidEnumException
     */
    Color readEyeColor() throws InvalidEnumException;
    /**
     * reads country from input
     * @return
     * @throws InvalidEnumException
     */
    Country readCountry() throws InvalidEnumException;
    /**
     * reads nationality from input
     * @return
     * @throws EmptyStringException
     */
    String readNationality() throws EmptyStringException;
    /**
     * reads passportID from input
     * @return
     * @throws InvalidNumberException
     */
    String readPassportID() throws InvalidNumberException;
    /**
     * reads x location from input
     * @return
     * @throws InvalidNumberException
     */
    Integer readXLocation() throws InvalidNumberException;
    /**
     * reads y location from input
     * @return
     * @throws InvalidNumberException
     */
    double readYLocation() throws InvalidNumberException;
    /**
     * reads z location from input
     * @return
     * @throws InvalidNumberException
     */
    int readZLocation() throws InvalidNumberException;
    /**
     * reads location coordinates from input
     * @return
     * @throws InvalidNumberException
     */
    Location readLocation() throws InvalidNumberException;
    /**
     * reads Person from input
     * @return
     * @throws InvalidDataException
     */
    Person readPerson() throws InvalidDataException;
    /**
     * gets input scanner
     * @return
     */
    Scanner getScanner();
    /**
     * reads command-argument pair from input
     * @return
     */
    public CommandMsg readCommand();
}
