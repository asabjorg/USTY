	package com.ru.usty.elevator;

public class Elevator implements Runnable  {

	@Override
	public void run() {
		
		
		for(int i=0; i < 6; i++){
			ElevatorScene.elevatorDoorInSemaphore.release(); //signal
		}
		
	}

	
		
}
