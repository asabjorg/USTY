	package com.ru.usty.elevator;

public class Elevator implements Runnable  {

	
	@Override
	public void run() {
		
		while(true){
			
			for(int i=0; i < 6; i++){
				ElevatorScene.elevatorDoorInSemaphore.release(); //signal
			}
		
			ElevatorScene.scene.incrementElevatorFloor(1);
		
			
			for(int i=0; i < 6; i++){
				System.out.println("releasing");
				ElevatorScene.elevatorDoorOutSemaphore.release(); //signal
			}
			
			ElevatorScene.scene.decrementElevatorFloor(1);
			
		}
		
	}

}
