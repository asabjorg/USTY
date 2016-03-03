package com.ru.usty.elevator;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * The base function definitions of this class must stay the same
 * for the test suite and graphics to use.
 * You can add functions and/or change the functionality
 * of the operations at will.
 *
 */

public class ElevatorScene {
	
	public static Semaphore[] elevatorDoorInSemaphore; //�urfum xx margar svona semphores
	
	public static Semaphore[] elevatorDoorOutSemaphore; //�urfum xx margar svona semphores
	
	public static Semaphore personCountMutex;
	
	public static Semaphore floorCountMutex; 
	
	public static Semaphore elevatorCountMutex;
	
	public static Semaphore countOutMutex;
	
	public static ElevatorScene scene; 
	
	public static int floorCount = 0; 
	
	public static int numberOfPeopleInElevator = 0; 
	
	public static int[] numberOfPeopleForDestFloor;
	
	public static boolean addPersonToWaitLine;
	
	public static boolean elevatorsMayDie;
	
	public static boolean elevatorMove;
	
	private Thread elevatorThread = null;
	
	public static Semaphore inSem; 
	public static Semaphore outSem;

	
	//TO SPEED THINGS UP WHEN TESTING,
	//feel free to change this.  It will be changed during grading
	public static final int VISUALIZATION_WAIT_TIME = 13;  //milliseconds
	
	public int numberOfFloors;
	private int numberOfElevators;
	

	ArrayList<Integer> personCount; //use if you want but
									//throw away and
									//implement differently
									//if it suits you
	
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

	//Base function: definition must not change
	//Necessary to add your code in this one
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
		elevatorsMayDie = false;	
		elevatorMove = true;
		
		scene = this;
		
		this.numberOfFloors = numberOfFloors;
		this.numberOfElevators = numberOfElevators;
		
		numberOfPeopleForDestFloor = new int[numberOfFloors];
		addPersonToWaitLine = true;
		
		personCount = new ArrayList<Integer>();
		for(int i = 0; i < numberOfFloors; i++) {
			this.personCount.add(0);
		}
		
		//arrays of semephores
		elevatorDoorInSemaphore = new Semaphore[numberOfFloors]; //create an array of semphores to go in 
		elevatorDoorOutSemaphore = new Semaphore[numberOfFloors];//create an array of semphores to go out
		
		
		//Mutex
		personCountMutex = new Semaphore(1);
		elevatorCountMutex = new Semaphore(1);
		floorCountMutex = new Semaphore(1); 
		countOutMutex = new Semaphore(1);
		inSem = new  Semaphore(1);
		outSem = new Semaphore(1);
		
		for(int i = 0 ; i < numberOfFloors; i++){ //init the array for in sem. to 0
			
			elevatorDoorInSemaphore[i] = new Semaphore(0);
		}
		
		for(int i = 0 ; i < numberOfFloors; i++){ //init the array for out to sem. to 0
			
			elevatorDoorOutSemaphore[i] = new Semaphore(0);
		}
	
		//Add an elevator thread and start it
		elevatorThread = new Thread(new Elevator());
		/*This is for when we have more than one elevator thread
		*ArrayList<Thread> elevatorThreads = new ArrayList<Thread>();
		*elevatorThreads.add(elevatorThread);
		*/
		elevatorThread.start();
		
		
		/**
		 * Important to add code here to make new
		 * threads that run your elevator-runnables
		 * 
		 * Also add any other code that initializes
		 * your system for a new run
		 * 
		 * If you can, tell any currently running
		 * elevator threads to stop
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

	//Base function: definition must not change
	//Necessary to add your code in this one
	public Thread addPerson(int sourceFloor, int destinationFloor) {
		
		//create new thread for the person and start it. Make it run.
		Thread thread = new Thread(new Person(sourceFloor, destinationFloor));
		
		thread.start();
		
		/**
		 * Important to add code here to make a
		 * new thread that runs your person-runnable
		 * 
		 * Also return the Thread object for your person
		 * so that it can be reaped in the testSuite
		 * (you don't have to join() yourself)
		 */

		//dumb code, replace it!
		personCount.set(sourceFloor, personCount.get(sourceFloor) + 1);
		
		//return the person into the system
		return thread;  //this means that the testSuite will not wait for the threads to finish
	}

	//Base function: definition must not change, but add your code
	public int getCurrentFloorForElevator(int elevator) {
		
		return floorCount;
	}
	
	public void decrementElevatorFloor(int elevator) {
		
		try {
			floorCountMutex.acquire();
				floorCount -= 1;
			floorCountMutex.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public void incrementElevatorFloor(int elevator) {
		try {
			floorCountMutex.acquire();
				floorCount += 1;
			floorCountMutex.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Base function: definition must not change, but add your code
	public int getNumberOfPeopleInElevator(int elevator) {
		
		return numberOfPeopleInElevator;
	}
	
	public void decrementNumberOfPeopleInElevator(int elevator){
		
		try {
			elevatorCountMutex.acquire();
				numberOfPeopleInElevator--;
			elevatorCountMutex.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void incrementNumberOfPeopleForDestFloor(int floor){
		
		try {
			countOutMutex.acquire();
				ElevatorScene.numberOfPeopleForDestFloor[floor] += 1;
				countOutMutex.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void decrementNumberOfPeopleForDestFloor(int floor){
		
		try {
			countOutMutex.acquire();
				ElevatorScene.numberOfPeopleForDestFloor[floor] -= 1;
				countOutMutex.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}
	
	public void incrementNumberOfPeopleInElevator(int elevator){
		
		try {
			elevatorCountMutex.acquire();
				numberOfPeopleInElevator++;
			elevatorCountMutex.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	//Base function: definition must not change, but add your code
	public int getNumberOfPeopleWaitingAtFloor(int floor) {

		return personCount.get(floor);
	}
	
	public void decrementNumberOfPeopleWaitingAtFloor(int floor){
		
		try {
			//put Mutex cause only one can use this critical section at one time
			personCountMutex.acquire();
				personCount.set(floor, personCount.get(floor) - 1);
			personCountMutex.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public void increamentNumberOfPeopleWaitingAtFloor(int floor){
		
		try {
			//put Mutex cause only one can use this critical section at one time
			personCountMutex.acquire();
				personCount.set(floor, personCount.get(floor) + 1);
			personCountMutex.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}

	//Base function: definition must not change, but add your code if needed
	public int getNumberOfFloors() {
		return numberOfFloors;
	}

	//Base function: definition must not change, but add your code if needed
	public void setNumberOfFloors(int numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
	}

	//Base function: definition must not change, but add your code if needed
	public int getNumberOfElevators() {
		return numberOfElevators;
	}

	//Base function: definition must not change, but add your code if needed
	public void setNumberOfElevators(int numberOfElevators) {
		this.numberOfElevators = numberOfElevators;
	}

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
