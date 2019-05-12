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

	public ElevatorImpl(int elevatorIdIn) throws InvalidDataException {
		identity = IdentifiableImplFactory.createIdentifiable("Elevator");
		movable = MovableImplFactory.createMovable(elevatorIdIn);
		elevatorId = elevatorIdIn;
		currentFloor = 1;
		direction = Direction.IDLE;
	}

	public int getElevatorId() {
		return elevatorId;
	}

	@Override
	public String getIdentifier() {
		return identity.getIdentifier();
	}

	@Override
	public void addFloorRequest(FloorRequest request) {
		floorRequests.add(request);
		//test to see if floor request was added
		for (int i = 0; i < floorRequests.size(); i++) {
			System.out.println("Added floor request " + floorRequests.get(i).toString());
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
				System.out.println("Current floor is: " + currentFloor);
				System.out.println("riderRequests: " + riderRequests);
				for (int i = 0; i < floorRequests.size(); i++) {
					//if current floor is equal to start floor
					if(currentFloor == floorRequests.get(i).getStart()) {
						Thread.sleep(2000);
						//open doors
						ElevatorDisplay.getInstance().openDoors(getElevatorId());
						//add people waiting on the current floor to riders list
						for (int k = 0; k < Building.getInstance().getFloorPeopleWaiting(currentFloor).size(); k++) {
							riders.add(Building.getInstance().getFloorPeopleWaiting(currentFloor).get(k));
							System.out.println("Rider "+ riders.get(i) + " at floor " + currentFloor + " added to riders list.");
						}
						//add rider destinations to riderRequests
						for (int d = 0; d < riders.size(); d++) {
							System.out.println(riders.get(i).getDestination());
							//riders.get(i).setDestination(riders.get(i).getDestination());
							riderRequests.add(riders.get(d).getDestination());
						}
						
						//remove people waiting on current floor
						Building.getInstance().removeFloorPeopleWaiting(currentFloor);
						floorRequests.remove(i);
						
						Thread.sleep(2000);
						//close doors
						ElevatorDisplay.getInstance().closeDoors(getElevatorId());
					}
				}
				
				for (int i = 0; i < riderRequests.size(); i++) {
					if (currentFloor == riderRequests.get(i)) {
						ElevatorDisplay.getInstance().openDoors(getElevatorId());
						
						//sleep to let people in and out
						Thread.sleep(2000);
						
						//add person to current floor's donePeople list
						Building.getInstance().getFloor(currentFloor).addRiderToDone(riders.get(0));
						//remove person from elevator
						riders.remove(0);
						//remove riderRequest
						riderRequests.remove(0);
						//System.out.println("NO MORE RIDERS.");
						ElevatorDisplay.getInstance().closeDoors(getElevatorId());
					}
				}

			} else {

				currentFloor--;
				System.out.println("Current Floor going down: " + currentFloor);
				ElevatorDisplay.getInstance().updateElevator(getElevatorId(), currentFloor, riders.size(), ElevatorDisplay.Direction.DOWN);
				
				for (int i = 0; i < floorRequests.size(); i++) {
					if (currentFloor == floorRequests.get(i).getStart()) {
						ElevatorDisplay.getInstance().openDoors(getElevatorId());
						
						Thread.sleep(1000);
						
						for (int k = 0; k < Building.getInstance().getFloorPeopleWaiting(currentFloor).size(); k++) {
							riders.add(Building.getInstance().getFloorPeopleWaiting(currentFloor).get(k));
							System.out.println("Rider "+ riders.get(i) + " at floor " + currentFloor + " added to riders list.");
						}
						//add rider destinations to riderRequests
						for (int d = 0; d < riders.size(); d++) {
							System.out.println(riders.get(i).getDestination());
							//riders.get(i).setDestination(riders.get(i).getDestination());
							riderRequests.add(riders.get(d).getDestination());
						}

						System.out.println(Building.getInstance().getFloorPeopleWaiting(currentFloor));

						//remove people from the floor that got into the elevator
						Building.getInstance().removeFloorPeopleWaiting(currentFloor);
						floorRequests.remove(0);
					}
				}
				
				for (int i = 0; i < riderRequests.size(); i++) {
					if (currentFloor == riderRequests.get(i)) {
						ElevatorDisplay.getInstance().openDoors(getElevatorId());
						
						//sleep to let people in and out
						Thread.sleep(2000);
						
						//add person to current floor's donePeople list
						Building.getInstance().getFloor(currentFloor).addRiderToDone(riders.get(i));
						//remove person from elevator
						riders.remove(0);
						//remove riderRequest
						riderRequests.remove(0);
						//System.out.println("NO MORE RIDERS.");
						ElevatorDisplay.getInstance().closeDoors(getElevatorId());
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
					//open doors
					ElevatorDisplay.getInstance().openDoors(getElevatorId());
					//sleep to let people in and out
					Thread.sleep(2000);
					//elevator adds people waiting at the floor to it's riders
					for (int i = 0; i < Building.getInstance().getFloorPeopleWaiting(currentFloor).size(); i++) {
						riders.add(Building.getInstance().getFloorPeopleWaiting(currentFloor).get(i));
					}
					//add rider destinations to riderRequests
					for (int i = 0; i < riders.size(); i++) {
						System.out.println(riders.get(i).getDestination());
						//riders.get(i).setDestination(riders.get(i).getDestination());
						riderRequests.add(riders.get(i).getDestination());
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


					System.out.println("Number of Floor Requests: " + floorRequests.size() + " for floor " + currentFloor);
					System.out.println("Number of Rider Requests: " + riderRequests.size());
				} 
			} else if(direction == Direction.UP) {
				
				//sort floorRequests
				Collections.sort(floorRequests, new FloorRequestComparator());
				System.out.println("Sorted Floor Requests: " + floorRequests);
				incrementCurrentFloor();
				System.out.println("Current Floor: " + currentFloor);
				ElevatorDisplay.getInstance().updateElevator(getElevatorId(), currentFloor, riders.size(), ElevatorDisplay.Direction.UP);
				//if current floor is equal to starting floor of the first floor request
				if (currentFloor == floorRequests.get(0).getStart()) {
					//open doors
					ElevatorDisplay.getInstance().openDoors(getElevatorId());
					//sleep to let people in and out
					Thread.sleep(2000);
					//elevator adds people waiting at the floor to it's riders
					for (int i = 0; i < Building.getInstance().getFloorPeopleWaiting(currentFloor).size(); i++) {
						riders.add(Building.getInstance().getFloorPeopleWaiting(currentFloor).get(i));
						System.out.println("Rider "+ riders.get(i) + " at floor " + currentFloor + " added to riders list.");
					}
					//add rider destinations to riderRequests
					for (int i = 0; i < riders.size(); i++) {
						System.out.println(riders.get(i).getDestination());
						//riders.get(i).setDestination(riders.get(i).getDestination());
						riderRequests.add(riders.get(i).getDestination());
					}

					System.out.println(Building.getInstance().getFloorPeopleWaiting(currentFloor));

					//remove people from the floor that got into the elevator
					Building.getInstance().removeFloorPeopleWaiting(currentFloor);
					floorRequests.remove(0);
					//						for (int i = 0; i < Building.getInstance().getFloorPeopleWaiting(currentFloor).size(); i++) {
					//							Building.getInstance().removePersonFromFloor(0);
					//						}


					//floor takes riders who have reached their destination
					//look through all the riders
					//					for (int i = 0; i < riders.size(); i++) {
					//						//if current floor of each rider is equal to their desired floor
					//						if (currentFloor == riders.get(i).getDestination()) {
					//							//add that rider to the floor
					//							Building.getInstance().getFloor(currentFloor).addRiderToDone(riders.get(i));
					//							//remove rider from elevator's riders list
					//							riders.remove(i);
					//						}
					//						
					//					}
					//
					//riderRequests.add(riders.get(0).getDestination());
					//floor adds people riding in elevator

					//close doors
					ElevatorDisplay.getInstance().closeDoors(getElevatorId());

				}
				System.out.println("Number of floor requests: " + floorRequests.size());

			} else {
				currentFloor--;
				ElevatorDisplay.getInstance().updateElevator(getElevatorId(), currentFloor, riders.size(), ElevatorDisplay.Direction.DOWN);
			}

			//otherwise, if we have no floor requests but we do have rider requests
		} else if (!isFloorRequests() && isRiderRequests()) {
			direction = Direction.determineDirection(currentFloor, riderRequests.get(0));
			System.out.println("New direction is: " + direction);

			System.out.println("!isFloorRequests() && isRiderRequests()");
			if (direction == Direction.IDLE) {
				direction = Direction.determineDirection(currentFloor, riderRequests.get(0));
				
				if (currentFloor == riderRequests.get(0)) {
					System.out.println("YES!!!!!!!");
					ElevatorDisplay.getInstance().openDoors(getElevatorId());
					//Building.getInstance().getFloor(currentFloor).addRiderToDone(riders.get(0));
					for (int i = 0; i < riderRequests.size(); i++) {
						riderRequests.remove(i);
					}
					System.out.print("Rider Requests left: "+riderRequests);
					
					
				}
				Building.getInstance().getFloors();
				
			} else if(direction == Direction.UP) {
				incrementCurrentFloor();
				System.out.println("Current Floor going up: " + currentFloor);
				ElevatorDisplay.getInstance().updateElevator(getElevatorId(), currentFloor, riders.size(), ElevatorDisplay.Direction.UP);
				//if currentFloor equals riderRequest floor
				System.out.println("currentFloor: " + currentFloor);
				System.out.println("riderRequests: " + riderRequests);
				if (currentFloor == riderRequests.get(0)) {
					//open doors
					ElevatorDisplay.getInstance().openDoors(getElevatorId());
					//sleep to let people in and out
					Thread.sleep(2000);

					//add person to current floor's donePeople list
					Building.getInstance().getFloor(currentFloor).addRiderToDone(riders.get(0));
					//remove person from elevator
					riders.remove(0);
					//remove riderRequest
					riderRequests.remove(0);
					//System.out.println("NO MORE RIDERS.");
				}
			} else {
				if (currentFloor > 1) {
					currentFloor--;
					System.out.println("Current Floor after going down: " + currentFloor);
					ElevatorDisplay.getInstance().updateElevator(getElevatorId(), currentFloor, riders.size(), ElevatorDisplay.Direction.DOWN);
				}
				
				for (int i = 0; i < floorRequests.size(); i++) {
					if (currentFloor == floorRequests.get(i).getStart()) {
						ElevatorDisplay.getInstance().openDoors(getElevatorId());
						
						Thread.sleep(1000);
						
						for (int k = 0; k < Building.getInstance().getFloorPeopleWaiting(currentFloor).size(); k++) {
							riders.add(Building.getInstance().getFloorPeopleWaiting(currentFloor).get(k));
							System.out.println("Rider "+ riders.get(i) + " at floor " + currentFloor + " added to riders list.");
						}
						//add rider destinations to riderRequests
						for (int d = 0; d < riders.size(); d++) {
							System.out.println(riders.get(i).getDestination());
							//riders.get(i).setDestination(riders.get(i).getDestination());
							riderRequests.add(riders.get(d).getDestination());
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
						//open doors
						ElevatorDisplay.getInstance().openDoors(getElevatorId());
						
						//sleep to let people in and out
						Thread.sleep(1000);
						
						//add first person in riders list to current floor's donePeople list
						Building.getInstance().getFloor(currentFloor).addRiderToDone(riders.get(0));
						//remove person from elevator
						riders.remove(0);
						System.out.println("Riders: " + riders);
						//remove riderRequest
						riderRequests.remove(i); 
						System.out.println("Rider Requests left: " + riderRequests);
						//System.out.println("NO MORE RIDERS.");
						ElevatorDisplay.getInstance().closeDoors(getElevatorId());
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
			//System.out.println("Current Floor: " + getCurrentFloor());
			//if (direction == Direction.IDLE) {
			//wait 10 seconds and then go down to first floor

			//int newCurrentFloor = currentFloor;
			//				if (idleCount == 10) {
			//					//idleCount = 0;
			//					while (currentFloor > 1) {
			//						//decrement currentFloor
			//						decrementCurrentFloor();
			//						//print out current floor
			//						System.out.println("Decremented currentFloor: " + currentFloor);
			//						ElevatorDisplay.getInstance().updateElevator(getElevatorId(), getCurrentFloor(), riders.size(), ElevatorDisplay.Direction.DOWN);
			//					}
			//					
			//					
			//				}

			//				if (currentFloor == 1) {
			//					ElevatorDisplay.getInstance().updateElevator(getElevatorId(), currentFloor, riders.size(), ElevatorDisplay.Direction.IDLE);
			//				}

			//}
		} else if (!isFloorRequests() && !isRiderRequests() && currentFloor != 1 && idleCount > 10) {
			System.out.println("!isFloorRequests() && !isRiderRequests() && currentFloor != 1 && idleCount > 10");
			ElevatorDisplay.getInstance().closeDoors(getElevatorId());
			decrementCurrentFloor();
			System.out.println("Decremented currentFloor: " + currentFloor);
			ElevatorDisplay.getInstance().updateElevator(getElevatorId(), getCurrentFloor(), riders.size(), ElevatorDisplay.Direction.DOWN);
			//idleCount = 0;
		}
		else {
			System.out.println("Else");
			idleCount = 0;
			System.out.println(idleCount);
			ElevatorDisplay.getInstance().openDoors(getElevatorId());
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

	//after getting direction
	//figure out, if currentFloor == desiredFloor
	//if it is, open door, add people from floor to elevator and add people from elevator to floor
	//otherwise, move
	//		ElevatorDisplay.getInstance().updateElevator(getElevatorId(), currentFloor, riders.size(), direction);
	//		System.out.println("We are going in " + direction + " direction");
	//		System.out.println("Current floor is: " + currentFloor);

	//





	//	@Override
	//	public void move(int milliseconds) throws InterruptedException {
	//		if (isFloorRequests()) {
	//			System.out.println("Number of Floor Requests:" + floorRequests.size());
	//				//removeFloorRequest(k);
	//			
	//			int repetition = 0;
	//			//System.out.println(getFloorRequest(0));
	//			//for all the floorRequests
	//			for(int i = 0; i < floorRequests.size(); i++) {
	//				//get the starting floor
	//				int floorRequestStartFloor = getFloorRequest(i).getStart();
	//				//determine direction
	//				
	//				//if direction is IDLE
	//				if(direction == Direction.IDLE && getFloorRequest(i).getStart() == currentFloor) {
	//					//open doors
	//					ElevatorDisplay.getInstance().openDoors(getElevatorId());
	//					
	//					//add person waiting on the current floor to riders Person array
	//					riders.add(Building.getInstance().getFloorPeopleWaiting(currentFloor).get(i));
	//					//Building.getInstance().getFloorPeopleWaiting(currentFloor).remove(0);
	//					//add the new rider's desired floor to riderRequests
	//					riderRequests.add(riders.get(i).getDestination());
	//					System.out.println("Rider: " + riders.get(i).getIdentifier());
	//					System.out.println("Number of riders: " + riders.size());
	//					System.out.println("Rider Request added for floor: " + riderRequests.get(i));
	//					System.out.println("Repetition: " + ++repetition);
	//					//close doors
	//					//get all the rider desired floors 
	//					//for (int r = 0; r < riders.size(); r++) {
	//				}
	//			}

	//			for (int i = 0; i < floorRequests.size(); i++) {
	//				removeFloorRequest(i);
	//			}
	//		}

	//		if (isRiderRequests()) {
	//			ElevatorDisplay.getInstance().closeDoors(getElevatorId());
	//			for (int i = 0; i < riderRequests.size(); i++) {
	//				direction = Direction.determineDirection(currentFloor, riderRequests.get(i));
	//				System.out.println("Elevator will go in direction: " + direction);
	//				
	//			}
	//			
	//		}
	//	}

	//if (isRiderRequests()) {


	//remove all the floor requests
	//add each person's desired floor to rider request array
	//
	//}
	//}



	//		for (int i = 0; i < riders.size(); i++) {
	//			System.out.println("Riders: " + riders.get(i).getIdentifier());
	//		}



	//		for (int i = 0; i < floorRequests.size(); i++) {
	//			removeFloorRequest(i);
	//		}

	//ElevatorDisplay.getInstance().closeDoors(getElevatorId());
	//determine direction of elevator
	//		for (int i = 0; i < riders.size(); i++) {
	//			direction = Direction.determineDirection(currentFloor, riders.get(i).getDestination());
	//			System.out.println(direction);
	//		}

	//		if (direction == Direction.UP) {
	//			while (currentFloor != riders.get(i).getDestination()) {
	//			//ElevatorDisplay.getInstance().updateElevator(getElevatorId(), riders.get(0).getDestination(), riders.size(), ElevatorDisplay.Direction.UP);
	//			ElevatorDisplay.getInstance().updateElevator(getElevatorId(), ++currentFloor, riders.size(), ElevatorDisplay.Direction.UP);
	//			Thread.sleep(milliseconds);
	//			}
	//		}
	//		
	//		if (currentFloor == riders.get(0).getDestination()) {
	//			ElevatorDisplay.getInstance().openDoors(getElevatorId());
	//			Building.getInstance().getFloor(currentFloor).addRiderToDone(riders.get(0));
	//			System.out.println("Number of People Done" + Building.getInstance().getFloor(currentFloor).getPeopleDone().size());
	//		}


	//}


	//}
	//			}
	//			
	//			for (int i = 0; i < riders.size(); i++) {
	//				System.out.println(riders.get(i).getIdentifier());
	//			}
	//			
	//		}



	//		if (isFloorRequests()) {
	//			for (int i = 0; i < floorRequests.size(); i++) {
	//				System.out.println(getFloorRequest(i));
	//				
	//			}
	//		}

	//		if(direction == Direction.IDLE) {
	//			getFloorRequest
	//		}


	//for all floors, get all floorRequests or riderRequests
	//if floorRequests start or riderRequests desiredFloor is the currentFloor
	//if floorRequests start is the currentFloor, the door opens and
	//the person gets in (the elevatorImpl adds the person 
	//to riders arraylist)
	//


	//for each floorRequest

	//		//if currentFloor equals floorRequest, open door and elevator adds people to riders list
	//		if (currentFloor == getFloorRequest())
	//		
	//		for (int i = 0; i < floorRequests.size(); i++) {
	//			
	//		}

	//if there is a floor request, get floor request 
	//		if (isFloorRequests()){
	//			for (int i = 0; i < floorRequests.size(); i++) {
	//				//get all floor requests
	//				getFloorRequest(i);
	//				System.out.println(getIdentifier() + ": floor request start: " + getFloorRequest(i).getStart() + " floor request direction: " + getFloorRequest(i).getDirection());
	//				//if direction is idle
	//				if (direction == Direction.IDLE) {
	//					//if currentFloor equals floorRequest start
	//					if (currentFloor == getFloorRequest(i).getStart()) {
	//						//open door
	//						ElevatorDisplay.getInstance().openDoors(getElevatorId());
	//					}	//add person(s)
	//						//Floor.getPersonWaiting();
	//						//riders.;
	//						//goal is: for each floor, get people waiting and add them to riders list
	//						//what i am actually doing: here i am adding everyone on floor 1 for the number
	//						//of floorRequests that there are
	//						riders.addAll(Building.getInstance().getFloor(1));
	//						//riders.addAll(Building.getInstance().getPeopleWaiting());
	//						System.out.println(riders);
	////						for (int i = 0; i < ) {
	////							
	////						}
	//						//riders.add(this);
	//						//close door
	//					
	//				}
	//			}


	//if Idle,
	//if currentFloor is equal to floor request
	//and elevator going in same direction as floor request
	//open door
	//add person(s) on the current floor to elevator
	//close door
	//go in direction of the desiredFloor,
	//stopping anytime there is either a floor request or a rider request


	//removeFloorRequest();
	//}
	//we have the elevatorId - we need to check direction, which floor the rider wants to go to,
	//and the number of riders
	//ElevatorDisplay.getInstance().updateElevator(elevatorId, floor, numRiders, direction);
	//}

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
