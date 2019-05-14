package com.cshikami.elevator;

public class RiderRequest {
	
	private int desiredFloor;
	
	
	public RiderRequest(int desiredFloorIn) {
		desiredFloor = desiredFloorIn;
	}
	
	public int getDesiredFloor() {
		return desiredFloor;
	}
	
	public void setDesiredFloor(int desiredFloorIn) {
		desiredFloor = desiredFloorIn;
	}
	
	public String toString() {
		return desiredFloor + "";
	}
}
