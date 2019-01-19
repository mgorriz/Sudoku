import sudokuMath.*;

public class TestSudoku {

    public static void main(String[] args) {
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
  
    	// General template 
        /*
        int[] testInput = {	0, 0, 0, 0, 0, 0, 0, 0, 0, 
        					0, 0, 0, 0, 0, 0, 0, 0, 0,  
        					0, 0, 0, 0, 0, 0, 0, 0, 0,  
        					0, 0, 0, 0, 0, 0, 0, 0, 0,  
        					0, 0, 0, 0, 0, 0, 0, 0, 0,  
        					0, 0, 0, 0, 0, 0, 0, 0, 0,  
        					0, 0, 0, 0, 0, 0, 0, 0, 0,  
        					0, 0, 0, 0, 0, 0, 0, 0, 0,  
        					0, 0, 0, 0, 0, 0, 0, 0, 0 }; 
        */

    	// Hard Sudoku 
    	
        int[] testInput = {	0, 0, 0, 1, 0, 0, 5, 0, 2, 
    						0, 0, 0, 0, 9, 0, 0, 0, 0,  
    						0, 9, 8, 5, 0, 0, 0, 7, 0,  
    						0, 0, 0, 0, 6, 1, 0, 0, 0,  
    						0, 0, 5, 0, 0, 0, 0, 4, 0,  
    						9, 2, 0, 0, 5, 0, 0, 3, 0,  
    						0, 0, 0, 7, 0, 4, 0, 0, 8,  
    						0, 0, 0, 0, 0, 0, 7, 0, 9,  
    						3, 5, 0, 0, 0, 0, 0, 0, 6 };
    	
        
        int[] testOutput = new int[81];
        Sudoku mySudoku = new Sudoku();
        System.out.println("This is the input.");
        mySudoku.loadSudokuFromIntArray (testInput);
        mySudoku.printSudokuToConsole();
        if (mySudoku.solveSudoku()){
            /*	    testOutput = mySudoku.storeSudokuToIntArray(mySudoku);
            for (int i=0;i<81;i++){
             	System.out.print(testOutput[i]);
             */	
        	System.out.println("This is the solution after " + mySudoku.getNoOfIterations()+" iterations.");
        	mySudoku.printSudokuToConsole();
        }
        else
        { System.out.println("This sudoku has no solution yet.");
        mySudoku.printSudokuToConsole();}

    }

  
}
