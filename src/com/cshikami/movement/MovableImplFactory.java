package com.cshikami.movement;

public class MovableImplFactory {
	
	public static Movable createMovable(int desiredFloorIn) {
		return new MovableImpl(desiredFloorIn);
	}

}
