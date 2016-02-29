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
			
			
			//System.out.println(this.sourceFloor + " and " + this.destFloor);
			
			//wait for their turn to go into the elevator
			ElevatorScene.elevatorDoorInSemaphore[this.sourceFloor].acquire();
		
			
			//I'm off the floor so I decrement the number 
			ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(this.sourceFloor);
			
			//I'm in. 
			ElevatorScene.scene.incrementNumberOfPeopleInElevator(0);
			
			//I want to go out of the elevator
			ElevatorScene.elevatorDoorOutSemaphore[this.destFloor].acquire();
			
			//I'm off
			ElevatorScene.scene.decrementNumberOfPeopleInElevator(0);
			
			//Added for better visualization, code from teacher
			ElevatorScene.scene.personExitsAtFloor(this.destFloor);
		
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		
	}
	
	

}
