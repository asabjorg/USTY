package com.ru.usty.elevator;

public class Person implements Runnable{

	int sourceFloor, destFloor;
	
	public Person(int sourceFloor, int destFloor){
		
		this.sourceFloor = sourceFloor; 
		this.destFloor = destFloor;
		
	}

	
	@Override
	public void run() {
		
		try {
	
			//making them wait if elevator is in critical section
			while(!ElevatorScene.addPersonToWaitLine){} 
				
				//wait for their turn to go into the elevator
				ElevatorScene.elevatorDoorInSemaphore[this.sourceFloor].acquire();
			
				//I'm off the floor so I decrement the number for the floor
				ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(this.sourceFloor);
				
				//I'm in the elevator
				ElevatorScene.scene.incrementNumberOfPeopleInElevator(0);
				ElevatorScene.scene.incrementNumberOfPeopleForDestFloor(this.destFloor);
				
				
				//I want to go out of the elevator
				ElevatorScene.elevatorDoorOutSemaphore[this.destFloor].acquire();
				
				
				//I'm off the elevator
				ElevatorScene.scene.decrementNumberOfPeopleInElevator(0);
				ElevatorScene.scene.decrementNumberOfPeopleForDestFloor(this.destFloor);
				
				//Added for better visualization, code from teacher
				ElevatorScene.scene.personExitsAtFloor(this.destFloor);
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		 
		
	}
	
	

}
