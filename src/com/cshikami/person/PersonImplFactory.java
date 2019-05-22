package com.cshikami.person;

import com.cshikami.exception.InvalidDataException;

public class PersonImplFactory {
	
	public static Person createPerson(int desiredFloor) throws InvalidDataException {
		return new PersonImpl(desiredFloor);
	}
}
