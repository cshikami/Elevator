package com.cshikami.building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cshikami.elevator.Direction;
import com.cshikami.elevator.ElevatorController;
import com.cshikami.elevator.FloorRequest;
import com.cshikami.floor.Floor;
import com.cshikami.identification.InvalidDataException;
import com.cshikami.person.Person;
import com.cshikami.person.PersonImplFactory;
import com.cshikami.time.Time;

/**
 * Singleton Building that owns the floors
 * @author christopher_shikami
 *
 */
public final class Building {
	
	private volatile static Building ourInstance;
	private int NUM_FLOORS;
	
	private Map<Integer, Floor> floors = new HashMap<>();
	private Person person;
	
	private Building() {

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
	
	//create floors in building by adding floor numbers and the floor with associated number to a hashmap
			//up to NUM FLOORS
	public void init() {
		for (int i = 1; i <= NUM_FLOORS; i++) {
			floors.put(i, new Floor(i));
		}
	}
	
	public void setNumberOfFloors(int numberOfFloorsIn) {
		NUM_FLOORS = numberOfFloorsIn;
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
		System.out.println(Time.getInstance().getCurrentTime() + " " + person + " created on " + startFloor + ", wants to go " + d + " to " + endFloor);
		//add that new person to the building's floor
		floors.get(startFloor).addWaitingPersonToFloor(person);
		//System.out.println(floors);
		System.out.println(Time.getInstance().getCurrentTime() + " " + person + " presses " + d + " on " + startFloor);
		//get Elevator number and add a floor request to the elevator specified by elevatorNumber
		ElevatorController.getInstance().getElevator(elevatorNumber).addFloorRequest(new FloorRequest(startFloor, d));
	}
	
	public List<Person> getPeopleWaiting() {
		List<Person> people = new ArrayList<Person>();
		for(int i = 1; i < floors.size(); i++) {
			people = floors.get(i).getPeopleWaiting();
		}
		return people;
	}
	
	public List<Person> getPeopleDone() {
		List<Person> people = new ArrayList<Person>();
		for (int i = 1; i < floors.size(); i++) {
			people = floors.get(i).getPeopleDone();
		}
		return people;
	}
	
	
	
//	public Person getPersonWaiting() {
//		
//	}
	
	public Floor getFloor(int currentFloor) {
		return floors.get(currentFloor);
	}
	
	public List<Person> getFloorPeopleWaiting(int theFloor) {
		Floor floor = floors.get(theFloor);
		return floor.getPeopleWaiting();
	}
	
	public List<Person> getFloorPeopleDone(int theFloor) {
		Floor floor = floors.get(theFloor);
		return floor.getPeopleDone();
	}
	
	public void removeFloorPeopleWaiting(int theFloor) {
		Floor floor = floors.get(theFloor);
		floor.removePeopleWaiting();
	}
	
	public void removePersonFromFloor(int theFloor) {
		Floor floor = floors.get(theFloor);
		floor.removePersonWaiting(0);
	}
	
	//test method to see how many Floors are in the Building
	public Map<Integer, Floor> getFloors() {
		for(int i = 1; i <= floors.size(); i++) {
			System.out.println("Floor: " + i + " is " + floors.get(i));
		}
		return floors;
	}
	
}
