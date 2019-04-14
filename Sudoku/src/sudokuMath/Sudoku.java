/**
 * @author Michael Gorriz
 * gorriz.de
 * 
 * This package contains all the classes which are needed 
 * to solve a Sudoku. You need ot 
 * 
 */
package sudokuMath;

public class Sudoku { 
    
    private boolean foundOneSolution = false;
    private SudokuMatrix resultSudokuMatrix=null;
    private int noOfIterations=0;
    
    public int getNoOfIterations() {
	return noOfIterations;
    }


    
    private void loadSudokuFromIntArray (int[] rawInput, SudokuMatrix mySudokuMatrix) {
	/* The method expects an integer array with 81 integer values from 0 to 9 included.
	 * Values outside that range will be set to 0.
	 * The outer parameter is the row counter.
	 * The inner parameter is the column counter.
	 * This means that the Sudoku needs to be entered from left to right, top to bottom.		
	 */
	int tempInt =0;
	for (int i=0; i<9; i++) {
	    // i is counting the rows
	    for (int j=0; j<9; j++) {
		// j is counting the columns
		tempInt = rawInput[i*9 + j];
		if ((tempInt >= 1) & (tempInt <= 9)) {
		    mySudokuMatrix.setValueOfField (i, j, tempInt, true, false);
		//    setmySudokuMatrix.sudokuArray[i][j].value=tempInt;    
		}
	    }
	}			
    }

