package com.cshikami;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Calendar calendar = new GregorianCalendar(0, 0, 0);	
		System.out.println(sdf.format(calendar.getTime()));
		
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
