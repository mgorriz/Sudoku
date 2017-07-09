import java.util.Observable;
import java.util.Observer;

public class ControllerConsole implements Observer {

	Model model;
	ViewConsole view;

	public ControllerConsole(Model model, ViewConsole view) {
		this.model = model;
		this.view = view;
		
		model.addObserver(this);
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 == model) {
			view.consoleOutput(String.valueOf(model.getZahl()));
		}
	}

}
