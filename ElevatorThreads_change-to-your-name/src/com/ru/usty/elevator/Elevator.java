package com.ru.usty.elevator;

public class Elevator implements Runnable  {

	
	@Override
	public void run() {
		
		while(true){
			ElevatorScene.addPersonToWaitLine = true;
			int tempNumberOfPeopleInElevator = (6 - ElevatorScene.numberOfPeopleInElevator);
			
			for(int i=0; i < tempNumberOfPeopleInElevator; i++){
				ElevatorScene.elevatorDoorInSemaphore[ElevatorScene.floorCount].release(); 
			}
			
			ElevatorScene.addPersonToWaitLine = false;
					
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			/*if(ElevatorScene.numberOfPeopleInElevator < 6){
				
				for (int i = 0; i < 6 - ElevatorScene.numberOfPeopleInElevator;  i++){
					
					if(ElevatorScene.floorCount != 0){
						try {
							
							ElevatorScene.elevatorDoorInSemaphore.acquire();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
				
			}*/
						
			if(ElevatorScene.floorCount == (ElevatorScene.scene.numberOfFloors - 1)){
				ElevatorScene.floorCount = 0; 	
			}
			else{	
				ElevatorScene.scene.incrementElevatorFloor(0);
			}
			
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			int tempNumberOfPeopleForDestFloor = ElevatorScene.numberOfPeopleForDestFloor[ElevatorScene.floorCount];
			
			for(int i=0; i < tempNumberOfPeopleForDestFloor; i++){
				ElevatorScene.elevatorDoorOutSemaphore[ElevatorScene.floorCount].release(); //signal
			}
			
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
