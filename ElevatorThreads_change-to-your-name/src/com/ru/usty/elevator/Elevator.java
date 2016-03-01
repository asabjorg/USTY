package com.ru.usty.elevator;

public class Elevator implements Runnable  {

	
	@Override
	public void run() {
		
		while(true){
			
			int tempNumberOfPeopleInElevator = (6 - ElevatorScene.numberOfPeopleInElevator);
			
			for(int i=0; i < tempNumberOfPeopleInElevator; i++){
				ElevatorScene.elevatorDoorInSemaphore[ElevatorScene.floorCount].release(); 
			}
			
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			ElevatorScene.addPersonToWaitLine = false;
			
			
			for(int i = 0 ; i < (6 - ElevatorScene.numberOfPeopleInElevator); i++){
				
				try {
					ElevatorScene.elevatorDoorInSemaphore[ElevatorScene.floorCount].acquire();
					System.out.println("acquire back");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
			}
			
			if(ElevatorScene.floorCount == (ElevatorScene.scene.numberOfFloors - 1)){
				ElevatorScene.floorCount = 0; 	
			}
			else{	
				ElevatorScene.scene.incrementElevatorFloor(0);
			}
			
			ElevatorScene.addPersonToWaitLine = true;
			
						
			
			
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			//við þurfum ekki þessa temp breytu held ég en mér gæti skjátlast, þetta virðsit virka eins án hennar
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
