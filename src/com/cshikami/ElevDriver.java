package com.cshikami;

import com.cshikami.building.Building;
import com.cshikami.elevator.Elevator;
import com.cshikami.elevator.ElevatorController;
import com.cshikami.elevator.StandardElevator;
import com.cshikami.gui.ElevatorDisplay;
import com.cshikami.identification.InvalidDataException;
import com.cshikami.person.Person;
import com.cshikami.person.StandardPerson;

/**
 * Driver class to show Elevator functionality
 * @author christopher_shikami
 *
 */

public class ElevDriver {

	public static void main(String[] args) throws InterruptedException, InvalidDataException {
		
		//Building.getInstance().getFloors();
		
//		try {
//			Person p = new StandardPerson(3);
//			System.out.println(p);
//			Person p2 = new StandardPerson(4);
//			System.out.println(p2);
//			
//			Elevator e = new StandardElevator(1);
//			Elevator e2 = new StandardElevator(2);
//			System.out.println(e);
//			System.out.println(e2);
//			
//		} catch (InvalidDataException e) {
//			e.printStackTrace();
//		}
		
		ElevatorDisplay.getInstance().initialize(Building.NUM_FLOORS);
		Building.getInstance();
		
		test1();
		
		System.out.println("DONE");
	}
	
	private static void test1() throws InterruptedException, InvalidDataException {
		
	    for (int i = 0; i < 40; i++) { // This will run for 40 seconds

	        if (i == 0) {
	            Building.getInstance().addPerson(1, 10, 1);  // startFloor, EndFloor, elevNum)
	        }

	       ElevatorController.getInstance().operateElevators(1000); // Tell elevators to operate for 1 sec
	        Thread.sleep(1000); // Sleep for 1 sec
	    }
	    Building.getInstance().getFloors();
	    ElevatorController.getInstance().getElevators();
	}

}
