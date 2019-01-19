/**
 * @author Michael Gorriz
 * gorriz.de
 * 
 * This package contains all the classes which are needed 
 * to store a Sudoku in a Java context. It also provides methods to read and write a 
 * Sudoku from file.
 * 
 */
package sudokuMath;

public class Sudoku {

	SudokuField[][] sudokuArray = new SudokuField[9][9];

	private boolean solvableSudoku=false;
	private boolean impossibleSudoku=true;
	private boolean hasAmbiguitySudoku=true;
	
	private boolean valueChanged=false;
	private int noOfIterations = 0;
	private int interimRow = 0;
	private int interimColumn = 0;
	
	public int getNoOfIterations() {
		return noOfIterations;
	}

	public void setNoOfIterations(int noOfIterations) {
		this.noOfIterations = noOfIterations;
	}


	class SudokuField {
		int value=0;
		boolean wasSet=false;
		boolean isSolved=false;
		/*
		 * A true at the index=0 means a "1" is a possible value,
		 * true at index=1 mean a "2" is a possible value etc.
		 * A false means, the respective value is already used in
		 * row, column or block.
		 */
		boolean[] potentialValue = new boolean[9];
		/**
		 * @param value
		 * @param wasSet
		 * @param isSolved
		 * @param potentialValue
		 */
		public SudokuField(int value, boolean wasSet, boolean isSolved, boolean[] potentialValue) {
			this.value = value;
			this.wasSet = wasSet;
			this.isSolved = isSolved;
			this.potentialValue = new boolean[9];
			for (int k=0;k<9;k++){
				this.potentialValue[k]=potentialValue[k];
			}	
		}
		public SudokuField() {
			// This is a constructor which creates a default field
			// boolean[] trueBoolean = {true, true, true, true, true, true, true, true, true};
			this.value = 0;
			this.wasSet = false;
			this.isSolved = false;
			this.potentialValue = new boolean[9];
			for (int k=0;k<9;k++){
				this.potentialValue[k]=true;
			}
		}

		private int noOfBooleanTrue() {
			// counts the number of true in the boolean array potentialValue
			int tempNo = 0; 
			for (int k=0;k<9;k++){
				if (this.potentialValue[k]){
					tempNo++;
				}
			}
			return tempNo;
		}

		
		
		public boolean hasOneSolution() {
			/* The method checks whether there is only one field in the 
			 * boolean array which is true.
			 */
			return this.noOfBooleanTrue() == 1;
		}

		public int solutionOfField() {
			/* The method returns the value of the solution of the specific field.
			 * This is defined by the index of the boolean array which contains a "true"
			 * but incremented by 1.
			 */
			int k=0;
			if (this.noOfBooleanTrue() == 1){
				while (!this.potentialValue[k] && k<9){
					k++;
				}
				return k+1;
			}
			return 0;
		}

		public boolean hasNoSolution() {
			/* The method checks whether there is no field in the 
			 * boolean array which is true.
			 */
			return this.noOfBooleanTrue() == 0;
		}
	}

