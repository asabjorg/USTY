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
			
			//wait for their turn to go into the elevator
			ElevatorScene.elevatorDoorInSemaphore.acquire();
			//I'm off the floor so I decrement the number 
			ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(sourceFloor);
			
			
			System.out.println("Thread relesaed");
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		
	}
	
	

}
