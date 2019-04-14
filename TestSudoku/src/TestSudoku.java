/* 
 * version 1.0 for master branch
 */

import sudokuMath.*;



public class TestSudoku {

    public static void main(String[] args) {
	
	long startTime =0;
	
    	// Easy Sudoku 
    	/*
    	int[] testInput = {	8, 7, 6, 9, 0, 0, 0, 0, 0, 
        			0, 1, 0, 0, 0, 6, 0, 0, 0,  
        			0, 4, 0, 3, 0, 5, 8, 0, 0,  
        			4, 0, 0, 0, 0, 0, 2, 1, 0,  
        			0, 9, 0, 5, 0, 0, 0, 0, 0,  
        			0, 5, 0, 0, 4, 0, 3, 0, 6,  
        			0, 2, 9, 0, 0, 0, 0, 0, 8,  
        			0, 0, 4, 6, 9, 0, 1, 7, 3,  
        			0, 0, 0, 0, 0, 1, 0, 0, 4 };
    	*/
	/*
        int[] testInput = {	
        	5, 3, 0, 0, 7, 0, 0, 0, 0, 
		6, 0, 0, 1, 9, 5, 0, 0, 0,  
		0, 9, 8, 0, 0, 0, 0, 6, 0,  
		8, 0, 0, 0, 6, 0, 0, 0, 3,  
		4, 0, 0, 8, 0, 3, 0, 0, 1,  
		7, 0, 0, 0, 2, 0, 0, 0, 6,  
		0, 6, 0, 0, 0, 0, 2, 8, 0,  
		0, 0, 0, 4, 1, 9, 0, 0, 5,  
		0, 0, 0, 0, 8, 0, 0, 7, 9};
	*/
	
	/*
	// This is a really sparse Sudoku with minimum of 17 values set
        int[] testInput = {	
        	5, 0, 0, 0, 7, 0, 0, 0, 0, 
		6, 0, 0, 1, 0, 5, 0, 0, 0,  
		0, 0, 0, 0, 0, 0, 0, 6, 0,  
		8, 0, 0, 0, 6, 0, 0, 0, 3,  
		4, 0, 0, 8, 0, 3, 0, 0, 0,  
		0, 0, 0, 0, 0, 0, 0, 0, 6,  
		0, 6, 0, 0, 0, 0, 0, 8, 0,  
		0, 0, 0, 0, 0, 9, 0, 0, 5,  
		0, 0, 0, 0, 0, 0, 0, 0, 0};
	*/	
    	// General template 
        /*
        int[] testInput = {	
        	0, 0, 0, 0, 0, 0, 0, 0, 0, 
        	0, 0, 0, 0, 0, 0, 0, 0, 0,  
        	0, 0, 0, 0, 0, 0, 0, 0, 0,  
        	0, 0, 0, 0, 0, 0, 0, 0, 0,  
        	0, 0, 0, 0, 0, 0, 0, 0, 0,  
        	0, 0, 0, 0, 0, 0, 0, 0, 0,  
        	0, 0, 0, 0, 0, 0, 0, 0, 0,  
        	0, 0, 0, 0, 0, 0, 0, 0, 0,  
        	0, 0, 0, 0, 0, 0, 0, 0, 0 }; 
        */
	/*
        int[] testInput = {	
        	0, 0, 0, 0, 0, 0, 0, 7, 0, 
		7, 0, 0, 0, 0, 0, 0, 0, 0,  
		0, 0, 0, 7, 0, 0, 0, 0, 0,  
		0, 0, 0, 0, 0, 0, 7, 0, 0,  
		0, 7, 0, 0, 0, 0, 0, 0, 0,  
		0, 0, 0, 0, 0, 7, 0, 0, 0,  
		0, 0, 0, 0, 7, 0, 0, 0, 0,  
		0, 0, 7, 0, 0, 0, 0, 0, 0,  
		0, 0, 0, 0, 0, 0, 0, 0, 7 }; 
	*/
	
    	// Hard Sudoku 
	 	
	int[] testInput = {	
		0, 0, 0, 1, 0, 0, 5, 0, 2, 
		0, 0, 0, 0, 9, 0, 0, 0, 0,  
		0, 9, 8, 5, 0, 0, 0, 7, 0,  
		0, 0, 0, 0, 6, 1, 0, 0, 0,  
		0, 0, 5, 0, 0, 0, 0, 4, 0,  
		9, 2, 0, 0, 5, 0, 0, 3, 0,  
		0, 0, 0, 7, 0, 4, 0, 0, 8,  
		0, 0, 0, 0, 0, 0, 7, 0, 9,  
		3, 5, 0, 0, 0, 0, 0, 0, 6 };
	

        // int[] testOutput = new int[81];
	    
	
        System.out.println("This is the input.");
        Sudoku.printSudokuArrayToConsole(testInput);
        startTime=System.nanoTime();
        Sudoku mySudoku = new Sudoku();   
        if (mySudoku.solveSudokuArray(testInput)){
 //           System.out.println("This is the solution.");
 //           Sudoku.printSudokuArrayToConsole(testInput);
 
            System.out.println("This is the solution after " + mySudoku.getNoOfIterations()+" iterations.");
            Sudoku.printSudokuArrayToConsole(testInput);
        }
        else {
            System.out.println("This sudoku has no solution.");
        }
        System.out.println("This took me "+((System.nanoTime()-startTime)/1e6)+"ms");
                
    }
}