	public Sudoku() {
		/* constructor for the Sudoku class
	   It will generate the individual fields for the whole sudokuarray
	   and set the inital values for each field
		 */

		boolean[] trueBoolean = {true, true, true, true, true, true, true, true, true};
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				sudokuArray[i][j]=new SudokuField(0,false,false,trueBoolean);
			}
		}
	}	

	public void loadSudokuFromIntArray (int[] rawInput) {
		/* 	The method expects an integer array with 81 integer values from 0 to 9 included.
		 	Values outside that range will be set to 0.
			The outer parameter is the row counter.
			The inner parameter is the column counter.
			This means that the Sudoku needs to be entered from left to right, top to bottom.
		 */
		int tempInt =0;
		for (int i=0; i<9; i++) {
			// i is counting the rows
			for (int j=0; j<9; j++) {
				// j is counting the columns
				tempInt = rawInput[i*9 + j];
				if ((tempInt >= 1) & (tempInt <= 9)) {
					setValueOfField ( i, j, tempInt, true, false);
				}
			}
		}			
	}

	public int[] storeSudokuToIntArray (Sudoku tempSudoku) {
		/* 	The method extracts an integer array with 81 integer values 
		 * 	from 0 to 9 included.
		 *	from the tempSudoku's solved values
		 *	Values outside that range will be set to 0.
		 *	The outer parameter is the row counter.
		 *	The inner parameter is the column counter.
		 *	This means that the int array will contain the values
		 *	from left to right and top to bottom.
		 */
		int valueField;
		int[] refArray = new int[81];
		for (int i=0; i<9; i++) {
			// i is counting the rows
			for (int j=0; j<9; j++) {
				// j is counting the columns
				valueField = tempSudoku.sudokuArray[i][j].value;
				refArray[i*9 + j]=valueField;
			}
		}
		return (refArray);
	}			

	public static int findFirstInBlock ( int counter){
		/* returns the first index in blocks of 3
		 * e.g. for 0, 1 and 2 it returns 0, 
		 * 		for 3, 4 and 5 it return 3 and 6 for the rest
		 * Integer division and then multiplied by 3
		 */
		return (counter/3)*3;
	}

	private void eliminatePossibleValuesInRow ( int row, int column ){
		/* if the value of the field itself is undefined let us just return
		 * and do nothing
		 */
		if (this.sudokuArray[row][column].value == 0) {
			return;
		}
		/* This is the index for the boolean array potentialValue.
		 */
		int booleanArrayIndex = this.sudokuArray[row][column].value - 1;
		/* This loop counts through the column in that specific row
		 * and sets the respective potentialValue to false
		 */
		for (int j = 0; j < 9; j++){
			this.sudokuArray[row][j].potentialValue[booleanArrayIndex]=false;
		}
	}

	private void eliminatePossibleValuesInColumn ( int row, int column ){
		// if the value is undefined let us just return
		if (this.sudokuArray[row][column].value == 0) {
			return;
		}
		int booleanArrayIndex = this.sudokuArray[row][column].value - 1;
		/* This loop counts through the rows in that specific column 
		 * and sets the respective potentialValue to false
		 */
		for (int i = 0; i < 9; i++){
			this.sudokuArray[i][column].potentialValue[booleanArrayIndex]=false;
		}	
	}

	private void eliminatePossibleValuesInBlock ( int row, int column ){
		// if the value is undefined let us just return
		if (this.sudokuArray[row][column].value == 0) {
			return;
		}
		int booleanArrayIndex = this.sudokuArray[row][column].value - 1;
		// This loop counts through the rows and columns of the block
		// and sets the respective potentialValue to false
		int iStartRow, iStartColumn;
		iStartRow = Sudoku.findFirstInBlock (row);
		iStartColumn = Sudoku.findFirstInBlock(column);
		for (int i = iStartRow; i < iStartRow + 3; i++){
			for (int j = iStartColumn; j < iStartColumn + 3; j++ )
				this.sudokuArray[i][j].potentialValue[booleanArrayIndex]=false;
		}	
	}

	public void setValueOfField ( int row, int column, int fieldValue , boolean wasSet , boolean isSolved){
		/* 1. The function will set the value at the location (row,column) to fieldValue
		 * 2. Then it eliminates the values from the row, column and block
		 */
		this.sudokuArray[row][column].value=fieldValue;
		this.sudokuArray[row][column].wasSet=wasSet;
		this.sudokuArray[row][column].isSolved=isSolved;
		eliminatePossibleValuesInRow (row, column);
		eliminatePossibleValuesInColumn (row, column);
		eliminatePossibleValuesInBlock (row, column);
	}

	public void printSudokuToConsole (){
		System.out.println("-------------------------");
		for (int i=0; i<9; i++) {
			System.out.print("| ");
			for (int j=0; j<9; j++) {
				if (sudokuArray[i][j].value != 0) 
					System.out.print ( sudokuArray[i][j].value+" " );
				else 
					System.out.print ( "  " );
				;
				if ((j % 3) == 2) {
					System.out.print("| ");
				}
			}
			System.out.println();{
				if ((i % 3) == 2) {
					System.out.println("-------------------------");
				}
			}
		}
	}

	public boolean easySolvable () {
		/* solving algorithm for Sudokus which have no ambiguities.
		 * These are the ones which can be solved straight forward.
		 * It will go through the whole Sudoku and does the following <pseudocode>
		 * 
		 * loop through the whole sudoku until 
		 *     check every field which is not set upfront if
		 *         onePossibiliy then valueChanged to true and exit loop
		 *         noPossibility then solvable to false and exit loop
		 *  return true when the sudoku is solved and no ambiguity left
		 * 
		 */

		noOfIterations = 0;
		
		int noOfUnsolvedFields;

		do {
			valueChanged = false;
			solvableSudoku = true;
			impossibleSudoku = false;
			noOfUnsolvedFields = 0;
			for (int i=0; i<9; i++) {
				// i is counting the rows
				for (int j=0; j<9; j++) {
					// j is counting the columns
					// Checks whether there is only one possibility left
					if (this.sudokuArray[i][j].value == 0 ){
						noOfUnsolvedFields++;
						if (this.sudokuArray[i][j].hasOneSolution() ){
							int solution = this.sudokuArray[i][j].solutionOfField();
							this.setValueOfField(i, j, solution, false, true);
							valueChanged=true;
						} 
						else if (this.sudokuArray[i][j].hasNoSolution() ) {
							impossibleSudoku = true;
						}
					} 
/* Debug
 * 					System.out.println("row:"+i+" column:"+j);
 * 					this.printSudokuToConsole();
 */					
				}
			}     
			noOfIterations++;
		} while (valueChanged && !impossibleSudoku);
		hasAmbiguitySudoku = (noOfUnsolvedFields > 0);
		return !impossibleSudoku && !hasAmbiguitySudoku ;
	}
	
	public boolean solveSudoku() {
		boolean isSolvedSudoku = false;
		isSolvedSudoku = easySolvable();
		if (isSolvedSudoku) return isSolvedSudoku;
		else if (impossibleSudoku) {
			System.out.println("Impossible Sudoku");
			return false;}
		else if (hasAmbiguitySudoku) {
			findNextAmbiguousField();
			System.out.println("This Sudoku has ambiguity");
			return false;}
		else return false;
		}

	private void findNextAmbiguousField() {
		search:
			for (int i=0; i<9 ; i++) {
				// i is counting the rows
				for (int j=0; j<9; j++) {
					// j is counting the columns
					if (sudokuArray[i][j].hasOneSolution()) {
						interimRow = i;
						interimColumn = j;
						break search;
					}
				}			
			}
	}

}