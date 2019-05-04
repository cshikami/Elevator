package com.cshikami.elevator;


import java.util.HashMap;
import java.util.Map;
import com.cshikami.identification.InvalidDataException;

/**
 * Singleton ElevatorController that controls the elevators
 * @author christopher_shikami
 *
 */
public final class ElevatorController {

	private static ElevatorController ourInstance;

	private Elevator elevator;
	private static final int NUM_ELEVATORS = 4;
	private Map<Integer, Elevator> elevators = new HashMap<>();
	//private Movable movable;


	private ElevatorController() throws InvalidDataException {
		//create elevators and put them in elevators HashMap
		for (int i = 1; i <= NUM_ELEVATORS; i++) {
			elevators.put(i, createNewElevator(i));
		}
	}

	public static ElevatorController getInstance() throws InvalidDataException {
		if (ourInstance == null) {
			synchronized(ElevatorController.class) {
				if(ourInstance == null) {
					ourInstance = new ElevatorController();
				}
			}
		}
		return ourInstance;
	}

	/**
	 * Return an Elevator with elevatorId
	 * @param elevatorId
	 * @throws InvalidDataException 
	 */
	public Elevator getElevator(int elevatorId) throws InvalidDataException {
		return elevators.get(elevatorId);
	}

	/**
	 * Create new Elevator using factory
	 * @param elevatorId
	 * @return Elevator
	 * @throws InvalidDataException
	 */
	public Elevator createNewElevator(int elevatorId) throws InvalidDataException {
		elevator = ElevatorImplFactory.createElevator(elevatorId);
		return elevator;
	}

	/**
	 * Move all elevators at speed milliseconds
	 * @param milliseconds
	 */
	public void operateElevators(int milliseconds) {
		//movable = MovableImplFactory.createMovable
	}

	//test method to see how many Elevators are in the Building
	public void getElevators() {
		for(int i = 1; i <= elevators.size(); i++) {
			System.out.println("Elevator " + i + " is " + elevators.get(i));
		}
	}


}
