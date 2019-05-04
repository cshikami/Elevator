package com.cshikami.building;

import java.util.HashMap;
import java.util.Map;

import com.cshikami.elevator.Direction;
import com.cshikami.elevator.ElevatorController;
import com.cshikami.elevator.Request;
import com.cshikami.floor.Floor;
import com.cshikami.identification.InvalidDataException;
import com.cshikami.person.Person;
import com.cshikami.person.PersonImplFactory;

/**
 * Singleton Building that owns the floors
 * @author christopher_shikami
 *
 */
public final class Building {
	
	private volatile static Building ourInstance;
	public static final int NUM_FLOORS = 20;
	
	private Map<Integer, Floor> floors = new HashMap<>();
	private Person person;
	
	private Building() {
		//create floors in building by adding floor numbers and the floor with associated number to a hashmap
		//up to NUM FLOORS
		//
		for (int i = 1; i <= NUM_FLOORS; i++) {
			floors.put(i, new Floor(i));
		}
	}
	
	public static Building getInstance() {
		if (ourInstance == null) {
			synchronized (Building.class) {
				if (ourInstance == null) {
					ourInstance = new Building();
				}
			}	
		} 
		return ourInstance;
	}
	
	/**
	 * Adds a floorRequest to a specified elevator
	 * @param startFloor
	 * @param endFloor
	 * @param elevatorNumber the chosen elevator
	 * @throws InvalidDataException
	 */
	public void addPerson(int startFloor, int endFloor, int elevatorNumber) throws InvalidDataException {
		
		//determine direction to go based on startFloor and endfloor
		Direction d = Direction.determineDirection(startFloor, endFloor);
		
		//use a factory to create a Person with desired floor endFloor
		person = PersonImplFactory.createPerson(endFloor);
		//add that new person to the building's floor
		floors.get(startFloor).addWaitingPersonToFloor(person);
		
		//get Elevator number and add a floor request to the elevator specified by elevatorNumber
		ElevatorController.getInstance().getElevator(elevatorNumber).addFloorRequest(new Request(startFloor, d));
		System.out.println(person + " created.");
		
	}
	
	//test method to see how many Floors are in the Building
	public void getFloors() {
		for(int i = 1; i <= floors.size(); i++) {
			System.out.println("Floor: " + i + " is " + floors.get(i));
		}
	}
	
}
