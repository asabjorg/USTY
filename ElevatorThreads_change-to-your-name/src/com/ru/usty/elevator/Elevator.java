package com.ru.usty.elevator;

public class Elevator implements Runnable  {

	
	@Override
	public void run() {
		
		while(true){
			
			
			for(int i=0; i < 6 - ElevatorScene.numberOfPeopleInElevator; i++){
				ElevatorScene.elevatorDoorInSemaphore[ElevatorScene.floorCount].release(); 
			}
			
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			ElevatorScene.addPersonToWaitLine = false;
			
			if(ElevatorScene.scene.getNumberOfPeopleWaitingAtFloor(ElevatorScene.floorCount) == 0){
				
				for(int i = 0 ; i < (6 - ElevatorScene.numberOfPeopleInElevator); i++){
					
					try {
						ElevatorScene.elevatorDoorInSemaphore[ElevatorScene.floorCount].acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				}
			}
			
			ElevatorScene.addPersonToWaitLine = true;
			
						
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
