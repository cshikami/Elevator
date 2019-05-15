package com.cshikami.elevator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cshikami.elevator.FloorRequest;
import com.cshikami.building.Building;
import com.cshikami.gui.ElevatorDisplay;
import com.cshikami.identification.Identifiable;
import com.cshikami.identification.IdentifiableImplFactory;
import com.cshikami.identification.InvalidDataException;
import com.cshikami.movement.Movable;
import com.cshikami.movement.MovableImplFactory;
import com.cshikami.person.Person;
import com.cshikami.time.Time;

public class ElevatorImpl implements Elevator {

	private Identifiable identity;
	private Movable movable;
	private int elevatorId;
	private Direction direction;
	private int currentFloor;

	//floorRequests have a number and a direction
	private final ArrayList<FloorRequest> floorRequests = new ArrayList<>();
	//riderRequests house a number only
	private final ArrayList<Integer> riderRequests = new ArrayList<>(); 
	private final List<Person> riders = new ArrayList<>();
	private int idleCount = 0;
	private int CAPACITY;

	public ElevatorImpl(int elevatorIdIn) throws InvalidDataException {
		identity = IdentifiableImplFactory.createIdentifiable("Elevator");
		movable = MovableImplFactory.createMovable(elevatorIdIn);
		elevatorId = elevatorIdIn;
		currentFloor = 1;
		direction = Direction.IDLE;
		//setMaxPersonsPerElevator()
	}

	public void setMaxPersonsPerElevator(int maxPersonsPerElevator) {
		CAPACITY = maxPersonsPerElevator;
	}

	public int getMaxPersonsPerElevator() {
		return CAPACITY;
	}

	public int getElevatorId() {
		return elevatorId;
	}

	public ArrayList<FloorRequest> getFloorRequests() {
		ArrayList<FloorRequest> a = new ArrayList<FloorRequest>();
		for (int i = 0; i < floorRequests.size(); i++) {
			a.add(floorRequests.get(i));
		}
		return a;
	}

	public ArrayList<Integer> getRiderRequests() {
		ArrayList<Integer> a = new ArrayList<Integer>();
		for (int i = 0; i < riderRequests.size(); i++) {
			a.add(riderRequests.get(i));
		}
		return a;
	}

	@Override
	public String getIdentifier() {
		return identity.getIdentifier();
	}

	@Override
	public void addFloorRequest(FloorRequest request) {
		floorRequests.add(request);
		if (request.getStart() != currentFloor) {
			System.out.print(Time.getInstance().getCurrentTime() + " " + getIdentifier() + " is going to Floor " + request.getStart() + " for " + request.getDirection() + " request ");
			printRequests();


		} else if (request.getStart() == currentFloor) {
			System.out.print(Time.getInstance().getCurrentTime() + " " + getIdentifier() + " is already on Floor " + request.getStart() + " for " + request.getDirection() + " request ");
			printRequests();
		}
	}

	public FloorRequest getFloorRequest(int floor) {
		return floorRequests.get(floor);	
	}

	public boolean isFloorRequests() {
		return floorRequests.size() > 0;
	}

	public boolean isRiderRequests() {
		return riderRequests.size() > 0;
	}

	public void printRequests() {
		System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
		System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
	}

	public void removeFloorRequest(int request) {
		floorRequests.remove(request);

	} 

	public void addRider(Person newRider) {
		riders.add(newRider);
	}

	public void removeRider(int rider) {
		riders.remove(rider);
	}

	public void addFloorPeopleWaitingToRiders() {
		if (riders.size() < CAPACITY) {
			//elevator adds people waiting at the floor to it's riders
			for (int i = 0; i < Building.getInstance().getFloorPeopleWaiting(currentFloor).size(); i++) {
				addRider(Building.getInstance().getFloorPeopleWaiting(currentFloor).get(i));
				System.out.println(Time.getInstance().getCurrentTime() + " " + Building.getInstance().getFloorPeopleWaiting(currentFloor).get(i) + 
						" entered " + getIdentifier() + " [Riders: " + riders + "]"); 
			}
		}
	}

	public void addRiderDestinationsToRiderRequests() {
		for (int i = 0; i < riders.size(); i++) {
			riderRequests.add(riders.get(i).getDestination());
			System.out.print(Time.getInstance().getCurrentTime() + " " + getIdentifier() + " Rider Request made for Floor " + riders.get(i).getDestination());
			printRequests();
		}
	}

