	package com.ru.usty.elevator;

public class Elevator implements Runnable  {

	
	@Override
	public void run() {
		
		while(true){
			
			for(int i=0; i < 6; i++){
				ElevatorScene.elevatorDoorInSemaphore.release(); //signal
			}
			
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			ElevatorScene.scene.incrementElevatorFloor(1);
		
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i=0; i < 6; i++){
				ElevatorScene.elevatorDoorOutSemaphore.release(); //signal
			}
			
			ElevatorScene.scene.decrementElevatorFloor(1);
			
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
