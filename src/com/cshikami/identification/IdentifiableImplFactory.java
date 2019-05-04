package com.cshikami.identification;

public class IdentifiableImplFactory {
	private static int PERSON_ID_COUNTER = 1; // Used to generate Person id's
	private static int ELEVATOR_ID_COUNTER = 1; // Used to generate Elevator id's
	
	private IdentifiableImplFactory() {
		
	}

	
    public static Identifiable createIdentifiable(String type) throws InvalidDataException {
    	if (type.equals("Person")) {
    		return new PersonIdentifiableImpl(generatePersonId());
    	} else if (type.equals("Elevator")) {
    		return new ElevatorIdentifiableImpl(generateElevatorId());
    	}
    	else return null;
        
    }

    private static String generatePersonId() {
        return "Person P" + PERSON_ID_COUNTER++; // Use the counter then increment it
    }
    
    private static String generateElevatorId() {
    	//return ElevatorImpl
    	return "Elevator " + ELEVATOR_ID_COUNTER++; // Use the counter then increment it
    }
}
