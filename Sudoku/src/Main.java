
public class Main {
	public static void main(String[] args) {
		Model model = new Model();
		
		ViewGUI viewGUI = new ViewGUI();
		ControllerGUI controllerGUI = new ControllerGUI(model, viewGUI);
		
		ViewConsole viewConsole = new ViewConsole();
		ControllerConsole controllerConsole = new ControllerConsole(model, viewConsole);
			
	}
	
}
