package com.ru.usty.elevator;

public class Elevator implements Runnable  {

	
	@Override
	public void run() {
		
		while(true){
			System.out.println("On floor: " + ElevatorScene.scene.floorCount);
			System.out.println("Person in elevator before in: " + ElevatorScene.numberOfPeopleInElevator);
			
			ElevatorScene.scene.addPersonToWaitLine = false;
			int tempNumberOfPeopleInElevator = 6 - ElevatorScene.numberOfPeopleInElevator;
			
			for(int i=0; i < tempNumberOfPeopleInElevator; i++){
				ElevatorScene.elevatorDoorInSemaphore[ElevatorScene.floorCount].release(); 
				System.out.println("In door " + ElevatorScene.floorCount);
			}
			ElevatorScene.scene.addPersonToWaitLine = true;
			
			System.out.println("Person in elevator after in: " + ElevatorScene.numberOfPeopleInElevator);
			
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
			
			System.out.println("On floor: " + ElevatorScene.scene.floorCount);
			System.out.println("Person in elevator before out: " + ElevatorScene.numberOfPeopleInElevator);
			
			int tempNumberOfPeopleForDestFloor = ElevatorScene.scene.numberOfPeopleForDestFloor[ElevatorScene.floorCount];
			
			for(int i=0; i < tempNumberOfPeopleForDestFloor; i++){
				ElevatorScene.elevatorDoorOutSemaphore[ElevatorScene.floorCount].release(); //signal
				System.out.println("Out door " +  ElevatorScene.floorCount);
			}
			
			System.out.println("Person in elevator after out: " + ElevatorScene.numberOfPeopleInElevator);
			System.out.println("-------------------------------------");
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
