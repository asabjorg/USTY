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
			
			if(ElevatorScene.numberOfPeopleInElevator != 6){
				
				try {
					ElevatorScene.elevatorWaitMutex.acquire();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.println(ElevatorScene.numberOfPeopleInElevator);
				
				for (int i = 0; i < 6 - ElevatorScene.numberOfPeopleInElevator;  i++){
					try {
						ElevatorScene.elevatorDoorInSemaphore.acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				ElevatorScene.elevatorWaitMutex.release();
			
			}
			
			ElevatorScene.scene.incrementElevatorFloor(0);
			

			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			for(int i=0; i < 6; i++){
				ElevatorScene.elevatorDoorOutSemaphore.release(); //signal
			}
			
			ElevatorScene.scene.decrementElevatorFloor(0);
			
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
