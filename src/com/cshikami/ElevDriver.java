package com.cshikami;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.cshikami.building.Building;
import com.cshikami.exception.InvalidParamException;
import com.cshikami.exception.InvalidRangeException;
import com.cshikami.elevator.ElevatorController;
import com.cshikami.gui.ElevatorDisplay;
import com.cshikami.exception.InvalidDataException;
import com.cshikami.time.Time;

/**
 * Driver class to show Elevator functionality
 * @author christopher_shikami
 *
 */

public class ElevDriver {

	public static void main(String[] args) throws InterruptedException, InvalidParamException, InvalidDataException, InvalidRangeException {

		System.out.println(Time.getInstance().getCurrentTime() + " Begin program");

		// read json input file using simplejson
		FileReader reader;
		try {
			// Create a FileReader object using your filename
			reader = new FileReader("input.json");
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			return;
		}
		JSONParser jsonParser = new JSONParser();
		JSONObject jObj;

		try {
			// Create a JSONParser using the FileReader
			jObj = (JSONObject) jsonParser.parse(reader);

		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			//get numberOfFloorsInBuilding from json file and set to int
			int numberOfFloors = ((Long) jObj.get("numberOfFloorsInBuilding")).intValue();
			//set number of floors in building
			Building.getInstance().setNumberOfFloors(numberOfFloors);
			//initialize floors in building
			Building.getInstance().init();
			
			//get numberOfElevatorsInBuilding from json file and set to int
			int numberOfElevators = ((Long) jObj.get("numberOfElevatorsInBuilding")).intValue();
			//set number of elevators
			ElevatorController.getInstance().setNumberOfElevators(numberOfElevators);

			//initalize floors in elevator display
			ElevatorDisplay.getInstance().initialize(numberOfFloors);
			
			//initialize elevators
			ElevatorController.getInstance().init();
			
			//get maxPersonPerElevator from json file as int
			int maxPersonsPerElevator = ((Long) jObj.get("maxPersonsPerElevator")).intValue();
			//set that to the elevator max capacity
			ElevatorController.getInstance().setElevatorMaxCapacity(maxPersonsPerElevator);
			
			//need to implement timePerFloor in program still
			int timePerFloor = ((Long) jObj.get("timePerFloor")).intValue();
			int doorOpenTime = ((Long) jObj.get("doorOpenTime")).intValue();
			ElevatorController.getInstance().setDoorOpenTime(doorOpenTime);
		} catch (NullPointerException e) {
			System.err.println("NullPointer Exception: " + e.getMessage());
		}
		
		Building.getInstance();

		//tests to make sure elevators are functioning properly
		test1();
		test2();
		test3();
		test4();

		System.out.println("DONE");
		ElevatorDisplay.getInstance().shutdown();
	}

	private static void test1() throws InterruptedException, InvalidDataException, InvalidParamException, InvalidRangeException {

		for (int i = 0; i < 40; i++) { // This will run for 40 seconds

			if (i == 0) {
				//add person to building at start floor
				Building.getInstance().addPerson(1, 10, 1); // (startFloor, EndFloor, elevNum)
			}

			ElevatorController.getInstance().operateElevators(1000); // Tell elevators to operate for 1 sec
			Thread.sleep(1000); // Sleep for 1 sec
		}
	}

	private static void test2() throws InvalidDataException, InterruptedException, InvalidParamException, InvalidRangeException {
		
		for (int i = 0; i < 60; i++) {

			if (i == 0) {
				Building.getInstance().addPerson(20, 5, 2);
			}	
			
			if (i == 5) {
				Building.getInstance().addPerson(15, 19, 2);
			}	
			
			ElevatorController.getInstance().operateElevators(1000);
			Thread.sleep(1000);
		}
	}

	private static void test3() throws InvalidDataException, InterruptedException, InvalidParamException, InvalidRangeException {
		for (int i = 0; i < 60; i++) {

			if (i == 0) {
				Building.getInstance().addPerson(20, 1, 3);
			}

			if (i == 25) {
				Building.getInstance().addPerson(10, 1, 3);
			}

			ElevatorController.getInstance().operateElevators(1000);
			Thread.sleep(1000);
		}
	}

	private static void test4() throws InvalidDataException, InterruptedException, InvalidParamException, InvalidRangeException {
		for (int i = 0; i < 70; i++) {

			if (i == 0) {
				Building.getInstance().addPerson(1, 10, 1);
			}

			if (i == 5) {
				Building.getInstance().addPerson(8, 17, 1);
			}

			if (i == 6) {
				Building.getInstance().addPerson(1, 9, 4);
			}

			if (i == 32) {
				Building.getInstance().addPerson(3, 1, 4);
			}

			ElevatorController.getInstance().operateElevators(1000);
			Thread.sleep(1000);
		}
	}
}
