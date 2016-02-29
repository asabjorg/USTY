package com.ru.usty.elevator;

public class Elevator implements Runnable  {

	
	@Override
	public void run() {
		
		while(true){
			
			System.out.println(ElevatorScene.numberOfPeopleInElevator);
			
			for(int i=0; i < 6 - ElevatorScene.numberOfPeopleInElevator; i++){
				
				ElevatorScene.elevatorDoorInSemaphore[ElevatorScene.floorCount].release(); 
			}
			
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
			
			System.out.println("before release floor " +  ElevatorScene.floorCount);
			

			for(int i=0; i < 6; i++){
				ElevatorScene.elevatorDoorOutSemaphore[ElevatorScene.floorCount].release(); //signal
				System.out.println("release floor " +  ElevatorScene.floorCount);
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
