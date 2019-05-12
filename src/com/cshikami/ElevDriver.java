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

		//test1();
		//test2();
		test3();
		//test4();

		System.out.println("DONE");
	}

	private static void test1() throws InterruptedException, InvalidDataException {

		for (int i = 0; i < 40; i++) { // This will run for 40 seconds

			if (i == 0) {
				//add person to building at start floor
				//Building.getInstance().addPerson(1, 10, 1);  // (startFloor, EndFloor, elevNum)
				//Building.getInstance().addPerson(1, 7, 1);
				Building.getInstance().addPerson(1, 10, 1);
			}

			ElevatorController.getInstance().operateElevators(1000); // Tell elevators to operate for 1 sec
			Thread.sleep(1000); // Sleep for 1 sec
		}
		Building.getInstance().getFloors();
		ElevatorController.getInstance().getElevators();
		System.out.println(Building.getInstance().getFloorPeopleWaiting(1));
	}

	private static void test2() throws InvalidDataException, InterruptedException {
		for (int i = 0; i < 60; i++) {

			if (i == 0) {
				//Building.getInstance().addPerson(20, 5, 2);
				Building.getInstance().addPerson(20, 5, 2);
			}	
			if (i == 5) {
				//Building.getInstance().addPerson(15, 19, 2);
				Building.getInstance().addPerson(15, 19, 2);
			}	

			Building.getInstance().getFloors();
			ElevatorController.getInstance().operateElevators(1000);
			Thread.sleep(1000);
		}
	}

	private static void test3() throws InvalidDataException, InterruptedException {
		for (int i = 0; i < 60; i++) {

			if (i == 0) {
				Building.getInstance().addPerson(20, 1, 3);
			}

			if (i == 25) {
				Building.getInstance().addPerson(10, 1, 3);
			}

			Building.getInstance().getFloors();
			ElevatorController.getInstance().operateElevators(1000);
			Thread.sleep(1000);
		}
	}

	private static void test4() throws InvalidDataException, InterruptedException {
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

			Building.getInstance().getFloors();
			ElevatorController.getInstance().operateElevators(1000);
			Thread.sleep(1000);
		}
	}
}
