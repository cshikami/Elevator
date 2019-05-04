package com.cshikami.elevator;

import com.cshikami.identification.Identifiable;

public interface Elevator extends Identifiable {
	
	public void addFloorRequest(Request request);

}