    private void loadSudokuToIntArray(int[] inputArray, SudokuMatrix mySudokuMatrix) {
	for (int i=0; i<9; i++) {
	    // i is counting the rows
	    for (int j=0; j<9; j++) {
		// j is counting the columns
		inputArray[i*9 + j] = mySudokuMatrix.sudokuArray[i][j].value;
	    }
	}

    }
    public static void printSudokuArrayToConsole ( int[] printArray){
	/* This prints an int array (9x9) to the console.
	 * It takes any int array as input and assumes that
	 * there are at least 81 elements in the array. 
	 * Otherwise it exists without action.
	 */
	if (printArray.length>=81) {
	    System.out.println("-------------------------");
	    for (int i=0; i<9; i++) {
		System.out.print("| ");
		for (int j=0; j<9; j++) {
		    if (printArray[i*9+j] != 0) 
			System.out.print ( printArray[i*9+j]+" " );
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
    }

    public static void printSudokuToConsole (SudokuMatrix mySudokuMatrix){
	/* This take a SudokuMatrix as input and prints it to the console.
	 */
	System.out.println("-------------------------");
	for (int i=0; i<9; i++) {
	    System.out.print("| ");
	    for (int j=0; j<9; j++) {
		if (mySudokuMatrix.sudokuArray[i][j].value != 0) 
		    System.out.print ( mySudokuMatrix.sudokuArray[i][j].value+" " );
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

    static int findFirstInBlock ( int counter){
	/* returns the first index in blocks of 3
	 * e.g. for 0, 1 and 2 it returns 0, 
	 * for 3, 4 and 5 it return 3 and 6 for the rest
	 * Integer division and then multiplied by 3
	 */
	return (counter/3)*3;
    }

    class SudokuMatrix {
	SudokuField[][] sudokuArray = new SudokuField[9][9];
	private boolean impossibleSudoku;
	private boolean hasAmbiguitySudoku;
	private boolean valueChanged;
//	private int noOfIterations;
	private int interimRow;
	private int interimColumn;
	
	SudokuMatrix () {
	    /* constructor for the SudokuMatrix class
		   It will generate the individual fields for the whole sudokuArray
		   and set the inital values for each field
	     */
	    impossibleSudoku=true;
	    hasAmbiguitySudoku=true;
	    valueChanged=false;
	    noOfIterations=0;
	    interimRow=0;
	    interimColumn=0;
	    boolean[] trueBoolean = {true, true, true, true, true, true, true, true, true};
	    for (int i=0; i<9; i++) {
		for (int j=0; j<9; j++) {
		    sudokuArray[i][j]=new SudokuField(0,false,false,trueBoolean);
		}
	    }
	}	

	private void copy (SudokuMatrix org, SudokuMatrix dest){
	    dest.hasAmbiguitySudoku=org.hasAmbiguitySudoku;
	    dest.impossibleSudoku = org.impossibleSudoku;
	    dest.interimColumn = org.interimColumn;
	    dest.interimRow = org.interimRow;
//	    dest.noOfIterations = org.noOfIterations;
	    dest.valueChanged = org.valueChanged;
	    for (int i=0; i<9; i++) {
		for (int j=0; j<9; j++) {
		    dest.sudokuArray[i][j].value = org.sudokuArray[i][j].value;    
		    dest.sudokuArray[i][j].isSolved = org.sudokuArray[i][j].isSolved;
		    dest.sudokuArray[i][j].wasSet = org.sudokuArray[i][j].wasSet;
		    for (int k=0;k<9;k++){
			dest.sudokuArray[i][j].potentialValue[k] = org.sudokuArray[i][j].potentialValue[k];
		    }
		}
	    }
	}
	
	class SudokuField {
	    int value=0;
	    boolean isSolved=false;
	    boolean wasSet=false;
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
	    SudokuField(int value, boolean wasSet, boolean isSolved, boolean[] potentialValue) {
		this.value = value;
		this.wasSet = wasSet;
		this.isSolved = isSolved;
		this.potentialValue = new boolean[9];
		for (int k=0;k<9;k++){
		    this.potentialValue[k]=potentialValue[k];
		}	
	    }
	    SudokuField() {
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

	    boolean hasOneSolution() {
		/* The method checks whether there is only one field in the 
		 * boolean array which is true.
		 */
		return this.noOfBooleanTrue() == 1;
	    }

	    int solutionOfField() {
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

	    boolean hasNoSolution() {
		/* The method checks whether there is no field in the 
		 * boolean array which is true.
		 */
		return this.noOfBooleanTrue() == 0;
	    }
	}
  
	private boolean doubleValueInBlock(int i, int j) {
	    /* This method returns true if the value in the sudokuArray[i][j] is 
	     * found in the same block another time
	     */
	    int iStartRow, iStartColumn;
	    iStartRow = Sudoku.findFirstInBlock (i);
	    iStartColumn = Sudoku.findFirstInBlock(j);
	    /*
	     * The two loops runs through the block in which the element(i,j)
	     * sits and determines whether there is an identical value in
	     * another field. It excludes the field which is examined
	     * and returns a true if a duplicate is found.
	     */
	    for (int ii = iStartRow; ii < iStartRow + 3; ii++){
		for (int jj = iStartColumn; jj < iStartColumn + 3; jj++)
		    if (!((ii==i) && (jj==j))){
			if ((sudokuArray[ii][jj].value!=0) && (sudokuArray[ii][jj].value==sudokuArray[i][j].value)){
			    return true;
			};
		    }
	    }	
	    return false;
	}

	private boolean doubleValueInColumn(int i, int j) {
	    /* This method returns true if the value in the sudokuArray[i][j] is 
	     * found in the same column another time
	     */
	    for (int jj = 0; jj < 9; jj++){
		    if (!(jj==j)){
			if ((sudokuArray[i][jj].value!=0) && (sudokuArray[i][jj].value==sudokuArray[i][j].value)){
			    return true;
			};
		    }
	    }
	    return false;
	}

	private boolean doubleValueInRow(int i, int j) {
	    /* This method returns true if the value in the sudokuArray[i][j] is 
	     * found in the same row another time
	     */
	    for (int ii = 0; ii < 9; ii++){
		    if (!(ii==i)){
			if ((sudokuArray[ii][j].value!=0) && (sudokuArray[ii][j].value==sudokuArray[i][j].value)){
			    return true;
			};
		    }
	    }
	    return false;
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
	    /* This loop runs through the column of that specific field
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
	    /* This loop runs through the rows of that specific field 
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

	private void setValueOfField ( int row, int column, int fieldValue , boolean wasSet , boolean isSolved){
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
	
	private int reduceAndAnalyseSudokuMatrix () {
	    /* reducing the number of possible fields
	     * Looks for field which have no ambiguities.
	     * These are the ones which can be set right away.
	     * 
	     * It will go through the whole Sudoku and does the following <pseudocode>
	     * 
	     * loop through the whole sudoku as long as a change has been made  
	     *     check every field which is not set upfront if
	     *         onePossibiliy then valueChanged to true and exit loop
	     *         noPossibility then solvable to false and exit loop
	     *         
	     *  it will return 0 when the sudoku is solved and no ambiguity left,
	     *  return -1 if impossible
	     *  return noOfUnsolvedFields if ambiguity exists
	     *  
	     *  It sets the global variable noOfIterations 
	     */

//	    noOfIterations = 0;
	    int noOfUnsolvedFields;
	    do {
		valueChanged = false;
		impossibleSudoku = false;
		noOfUnsolvedFields = 0;
		for (int i=0; i<9; i++) {
		    // i is counting the rows
		    for (int j=0; j<9; j++) {
			// j is counting the columns
			// Checks whether there is only one possibility left
			if (sudokuArray[i][j].value == 0 ){
			    noOfUnsolvedFields++;
			    if (sudokuArray[i][j].hasOneSolution() ){
				int solution = sudokuArray[i][j].solutionOfField();
				setValueOfField(i, j, solution, false, true);
				valueChanged=true;
			    } 
			    else if (sudokuArray[i][j].hasNoSolution() ) {
				impossibleSudoku = true;
			    }
			} 
		    }
		}     
		noOfIterations++;
	    } while (valueChanged && !impossibleSudoku);
	    hasAmbiguitySudoku = (noOfUnsolvedFields > 0);
	    if (impossibleSudoku) return -1;
	    return noOfUnsolvedFields ;
	}
	
	private void findNextAmbiguousField() {
	    /* finds the next ambiguous field and stores the indices in the 
	     * variables interimRow and interimColumn
	     */
	    search:
		for (int i=0; i<9 ; i++) {
		    // i is counting the rows
		    for (int j=0; j<9; j++) {
			// j is counting the columns
			if (this.sudokuArray[i][j].noOfBooleanTrue()>1) {
			    this.interimRow = i;
			    this.interimColumn = j;
			    break search;
			}
		    }			
		}
	}

	private boolean isPossibleSudoku() {
	    /* Checks whether the Sudoku is allowed and does not infringe with the rules
	     * It returns false if rules are broken and true if everything is possible
	     */
	    boolean result=true;
	    foundBad:		
	    for (int i=0; i<9 ; i++) {
		// i is counting the rows
		for (int j=0; j<9; j++) {
		    // j is counting the columns
		    if(sudokuArray[i][j].value>0) {
			if (	doubleValueInRow (i,j) || 
				doubleValueInColumn (i,j) || 
				doubleValueInBlock (i,j) ) {
			    result = false;
			    break foundBad;
			}
		    }
		}
	    }	
	    return result;
	}

    } // end of class SudokuMatrix

    private void solveSudoku(SudokuMatrix mySudokuMatrix) {
	/*
	 *  Here I test whether the sudoku has been solved by checking the 
	 *  global variable foundOneSolution and we end the recursion.
	 *  Not super-elegant but I did not figure out a better way to do it
	 */
	if (foundOneSolution) {
	    return;
	    }
	/* I first check whether an easy solution exists.
	 * This means there are no ambiguities in the sudoku
	 * and therefore we have the solution right away. This is indicated by 
	 * the result of the reduceAndAnalyseSudokuMatrix returns a 0 
	 * which means there are no ambiguities left.
	 */
	int result = mySudokuMatrix.reduceAndAnalyseSudokuMatrix();
	switch (result) { 
	case 0: 
	    foundOneSolution = true;
	    resultSudokuMatrix=mySudokuMatrix;
	    return;
	case -1: 
	    return;
	default:
	    /*
	     * If there are ambiguities (i.e. result > 0 ) we start the 
	     * backtracking by reducing the problem. I look for the first ambiguous field
	     * and identify the first possible value. Then we create a new 
	     * sudoku (leftSudokuMatrix) where we assume the one possibility as a solution 
	     * and call recursively solveSudoku.
	     * 
	     * With the rest we eliminate the potential solution we have tested with
	     * the leftSudokuMatrix and first check whether we have an easy sudoku in which
	     * case we set the global variable resultSudokuMatrix to the current value and
	     * set foundOneSolution to true which will exits the backtracking.
	     * 
	     * Otherwise we call recursively solveSudoku.
	     * 
	     * All in all a little tricky and resource intensive as we create new instances of
	     * SudokuMatrix for each recursion. In future I might find other algos to solve this
	     */
	    mySudokuMatrix.findNextAmbiguousField();
	    SudokuMatrix leftSudokuMatrix = new SudokuMatrix();
	    leftSudokuMatrix.copy(mySudokuMatrix, leftSudokuMatrix);
	    int k=0;	    
	    while (!mySudokuMatrix.sudokuArray[mySudokuMatrix.interimRow][mySudokuMatrix.interimColumn].potentialValue[k]){
		k++;
	    }
	    mySudokuMatrix.sudokuArray[mySudokuMatrix.interimRow][mySudokuMatrix.interimColumn].potentialValue[k]=false;
	    solveSudoku(mySudokuMatrix);
	    leftSudokuMatrix.setValueOfField(leftSudokuMatrix.interimRow, leftSudokuMatrix.interimColumn, k+1, true, false);
	    if ( leftSudokuMatrix.reduceAndAnalyseSudokuMatrix()==0){
		foundOneSolution = true;
		resultSudokuMatrix = leftSudokuMatrix;}
	    else {	      
		solveSudoku(leftSudokuMatrix);
	    }
	}
    }

     public boolean solveSudokuArray (int[] inputArray){
	SudokuMatrix mySudokuMatrix = new SudokuMatrix();
	loadSudokuFromIntArray (inputArray, mySudokuMatrix);
	if (!mySudokuMatrix.isPossibleSudoku()){
	    return false;
	}
	solveSudoku(mySudokuMatrix);
	if (foundOneSolution) {
	    loadSudokuToIntArray (inputArray, resultSudokuMatrix);
//	    noOfIterations = resultSudokuMatrix.noOfIterations;
	    return true;
	}
	else {
	    return false;
	}
    }

}