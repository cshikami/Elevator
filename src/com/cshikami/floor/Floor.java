package com.cshikami.floor;

import java.util.ArrayList;
import java.util.List;

import com.cshikami.person.Person;

/**
 * Floor in a Building with a List of people waiting for the elevator 
 * and a List of people done riding the elevator
 * @author christopher_shikami
 *
 */
public class Floor {
	
	int floorNumber; //floor number
	
	private List<Person> peopleWaiting = new ArrayList<Person>(); 
	private List<Person> peopleDone = new ArrayList<Person>();
	
	public Floor(int floorNumberIn) {
		
		floorNumber = floorNumberIn;
	}
	
	public void addWaitingPersonToFloor(Person person) {
		peopleWaiting.add(person);
	}
	
	public String toString() {
		return "Floor " + floorNumber + ": " + peopleWaiting.size() + " Waiting :" + peopleDone.size() + " Done.";
	}
	
}