	public void openDoors() {
		ElevatorDisplay.getInstance().openDoors(getElevatorId());
		System.out.println(Time.getInstance().getCurrentTime() + " " + getIdentifier() + " Doors Open");
	}

	public void closeDoors() {
		ElevatorDisplay.getInstance().closeDoors(getElevatorId());
		System.out.println(Time.getInstance().getCurrentTime() + " " + getIdentifier() + " Doors Close");
	}

	public String toString() {
		return "Elevator " + getElevatorId();
	}

	public void sortFloorRequests(String direction) {

	}

	public void getDirection() {

		//movable.determineDirection(int distanceFromFloorRequest, int distanceFromRiderRequest)
		int distanceFromFloorRequest = Math.abs(currentFloor - floorRequests.get(0).getStart());
		int distanceFromRiderRequest = Math.abs(currentFloor - riderRequests.get(0));
		//if distance from next floor request is less than distance from next rider request
		if (distanceFromFloorRequest < distanceFromRiderRequest && distanceFromFloorRequest != 0) {
			//determine the direction to the next floor request
			direction = Direction.determineDirection(currentFloor, floorRequests.get(0).getStart());
			//else if distance from the next floor request is greater than distance from the 
			//next rider request
		} else if (distanceFromFloorRequest > distanceFromRiderRequest && distanceFromRiderRequest != 0) {
			//determine direction to next rider request
			direction = Direction.determineDirection(currentFloor, riderRequests.get(0));
		} else if (distanceFromFloorRequest < distanceFromRiderRequest && distanceFromFloorRequest == 0) {
			direction = Direction.determineDirection(currentFloor, riderRequests.get(0));
			//otherwise, the distance to the next is the same, 
			//it doesn't matter which we choose (we choose floorRequest here)
		} 	
	}

	/**
	 * Move elevator up one floor and print out message
	 */
	public void goUp() {
		incrementCurrentFloor();
		ElevatorDisplay.getInstance().updateElevator(getElevatorId(), currentFloor, riders.size(), ElevatorDisplay.Direction.UP);
		System.out.print(Time.getInstance().getCurrentTime() + " " + getIdentifier() + " moving from Floor " + (currentFloor - 1)  + " to Floor " + currentFloor);
		printRequests();
	}


	/**
	 * Move elevator down one floor and print out message
	 */
	public void goDown() {
		decrementCurrentFloor();
		ElevatorDisplay.getInstance().updateElevator(getElevatorId(), getCurrentFloor(), riders.size(), ElevatorDisplay.Direction.DOWN);
		System.out.print(Time.getInstance().getCurrentTime() + " " + getIdentifier() + " moving from Floor " + (currentFloor + 1)  + " to Floor " + currentFloor);
		printRequests();
	}

	/**
	 * Process floor requests when there is a floor request on the current floor
	 * @throws InterruptedException
	 */
	public void processFloorRequests() throws InterruptedException {
		Collections.sort(floorRequests, new FloorRequestComparator());

		for (int i = 0; i < floorRequests.size(); i++) {

			//if current floor is equal to starting floor of the first floor request
			if (currentFloor == floorRequests.get(i).getStart()) {

				System.out.print(Time.getInstance().getCurrentTime() + " " + getIdentifier() + " has arrived at " + currentFloor + " for Floor Request ");
				printRequests();
				//open doors
				openDoors();

				Thread.sleep(2000);

				//elevator adds people waiting at the floor to it's riders
				addFloorPeopleWaitingToRiders();

				//add rider destinations to riderRequests
				addRiderDestinationsToRiderRequests();

				//remove people from the floor that got into the elevator
				Building.getInstance().removeFloorPeopleWaiting(currentFloor);
				floorRequests.remove(i);

				//close doors
				closeDoors();
			}
		}
	}

