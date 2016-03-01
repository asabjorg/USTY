package com.ru.usty.elevator;

public class Elevator implements Runnable  {
	
	int elevatorNumber;
	
	//Constructor to take in the number of the elevator, not needed with only one elevator in scene
	public Elevator(int elevatorNumber){
		this.elevatorNumber = elevatorNumber;
	}

	
	@Override
	public void run() {
		
		while(true){
			//If this is true, return to ElevatorScene
			if(ElevatorScene.elevatorsMayDie){
				return;
			}
			
			//not sure if we need this temp variable - Ása
			//since numberOfPeopleInElevator changes during the execution of the loop
			//this int temp is necessary - Petra
			int tempNumberOfPeopleInElevator = (6 - ElevatorScene.scene.getNumberOfPeopleInElevator(this.elevatorNumber));;
			
			for(int i=0; i < tempNumberOfPeopleInElevator; i++){
				ElevatorScene.elevatorDoorInSemaphore[ElevatorScene.scene.getCurrentFloorForElevator(this.elevatorNumber)].release(); 
			}
			
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//goes in to the critical section
			ElevatorScene.addPersonToWaitLine = false;
			
			//taking back release with acquire if the elevator is leaving with empty spaces. 
			for(int i = 0 ; i < (6 - ElevatorScene.scene.getNumberOfPeopleInElevator(this.elevatorNumber)); i++){
				
				try {
					ElevatorScene.elevatorDoorInSemaphore[ElevatorScene.scene.getCurrentFloorForElevator(this.elevatorNumber)].acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
			}
			
			if(ElevatorScene.scene.getCurrentFloorForElevator(this.elevatorNumber) == (ElevatorScene.scene.getNumberOfFloors() - 1)){
				ElevatorScene.scene.floorCount[this.elevatorNumber] = 0; 	
			}
			else{	
				ElevatorScene.scene.incrementElevatorFloor(this.elevatorNumber);
			}
			
			//leaves the critical section
			ElevatorScene.addPersonToWaitLine = true;
			
						
			
			
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			//við þurfum ekki þessa temp breytu held ég en mér gæti skjátlast, þetta virðsit virka eins án hennar
			//since numberOfPeopleInElevator changes during the execution of the loop
			//this int temp is necessary - Petra
			int tempNumberOfPeopleForDestFloor = ElevatorScene.scene.getNumberOfPeopleForDestFloor(this.elevatorNumber);
					
			for(int i=0; i < tempNumberOfPeopleForDestFloor; i++){
				ElevatorScene.elevatorDoorOutSemaphore[ElevatorScene.scene.getCurrentFloorForElevator(this.elevatorNumber)].release(); //signal
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
