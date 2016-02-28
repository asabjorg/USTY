	package com.ru.usty.elevator;

public class Elevator implements Runnable  {

	
	@Override
	public void run() {
		
		while(true){
			System.out.println("How often do I get here");
			
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

				System.out.println("Do I get in to try");
					for(int x=0; x < 6; x++){
						ElevatorScene.elevatorDoorInSemaphore.release(); //signal
						System.out.println("The elevator is open for threads");
						System.out.println(ElevatorScene.scene.getNumberOfPeopleInElevator(0));
					}

		    
			
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			if(ElevatorScene.numberOfPeopleInElevator != 6){
				try {
					ElevatorScene.elevatorWaitMutex.acquire();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
				
				//System.out.println(ElevatorScene.numberOfPeopleInElevator);
				
				for (int i = 0; i < 6 - ElevatorScene.numberOfPeopleInElevator;  i++){
					try {
						ElevatorScene.elevatorDoorInSemaphore.acquire();
						System.out.println("Inside for loop");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				ElevatorScene.elevatorWaitMutex.release();
			
			}
			//ElevatorScene.elevatorMaxMutex.release();
			ElevatorScene.scene.incrementElevatorFloor(0);
			System.out.println("The elevator is on its way up");
			

			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			for(int i=0; i < 6; i++){
				ElevatorScene.elevatorDoorOutSemaphore.release(); //signal
				System.out.println("Elevator is letting people out");
			}
			
			ElevatorScene.scene.decrementElevatorFloor(0);
			
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
