package com.ru.usty.elevator;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;


public class ElevatorScene {

	public static ElevatorScene scene; 
	private Thread elevatorThread = null;
	
	//Semaphores
	public static Semaphore[] elevatorDoorInSemaphore; 
	public static Semaphore[] elevatorDoorOutSemaphore; 
	public static Semaphore personCountMutex;
	public static Semaphore floorCountMutex; 
	public static Semaphore elevatorCountMutex;
	public static Semaphore countOutMutex;
	public static Semaphore inSem; 
	public static Semaphore outSem;
	
	//Counting variables
	public static int floorCount = 0; 
	public static int numberOfPeopleInElevator = 0; 
	public static int[] numberOfPeopleForDestFloor;
	ArrayList<Integer> personCount;
	
	//Boolean
	public static boolean addPersonToWaitLine;
	public static boolean elevatorsMayDie;
	public static boolean elevatorMove;
	
	//Final int in milliseconds to let elevator sleep
	public static final int VISUALIZATION_WAIT_TIME = 500;
	
	//Used when initializing the program
	public int numberOfFloors;
	private int numberOfElevators;
	
	//Added for better visualization (from teacher)
	ArrayList<Integer> exitedCount = null;
	public static Semaphore exitedCountMutex;

	public void personExitsAtFloor(int floor) {
		try {
			
			exitedCountMutex.acquire();
			exitedCount.set(floor, (exitedCount.get(floor) + 1));
			exitedCountMutex.release();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getExitedCountAtFloor(int floor) {
		if(floor < getNumberOfFloors()) {
			return exitedCount.get(floor);
		}
		else {
			return 0;
		}
	} //End of added code from teacher

	//Base function: Initializes variables before starting the elevator
	public void restartScene(int numberOfFloors, int numberOfElevators) {
	
		//In the beginning of a scene we will kill off all elevatorThreads that are alive.
		elevatorsMayDie = true;
		
		if(elevatorThread != null){
			
			if(elevatorThread.isAlive()){
				
				try {
					
					elevatorThread.join();
				} catch (InterruptedException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		/*Code for when we have more than one elevator thread
		 * for(Thread tread : elevatorThreads)
		{
			if(thread != null)
			{
				if(thread.isAlive())
				{
					thread.join();
				}
			}
		}*/
		
		//Initialize variables
		elevatorsMayDie = false;	
		elevatorMove = true;
		scene = this;	
		this.numberOfFloors = numberOfFloors;
		this.numberOfElevators = numberOfElevators;	
		numberOfPeopleForDestFloor = new int[numberOfFloors];
		addPersonToWaitLine = true;
		
		//Initialize personCount to 0 for each floor
		personCount = new ArrayList<Integer>();
		for(int i = 0; i < numberOfFloors; i++) {
			this.personCount.add(0);
		}
		
		//Arrays of semaphores
		//to go in
		elevatorDoorInSemaphore = new Semaphore[numberOfFloors];  
		//to go out
		elevatorDoorOutSemaphore = new Semaphore[numberOfFloors];
			
		//Mutex
		personCountMutex = new Semaphore(1);
		elevatorCountMutex = new Semaphore(1);
		floorCountMutex = new Semaphore(1); 
		countOutMutex = new Semaphore(1);
		inSem = new  Semaphore(1);
		outSem = new Semaphore(1);
		
		//Initialize the array for in semaphores to 0
		for(int i = 0 ; i < numberOfFloors; i++){ 
			
			elevatorDoorInSemaphore[i] = new Semaphore(0);
		}
		
		//Initialize the array for out semaphores to 0
		for(int i = 0 ; i < numberOfFloors; i++){ 
			
			elevatorDoorOutSemaphore[i] = new Semaphore(0);
		}
	
		//Add an elevator thread and start it
		elevatorThread = new Thread(new Elevator());
		elevatorThread.start();
		
		/*This is for when we have more than one elevator thread
		*ArrayList<Thread> elevatorThreads = new ArrayList<Thread>();
		*elevatorThreads.add(elevatorThread);
		*/

		//Code added from teacher for better visualization
		if(exitedCount == null) {
			exitedCount = new ArrayList<Integer>();
		}
		else {
			exitedCount.clear();
		}
		for(int i = 0; i < getNumberOfFloors(); i++) {
			this.exitedCount.add(0);
		}
		exitedCountMutex = new Semaphore(1);
		//End of added code
		
	}

	//Base function: Adds new person threads
	public Thread addPerson(int sourceFloor, int destinationFloor) {
		
		//Create new thread for the person and start it.
		Thread thread = new Thread(new Person(sourceFloor, destinationFloor));
		thread.start();
		
		//Returns the person to the system.
		return thread;  
	}

	//****Begin functions for floor count****//
	
	//Base function: Returns the floor number for the elevator
	public int getCurrentFloorForElevator(int elevator) {
		
		return floorCount;
	}
	
	//Base function: Decrements floorCount
	public void decrementElevatorFloor(int elevator) {
		
		try {
			//Use Mutex cause only one can use this critical section at one time
			floorCountMutex.acquire();
				floorCount -= 1;
			floorCountMutex.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	//Basic function: Increments floorCount
	public void incrementElevatorFloor(int elevator) {
		
		try {
			//Use Mutex cause only one can use this critical section at one time
			floorCountMutex.acquire();
				floorCount += 1;
			floorCountMutex.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//****End functions for floor count****//
	//****Begin functions for number of people in elevator****//

	//Base function: Returns the amount of people in the elevator
	public int getNumberOfPeopleInElevator(int elevator) {
		
		return numberOfPeopleInElevator;
	}
	
	//Basic function: Decrements the number of people in elevator
	public void decrementNumberOfPeopleInElevator(int elevator){
		
		try {
			//Use Mutex cause only one can use this critical section at one time
			elevatorCountMutex.acquire();
				numberOfPeopleInElevator--;
			elevatorCountMutex.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Basic function: Increments the number of people in elevator
	public void incrementNumberOfPeopleInElevator(int elevator){
		
		try {
			//Use Mutex cause only one can use this critical section at one time
			elevatorCountMutex.acquire();
				numberOfPeopleInElevator++;
			elevatorCountMutex.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	//****End functions for number of people in elevator****//
	//****Begin functions for number of people for destination floor****//
	
	//Basic function: Decrements the number of people for a certain destination floor
	public void decrementNumberOfPeopleForDestFloor(int floor){
		
		try {
			//Use Mutex cause only one can use this critical section at one time
			countOutMutex.acquire();
				ElevatorScene.numberOfPeopleForDestFloor[floor] -= 1;
			countOutMutex.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}
	
	//Basic function: Increments the number of people for a certain destination floor
	public void incrementNumberOfPeopleForDestFloor(int floor){
		
		try {
			//Use Mutex cause only one can use this critical section at one time
			countOutMutex.acquire();
				ElevatorScene.numberOfPeopleForDestFloor[floor] += 1;
			countOutMutex.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	//****End functions for number of people for destination floor****//
	//****Begin functions for number of people for waiting at floor****//

	//Base function: Returns the number of people waiting at a particular floor
	public int getNumberOfPeopleWaitingAtFloor(int floor) {

		return personCount.get(floor);
	}
	
	//Basic function: Decrements the number of people waiting at a particular floor
	public void decrementNumberOfPeopleWaitingAtFloor(int floor){
		
		try {
			//Use Mutex cause only one can use this critical section at one time
			personCountMutex.acquire();
				personCount.set(floor, personCount.get(floor) - 1);
			personCountMutex.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	//Basic function: Increments the number of people waiting at a particular floor
	public void increamentNumberOfPeopleWaitingAtFloor(int floor){
		
		try {
			//Use Mutex cause only one can use this critical section at one time
			personCountMutex.acquire();
				personCount.set(floor, personCount.get(floor) + 1);
			personCountMutex.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
	}
	
	//****End functions for number of people for waiting at floor****//

	//Base function: Returns number of floors the elevator has
	public int getNumberOfFloors() {
		
		return numberOfFloors;
	}

	//Base function: Set number of floors
	public void setNumberOfFloors(int numberOfFloors) {
		
		this.numberOfFloors = numberOfFloors;
	}

	//Base function: Returns how many elevators the program is running
	public int getNumberOfElevators() {
		
		return numberOfElevators;
	}

	//Base function: Sets number of elevators
	public void setNumberOfElevators(int numberOfElevators) {
		
		this.numberOfElevators = numberOfElevators;
	}

	//****Code from teacher, not used by us ***//
	
	//Base function: no need to change unless you choose
	//				 not to "open the doors" sometimes
	//				 even though there are people there
	public boolean isElevatorOpen(int elevator) {

		return isButtonPushedAtFloor(getCurrentFloorForElevator(elevator));
	}
	//Base function: no need to change, just for visualization
	//Feel free to use it though, if it helps
	public boolean isButtonPushedAtFloor(int floor) {

		return (getNumberOfPeopleWaitingAtFloor(floor) > 0);
	}

}