	/**
	 * Process rider requests when there is a rider request on the current floor
	 * @throws InterruptedException
	 */
	public void processRiderRequests() throws InterruptedException {
		for (int i = 0; i < riderRequests.size(); i++) {
			//if current floor is equal to a rider request
			if (currentFloor == riderRequests.get(i)) {
				System.out.print(Time.getInstance().getCurrentTime() + " " + getIdentifier() + " has arrived at " + currentFloor + " for Rider Request ");
				printRequests();
				//open doors
				openDoors();

				//sleep to let people in and out
				Thread.sleep(2000);

				//add person to current floor's donePeople list
				Building.getInstance().getFloor(currentFloor).addRiderToDone(riders.get(0));

				//remove person from elevator
				removeRider(0);

				System.out.println(Time.getInstance().getCurrentTime() + " " + Building.getInstance().getFloorPeopleDone(currentFloor).get(0) + 
						" has left " + getIdentifier() + " [Riders: " + riders + "]");

				//remove riderRequest
				riderRequests.remove(0);
				//close doors
				closeDoors();

			}
		}
	}

	/**
	 * Move the elevator up or down, or remain idle, depending upon whether there are 
	 * floor requests, rider requests, location, and how long the elevator has been idle
	 */
	@Override
	public void move(int milliseconds) throws InterruptedException {

		//first, check if there are floor requests and rider requests
		if (isFloorRequests() && isRiderRequests()) {
			System.out.println("isFloorRequests() && isRiderRequests()");
			//if there are and direction is idle, check what the direction to go
			if (direction == Direction.IDLE) {	
				//get direction
				getDirection();
				//if direction is up
			} else if (direction == Direction.UP) {

				//move up
				goUp();
				processFloorRequests();
				processRiderRequests();

			} else {
				//move down
				goDown();
				processFloorRequests();
				processRiderRequests();
			}

			//if we have floor requests but no rider requests
		} else if (isFloorRequests() && !isRiderRequests()) {
			System.out.println("isFloorRequests() && !isRiderRequests()");
			if(direction == Direction.IDLE) {
				//determine direction from current floor to next floor request
				direction = Direction.determineDirection(currentFloor, floorRequests.get(0).getStart());
				processFloorRequests();

			} else if(direction == Direction.UP) {
				goUp();
				processFloorRequests();

			} else {
				goDown();
				processFloorRequests();
			}

			//otherwise, if we have no floor requests but we do have rider requests
		} else if (!isFloorRequests() && isRiderRequests()) {
			direction = Direction.determineDirection(currentFloor, riderRequests.get(0));

			System.out.println("!isFloorRequests() && isRiderRequests()");
			//if idle
			if (direction == Direction.IDLE) {
				direction = Direction.determineDirection(currentFloor, riderRequests.get(0));

				if (currentFloor == riderRequests.get(0)) {
					System.out.print(Time.getInstance().getCurrentTime() + " " + getIdentifier() + " has arrived at " + currentFloor + " for Rider Request ");
					printRequests();
					openDoors();

					//remove rider requests
					for (int i = 0; i < riderRequests.size(); i++) {
						riderRequests.remove(i);
					}
					
					closeDoors();
				}
			//if going up
			} else if(direction == Direction.UP) {
				goUp();
				processRiderRequests();
				//if going down
			} else {
				if (currentFloor > 1) {
					goDown();
				}

				processFloorRequests();
				processRiderRequests();
			}

		} else if (!isFloorRequests() && !isRiderRequests() && currentFloor != 1 && idleCount <= 10) {

			//set direction to idle
			direction = Direction.IDLE;
			//stay at the same floor

			ElevatorDisplay.getInstance().updateElevator(getElevatorId(), currentFloor, riders.size(), ElevatorDisplay.Direction.IDLE);
			//wait 10 seconds and go down
			idleCount++;

		} else if (!isFloorRequests() && !isRiderRequests() && currentFloor != 1 && idleCount > 10) {
			System.out.println("!isFloorRequests() && !isRiderRequests() && currentFloor != 1 && idleCount > 10");
			closeDoors();
			goDown();
		}
		else {
			openDoors();
			ElevatorDisplay.getInstance().updateElevator(getElevatorId(), getCurrentFloor(), riders.size(), ElevatorDisplay.Direction.IDLE);
		}
	}

	public void incrementCurrentFloor() {
		currentFloor = currentFloor + 1;
	}

	public void decrementCurrentFloor() {
		currentFloor = currentFloor - 1;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	@Override
	public void setDestination(int destination) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getDestination() {
		// TODO Auto-generated method stub
		return 0;
	}
}
