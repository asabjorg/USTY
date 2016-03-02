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
			
			
			//checking if thread safety is on by making the thread sleep when the arrive first so 
			//there will be no line when the first elevator leaves - Ása
			/*try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			//making them wait if elevator is in critical section - Ása
			while(!ElevatorScene.addPersonToWaitLine){} 
				//System.out.println(this.sourceFloor + " and " + this.destFloor);
				
				//wait for their turn to go into the elevator
				ElevatorScene.elevatorDoorInSemaphore[this.sourceFloor].acquire();
			
				
				//I'm off the floor so I decrement the number 
				ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(this.sourceFloor);
				
				//I'm in. 
				ElevatorScene.scene.incrementNumberOfPeopleInElevator(0);
				ElevatorScene.scene.incrementNumberOfPeopleForDestFloor(this.destFloor);
				
				//I want to go out of the elevator
				ElevatorScene.elevatorDoorOutSemaphore[this.destFloor].acquire();
				
				//I'm off
				ElevatorScene.scene.decrementNumberOfPeopleInElevator(0);
				ElevatorScene.scene.decrementNumberOfPeopleForDestFloor(this.destFloor);
				
				//Added for better visualization, code from teacher
				ElevatorScene.scene.personExitsAtFloor(this.destFloor);
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		
	}
	
	

}
