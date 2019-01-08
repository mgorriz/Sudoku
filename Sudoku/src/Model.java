import java.util.Observable;

public class Model extends Observable {
	
	public void zaehlen(){
		setChanged();
		notifyObservers();
	}

	public char[] getZahl() {
		// TODO Auto-generated method stub
		return null;
	}
}	
	
