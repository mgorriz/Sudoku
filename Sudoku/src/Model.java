import java.util.Observable;


public class Model extends Observable {
	
	int zahl=0;
	
	public class SudokuField {
		int value=0;
		boolean wasSet=false;
		boolean isSolved=false;
		int[] potentialValue = new int[9];
		
		SudokuField () {
			for (int i=0; i<9 ; i++) {
			potentialValue[i]=0;
			}
		}
	}
	
	public class Sudoku {
		private  SudokuField[][] sudokuArray = new SudokuField[9][9];
		
		public Sudoku (){
		}
		
		public void setValue (int n, int x, int y) {
			sudokuArray[x][y].value=n;
			sudokuArray[x][y].wasSet=true;
		}	
	}

	
	public int getZahl() {
		return zahl;
	}
	
	public void zaehlen(){
		zahl=(zahl+1)%3;
		setChanged();
		notifyObservers();
	}
}	
	
