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
			
			ElevatorScene.elevatorWaitMutex.acquire();
			//wait for their turn to go into the elevator
				ElevatorScene.elevatorDoorInSemaphore.acquire();
				System.out.println("aquire person");

				
			ElevatorScene.elevatorWaitMutex.release();
			
			//I'm off the floor so I decrement the number 
			ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(0);
			System.out.println(ElevatorScene.scene.getNumberOfPeopleWaitingAtFloor(0));
			
			//I want to go out of the elevator
			ElevatorScene.scene.incrementNumberOfPeopleInElevator(0);
			System.out.println(ElevatorScene.numberOfPeopleInElevator);
			
			ElevatorScene.elevatorDoorOutSemaphore.acquire();
				ElevatorScene.scene.decrementNumberOfPeopleInElevator(0);
				//System.out.println(ElevatorScene.numberOfPeopleInElevator);
		
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		
	}
	
	

}
