package com.cshikami.elevator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		ArrayList<FloorRequest> a = new ArrayList();
		for (int i = 0; i < floorRequests.size(); i++) {
			a.add(floorRequests.get(i));
		}
		return a;
	}
	
	public ArrayList<Integer> getRiderRequests() {
		ArrayList<Integer> a = new ArrayList();
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
			System.out.print(getIdentifier() + " is going to Floor " + request.getStart() + " for " + request.getDirection() + " request ");
			System.out.print("[Current Floor Requests: " + getFloorRequests() + "]");
			System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
			
			
		} else if (request.getStart() == currentFloor) {
			System.out.print(getIdentifier() + " is already on Floor " + request.getStart() + " for " + request.getDirection() + " request ");
			System.out.print("[Current Floor Requests: " + getFloorRequests() + "]");
			System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
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

	public void removeFloorRequest(int request) {
		floorRequests.remove(request);
	} 

	public String toString() {
		return "Elevator " + getElevatorId();
	}

	public void sortFloorRequests(String direction) {

		//		if (direction == Direction.UP)
		//if number of floor requests is greater than or equal to 2
		//if (floorRequests.size() >= 2) {
		//depending on direction the elevator is going in already
		//sort the floor requests 
		//sort them going up
		//			Collections.sort(floorRequests);
		//			//sort them going down
		//			Collections.sort(floorRequests);

	}

	//Building.getInstance().getPeopleWaiting().size() > 0

	@Override
	public void move(int milliseconds) throws InterruptedException {
		//first, check if there are floor requests or rider requests
		if (isFloorRequests() && isRiderRequests()) {
			System.out.println("isFloorRequests() && isRiderRequests()");
			//if there are, check what the direction to go
			//we determine this by which is closer
			if (direction == Direction.IDLE) {
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
					//direction = Direction.determineDirection(currentFloor, floorRequests.get(0).getStart());

					//open doors
					//add floor people to elevator and add riders to donePeople on the floor
					//close doors
				} //so here we just got the direction when we have both floor requests and rider requests
				//if the direction is going up already	
			} else if (direction == Direction.UP) {
				currentFloor = currentFloor + 1;
				ElevatorDisplay.getInstance().updateElevator(getElevatorId(), currentFloor, riders.size(), ElevatorDisplay.Direction.UP);
				System.out.print(getIdentifier() + " moving from Floor " + (currentFloor - 1)  + " to Floor " + currentFloor);
				System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
				System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
				
				System.out.println("Current floor is: " + currentFloor);
				System.out.println("riderRequests: " + riderRequests);
				for (int i = 0; i < floorRequests.size(); i++) {
					//if current floor is equal to start floor
					if(currentFloor == floorRequests.get(i).getStart()) {
						System.out.print(getIdentifier() + " has arrived at " + currentFloor + " for Floor Request ");
						System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
						System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
						Thread.sleep(2000);
						//open doors
						ElevatorDisplay.getInstance().openDoors(getElevatorId());
						System.out.println(getIdentifier() + " Doors Open");
						//add people waiting on the current floor to riders list
						for (int k = 0; k < Building.getInstance().getFloorPeopleWaiting(currentFloor).size(); k++) {
							//if the number of people waiting on the floor plus the current amount of riders is less than capacity,
							//while number of riders is less than CAPACITY
							//if ((Building.getInstance().getFloorPeopleWaiting(currentFloor).size() + riders.size()) < CAPACITY ) {
								//add those riders to the list of riders
							System.out.println("******************************************************************");
							System.out.println("Amount of riders in elevator: " + riders.size());
							System.out.println("Elevator Capacity: " + CAPACITY);
							if (riders.size() < CAPACITY) {
								riders.add(Building.getInstance().getFloorPeopleWaiting(currentFloor).get(k));
								System.out.println(Building.getInstance().getFloorPeopleWaiting(currentFloor).get(k) + 
										" entered " + getIdentifier() + " [Riders: " + riders + "]"); 
							}
							
							System.out.println("Rider "+ riders.get(i) + " at floor " + currentFloor + " added to riders list.");
						}
						//add rider destinations to riderRequests
						for (int d = 0; d < riders.size(); d++) {
							System.out.println(riders.get(i).getDestination());
							//riders.get(i).setDestination(riders.get(i).getDestination());
							riderRequests.add(riders.get(d).getDestination());
							System.out.print(getIdentifier() + " Rider Request made for Floor " + riders.get(d).getDestination());
							System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
							System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
							//System.out.println(getIdentifier() + " Rider Request made for " + riders.get(d).getDestination());
						}
						
						//remove people waiting on current floor
						Building.getInstance().removeFloorPeopleWaiting(currentFloor);
						floorRequests.remove(i);
						
						Thread.sleep(2000);
						//close doors
						ElevatorDisplay.getInstance().closeDoors(getElevatorId());
						System.out.println(getIdentifier() + " Doors Close");
					}
				}
				
				for (int i = 0; i < riderRequests.size(); i++) {
					if (currentFloor == riderRequests.get(i)) {
						System.out.print(getIdentifier() + " has arrived at " + currentFloor + " for Rider Request ");
						System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
						System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
						ElevatorDisplay.getInstance().openDoors(getElevatorId());
						System.out.println(getIdentifier() + " Doors Open");
						
						//sleep to let people in and out
						Thread.sleep(2000);
						
						//add person to current floor's donePeople list
						Building.getInstance().getFloor(currentFloor).addRiderToDone(riders.get(0));
						
						//remove person from elevator
						riders.remove(0);
						
						System.out.println(Building.getInstance().getFloorPeopleDone(currentFloor).get(0) + 
								" has left " + getIdentifier() + " [Riders: " + riders + "]");
						
						//remove riderRequest
						riderRequests.remove(0);
						//System.out.println("NO MORE RIDERS.");
						ElevatorDisplay.getInstance().closeDoors(getElevatorId());
						System.out.println(getIdentifier() + " Doors Close");
					}
				}

			} else {

				currentFloor--;
				
				ElevatorDisplay.getInstance().updateElevator(getElevatorId(), currentFloor, riders.size(), ElevatorDisplay.Direction.DOWN);
				System.out.print(getIdentifier() + " moving from Floor " + (currentFloor + 1)  + " to Floor " + currentFloor);
				System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
				System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
				
				for (int i = 0; i < floorRequests.size(); i++) {
					if (currentFloor == floorRequests.get(i).getStart()) {
						System.out.print(getIdentifier() + " has arrived at " + currentFloor + " for Floor Request ");
						System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
						System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
						ElevatorDisplay.getInstance().openDoors(getElevatorId());
						System.out.println(getIdentifier() + " Doors Open");
						
						Thread.sleep(1000);
						
						for (int k = 0; k < Building.getInstance().getFloorPeopleWaiting(currentFloor).size(); k++) {
							riders.add(Building.getInstance().getFloorPeopleWaiting(currentFloor).get(k));
							System.out.println(Building.getInstance().getFloorPeopleWaiting(currentFloor).get(k) + 
									" entered " + getIdentifier() + " [Riders: " + riders + "]"); 
							System.out.println("Rider "+ riders.get(i) + " at floor " + currentFloor + " added to riders list.");
						}
						//add rider destinations to riderRequests
						for (int d = 0; d < riders.size(); d++) {
							System.out.println(riders.get(i).getDestination());
							riderRequests.add(riders.get(d).getDestination());
							System.out.print(getIdentifier() + " Rider Request made for Floor " + riders.get(d).getDestination());
							System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
							System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
						}

						System.out.println(Building.getInstance().getFloorPeopleWaiting(currentFloor));

						//remove people from the floor that got into the elevator
						Building.getInstance().removeFloorPeopleWaiting(currentFloor);
						floorRequests.remove(0);
					}
				}
				
				for (int i = 0; i < riderRequests.size(); i++) {
					if (currentFloor == riderRequests.get(i)) {
						System.out.print(getIdentifier() + " has arrived at " + currentFloor + " for Rider Request ");
						System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
						System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
						
						ElevatorDisplay.getInstance().openDoors(getElevatorId());
						System.out.println(getIdentifier() + " Doors Open");
						
						//sleep to let people in and out
						Thread.sleep(2000);
						
						//add person to current floor's donePeople list
						Building.getInstance().getFloor(currentFloor).addRiderToDone(riders.get(i));
						
						//remove person from elevator
						riders.remove(0);
						
						System.out.println(Building.getInstance().getFloorPeopleDone(currentFloor).get(0) + 
								" has left " + getIdentifier() + " [Riders: " + riders + "]");
						
						//remove riderRequest
						riderRequests.remove(0);
						//System.out.println("NO MORE RIDERS.");
						ElevatorDisplay.getInstance().closeDoors(getElevatorId());
						System.out.println(getIdentifier() + " Doors Close");
					}
				}
				
			}

			//if we have floor requests but no rider requests
		} else if (isFloorRequests() && !isRiderRequests()) {
			System.out.println("isFloorRequests() && !isRiderRequests()");
			if(direction == Direction.IDLE) {
				//determine direction from current floor to next floor request
				direction = Direction.determineDirection(currentFloor, floorRequests.get(0).getStart());

				if (currentFloor == floorRequests.get(0).getStart()) {
					System.out.print(getIdentifier() + " has arrived at " + currentFloor + " for Floor Request ");
					System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
					System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
					//open doors
					ElevatorDisplay.getInstance().openDoors(getElevatorId());
					System.out.println(getIdentifier() + " Doors Open");
					
					//sleep to let people in and out
					Thread.sleep(2000);
					if (riders.size() < CAPACITY) {
						//elevator adds people waiting at the floor to it's riders
						for (int i = 0; i < Building.getInstance().getFloorPeopleWaiting(currentFloor).size(); i++) {
							riders.add(Building.getInstance().getFloorPeopleWaiting(currentFloor).get(i));
							System.out.println(Building.getInstance().getFloorPeopleWaiting(currentFloor).get(i) + 
									" entered " + getIdentifier() + " [Riders: " + riders + "]"); 
						}
					}
					
					//add rider destinations to riderRequests
					for (int i = 0; i < riders.size(); i++) {
						System.out.println(riders.get(i).getDestination());
						//riders.get(i).setDestination(riders.get(i).getDestination());
						riderRequests.add(riders.get(i).getDestination());
						System.out.print(getIdentifier() + " Rider Request made for Floor " + riders.get(i).getDestination());
						System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
						System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
					}
					//remove people from the floor that got into the elevator
					//					for (int i = 0; i < Building.getInstance().getFloorPeopleWaiting(currentFloor).size(); i++) {
					//						Building.getInstance().removeFloorPeopleWaiting(currentFloor);
					//						
					//					}
					Building.getInstance().removeFloorPeopleWaiting(currentFloor);

					floorRequests.clear();

					//remove floor requests
					//					for (int i = 0; i < floorRequests.size(); i++) {
					//						floorRequests.
					//					}


					//close doors
					ElevatorDisplay.getInstance().closeDoors(getElevatorId());
					System.out.println(getIdentifier() + " Doors Close");
				} 
			} else if(direction == Direction.UP) {
				
				//sort floorRequests
				Collections.sort(floorRequests, new FloorRequestComparator());
				System.out.println("Sorted Floor Requests: " + floorRequests);
				incrementCurrentFloor();
				System.out.println("Current Floor: " + currentFloor);
				ElevatorDisplay.getInstance().updateElevator(getElevatorId(), currentFloor, riders.size(), ElevatorDisplay.Direction.UP);
				System.out.print(getIdentifier() + " moving from Floor " + (currentFloor - 1)  + " to Floor " + currentFloor);
				System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
				System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
				//if current floor is equal to starting floor of the first floor request
				if (currentFloor == floorRequests.get(0).getStart()) {
					System.out.print(getIdentifier() + " has arrived at " + currentFloor + " for Floor Request ");
					System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
					System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
					//open doors
					ElevatorDisplay.getInstance().openDoors(getElevatorId());
					System.out.println(getIdentifier() + " Doors Open");
					
					//sleep to let people in and out
					Thread.sleep(2000);
					//elevator adds people waiting at the floor to it's riders
					if (riders.size() < CAPACITY) {
						for (int i = 0; i < Building.getInstance().getFloorPeopleWaiting(currentFloor).size(); i++) {
							riders.add(Building.getInstance().getFloorPeopleWaiting(currentFloor).get(i));
							System.out.println(Building.getInstance().getFloorPeopleWaiting(currentFloor).get(i) + 
									" entered " + getIdentifier() + " [Riders: " + riders + "]"); 
							System.out.println("Rider "+ riders.get(i) + " at floor " + currentFloor + " added to riders list.");
						}
					}
					
					//add rider destinations to riderRequests
					for (int i = 0; i < riders.size(); i++) {
						System.out.println(riders.get(i).getDestination());
						//riders.get(i).setDestination(riders.get(i).getDestination());
						riderRequests.add(riders.get(i).getDestination());
						System.out.print(getIdentifier() + " Rider Request made for Floor " + riders.get(i).getDestination());
						System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
						System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
					}

					System.out.println(Building.getInstance().getFloorPeopleWaiting(currentFloor));

					//remove people from the floor that got into the elevator
					Building.getInstance().removeFloorPeopleWaiting(currentFloor);
					floorRequests.remove(0);
				
					//close doors
					ElevatorDisplay.getInstance().closeDoors(getElevatorId());
					System.out.println(getIdentifier() + " Doors Close");

				}

			} else {
				currentFloor--;
				ElevatorDisplay.getInstance().updateElevator(getElevatorId(), currentFloor, riders.size(), ElevatorDisplay.Direction.DOWN);
				System.out.print(getIdentifier() + " moving from Floor " + (currentFloor + 1)  + " to Floor " + currentFloor);
				System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
				System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
			}

			//otherwise, if we have no floor requests but we do have rider requests
		} else if (!isFloorRequests() && isRiderRequests()) {
			direction = Direction.determineDirection(currentFloor, riderRequests.get(0));
			//System.out.println("New direction is: " + direction);

			System.out.println("!isFloorRequests() && isRiderRequests()");
			if (direction == Direction.IDLE) {
				direction = Direction.determineDirection(currentFloor, riderRequests.get(0));
				
				if (currentFloor == riderRequests.get(0)) {
					System.out.print(getIdentifier() + " has arrived at " + currentFloor + " for Rider Request ");
					System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
					System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
					
					System.out.println("YES!!!!!!!");
					ElevatorDisplay.getInstance().openDoors(getElevatorId());
					System.out.println(getIdentifier() + " Doors Open");
					
					//Building.getInstance().getFloor(currentFloor).addRiderToDone(riders.get(0));
					for (int i = 0; i < riderRequests.size(); i++) {
						riderRequests.remove(i);
					}
					System.out.print("Rider Requests left: "+riderRequests);
					
					
				}
				Building.getInstance().getFloors();
				
			} else if(direction == Direction.UP) {
				incrementCurrentFloor();
				
				ElevatorDisplay.getInstance().updateElevator(getElevatorId(), currentFloor, riders.size(), ElevatorDisplay.Direction.UP);
				System.out.print(getIdentifier() + " moving from Floor " + (currentFloor - 1)  + " to Floor " + currentFloor);
				System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
				System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
				//if currentFloor equals riderRequest floor
				System.out.println("currentFloor: " + currentFloor);
				System.out.println("riderRequests: " + riderRequests);
				if (currentFloor == riderRequests.get(0)) {
					System.out.print(getIdentifier() + " has arrived at " + currentFloor + " for Rider Request ");
					System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
					System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
					
					//open doors
					ElevatorDisplay.getInstance().openDoors(getElevatorId());
					System.out.println(getIdentifier() + " Doors Open");
					
					//sleep to let people in and out
					Thread.sleep(2000);

					//add person to current floor's donePeople list
					Building.getInstance().getFloor(currentFloor).addRiderToDone(riders.get(0));
					//remove person from elevator
					riders.remove(0);
					System.out.println(Building.getInstance().getFloorPeopleDone(currentFloor).get(0) + 
							" has left " + getIdentifier() + " [Riders: " + riders + "]"); 
					
					//remove riderRequest
					riderRequests.remove(0);
					//System.out.println("NO MORE RIDERS.");
				}
			} else {
				if (currentFloor > 1) {
					currentFloor--;
					
					ElevatorDisplay.getInstance().updateElevator(getElevatorId(), currentFloor, riders.size(), ElevatorDisplay.Direction.DOWN);
					System.out.print(getIdentifier() + " moving from Floor " + (currentFloor + 1)  + " to Floor " + currentFloor);
					System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
					System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
				}
				
				for (int i = 0; i < floorRequests.size(); i++) {
					if (currentFloor == floorRequests.get(i).getStart()) {
						System.out.print(getIdentifier() + " has arrived at " + currentFloor + " for Floor Request ");
						System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
						System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
						ElevatorDisplay.getInstance().openDoors(getElevatorId());
						System.out.println(getIdentifier() + " Doors Open");
						
						Thread.sleep(1000);
						
						for (int k = 0; k < Building.getInstance().getFloorPeopleWaiting(currentFloor).size(); k++) {
							riders.add(Building.getInstance().getFloorPeopleWaiting(currentFloor).get(k));
							System.out.println(Building.getInstance().getFloorPeopleWaiting(currentFloor).get(k) + 
									" entered " + getIdentifier() + " [Riders: " + riders + "]"); 
							System.out.println("Rider "+ riders.get(i) + " at floor " + currentFloor + " added to riders list.");
						}
						//add rider destinations to riderRequests
						for (int d = 0; d < riders.size(); d++) {
							System.out.println(riders.get(i).getDestination());
							//riders.get(i).setDestination(riders.get(i).getDestination());
							riderRequests.add(riders.get(d).getDestination());
							System.out.print(getIdentifier() + " Rider Request made for Floor " + riders.get(d).getDestination());
							System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
							System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
						}

						System.out.println(Building.getInstance().getFloorPeopleWaiting(currentFloor));

						//remove people from the floor that got into the elevator
						Building.getInstance().removeFloorPeopleWaiting(currentFloor);
						floorRequests.remove(0);
					}
				}
				
				//for all rider requests
				for (int i = 0; i < riderRequests.size(); i++) {
					//if the current floor is equal to the riderRequest
					if (currentFloor == riderRequests.get(i)) {
						System.out.print(getIdentifier() + " has arrived at " + currentFloor + " for Rider Request ");
						System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
						System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
						
						//open doors
						ElevatorDisplay.getInstance().openDoors(getElevatorId());
						System.out.println(getIdentifier() + " Doors Open");
						
						//sleep to let people in and out
						Thread.sleep(1000);
						
						//add first person in riders list to current floor's donePeople list
						Building.getInstance().getFloor(currentFloor).addRiderToDone(riders.get(0));
						
						//remove person from elevator
						riders.remove(0);
						System.out.println(Building.getInstance().getFloorPeopleDone(currentFloor).get(0) + 
								" has left " + getIdentifier() + " [Riders: " + riders + "]");
						System.out.println("Riders: " + riders);
						//remove riderRequest
						riderRequests.remove(i); 
						System.out.println("Rider Requests left: " + riderRequests);
						//System.out.println("NO MORE RIDERS.");
						ElevatorDisplay.getInstance().closeDoors(getElevatorId());
						System.out.println(getIdentifier() + " Doors Close");
					}
				}
			}



		} else if (!isFloorRequests() && !isRiderRequests() && currentFloor != 1 && idleCount <= 10) {
			//what is the current floor?
			System.out.println("Current Floor: " + currentFloor);
			//if idleCount is 10

			//set direction to idle
			direction = Direction.IDLE;
			//stay at the same floor

			ElevatorDisplay.getInstance().updateElevator(getElevatorId(), currentFloor, riders.size(), ElevatorDisplay.Direction.IDLE);
			//wait 10 seconds and go down
			idleCount++;

			System.out.println("Idle Count is:" + idleCount);
			
		} else if (!isFloorRequests() && !isRiderRequests() && currentFloor != 1 && idleCount > 10) {
			System.out.println("!isFloorRequests() && !isRiderRequests() && currentFloor != 1 && idleCount > 10");
			ElevatorDisplay.getInstance().closeDoors(getElevatorId());
			System.out.println(getIdentifier() + " Doors Close");
			decrementCurrentFloor();
			System.out.println("Decremented currentFloor: " + currentFloor);
			ElevatorDisplay.getInstance().updateElevator(getElevatorId(), getCurrentFloor(), riders.size(), ElevatorDisplay.Direction.DOWN);
			System.out.print(getIdentifier() + " moving from Floor " + (currentFloor + 1)  + " to Floor " + currentFloor);
			System.out.print(" [Current Floor Requests: " + getFloorRequests() + "]");
			System.out.println("[Current Rider Requests: " + getRiderRequests() + "]");
			//idleCount = 0;
		}
		else {
			
//			idleCount = 0;
//			System.out.println(idleCount);
			ElevatorDisplay.getInstance().openDoors(getElevatorId());
			System.out.println(getIdentifier() + " Doors Open");
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
