package com.cshikami.elevator;


import java.util.HashMap;
import java.util.Map;
import com.cshikami.exception.InvalidDataException;
import com.cshikami.exception.InvalidParamException;
import com.cshikami.exception.InvalidRangeException;
import com.cshikami.gui.ElevatorDisplay;

/**
 * Singleton ElevatorController that controls the elevators
 * @author christopher_shikami
 *
 */
public final class ElevatorController {

	private static ElevatorController ourInstance;

	private Elevator elevator;
	private int NUM_ELEVATORS;
	private Map<Integer, Elevator> elevators = new HashMap<>();
	//private Movable movable;


	private ElevatorController() throws InvalidDataException {

	}
	
	public void init() throws InvalidDataException {
		for (int i = 1; i <= NUM_ELEVATORS; i++) {
			elevators.put(i, createNewElevator(i));
			ElevatorDisplay.getInstance().addElevator(i, 1);
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
	
	public int getNumberOfElevators() {
		return NUM_ELEVATORS;
	}
	
	public void setDoorOpenTime(int time) throws InvalidParamException, InvalidDataException {
		for (int i = 1; i <= elevators.size(); i++) {
			getElevator(i).setDoorOpenTime(time);
		}
	}
	
	public void setElevatorMaxCapacity(int maxCapacity) throws InvalidDataException, InvalidParamException {
		for (int i = 1; i <= elevators.size(); i++) {
			getElevator(i).setMaxPersonsPerElevator(maxCapacity);
		}
	}
	
	public void getElevatorMaxCapacity() throws InvalidDataException {
		int capacity;
		for (int i = 1; i < elevators.size(); i++) {
			capacity = getElevator(i).getMaxPersonsPerElevator();
			System.out.println("Elevator " + i + "'s max capacity: " +capacity);
		}
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
	 * @throws InterruptedException 
	 * @throws InvalidRangeException 
	 */
	public void operateElevators(int milliseconds) throws InterruptedException, InvalidRangeException {
		//for all the elevators, move each elevator, regardless of elevator type
		for (int i = 1; i <= elevators.size(); i++) {
			elevators.get(i).move(1000);
		}
	}

	//test method to see how many Elevators are in the Building
	public void getElevators() {
		for(int i = 1; i <= elevators.size(); i++) {
			System.out.println("Elevator " + i + " is " + elevators.get(i));
		}
	}

	/**
	 * Set number of elevators
	 * @param numberOfElevators
	 */
	public void setNumberOfElevators(int numberOfElevators) {
		NUM_ELEVATORS = numberOfElevators;
	}


}
