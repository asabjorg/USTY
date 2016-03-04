package com.ru.usty.elevator;

import com.ru.usty.elevator.visualization.TestSuite;

public class ElevatorMainProgram {
	public static void main(String[] args) {

		try {

			TestSuite.startVisualization();
			Thread.sleep(1000);
			
			//Runs one test
			/*TestSuite.runTest(2);
			Thread.sleep(2000);*/

			//Runs all test
			for(int i = 0; i <= 4; i++) {
				TestSuite.runTest(i);
				Thread.sleep(2000);
			}
		} catch (InterruptedException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.exit(0);
	}
}
