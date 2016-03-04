package com.ru.usty.elevator;

public class Elevator implements Runnable  {

	//If there would be more than one elevator
	/*int elevatorNumer;
	
	public Elevator(int elevatorNumber){
		this.elevatorNumer = elevatorNumber;
	}*/
	
	@Override
	public void run() {
		
		while(true){
			
			//If this is true, return to ElevatorScene
			if(ElevatorScene.elevatorsMayDie){
				return;
			}
			
			//Temp variable for NumberOfPeopleInElevator since it is decremented during the for loop
			int tempNumberOfPeopleInElevator = (6 - ElevatorScene.scene.getNumberOfPeopleInElevator(0));
			
			//Releases person threads from waiting line
			for(int i=0; i < tempNumberOfPeopleInElevator; i++){

				ElevatorScene.elevatorDoorInSemaphore[ElevatorScene.floorCount].release(); 
			}
			
			//Elevator sleeps
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Goes in to the critical section
			ElevatorScene.addPersonToWaitLine = false;
			
			
			//Taking back release with acquire if the elevator is leaving with empty spaces. 
			for(int i = 0 ; i < (6 - ElevatorScene.scene.getNumberOfPeopleInElevator(0)); i++){
					
				try {
					ElevatorScene.elevatorDoorInSemaphore[ElevatorScene.scene.getCurrentFloorForElevator(0)].acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 	
			}
			
			//elevatorMove is changed if the elevator is at the top or bottom
			//To know if to decrement or increment the floor count
			if(ElevatorScene.scene.getCurrentFloorForElevator(0) == (ElevatorScene.scene.getNumberOfFloors() - 1)){
				ElevatorScene.elevatorMove = false;
				 	
			} else if (ElevatorScene.scene.getCurrentFloorForElevator(0) == 0){
				ElevatorScene.elevatorMove = true;
			}
			
			//Changes the floor count
			if(ElevatorScene.elevatorMove){	
				ElevatorScene.scene.incrementElevatorFloor(0);
			} else{
				ElevatorScene.scene.decrementElevatorFloor(0);
			}
			
			//Leaves the critical section
			ElevatorScene.addPersonToWaitLine = true;
			
			//Elevator sleeps
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Temp variable for NumberOfPeopleForDestFloor since it is decremented during the for loop
			int tempNumberOfPeopleForDestFloor = ElevatorScene.scene.getNumberOfPeopleForDestFloor(ElevatorScene.scene.getCurrentFloorForElevator(0));
			
			//Elevator releases person threads from elevator
			for(int i=0; i < tempNumberOfPeopleForDestFloor; i++){
				
				ElevatorScene.elevatorDoorOutSemaphore[ElevatorScene.scene.getCurrentFloorForElevator(0)].release(); //signal
			}
			
			//Elevator sleeps
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
