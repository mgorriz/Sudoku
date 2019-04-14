import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;


public class ControllerGUI implements Observer, ActionListener {

	private Model model;
	private ViewGUI view;
	
	public ControllerGUI(Model model, ViewGUI view){
		this.model = model;
		this.view = view;
		
		model.addObserver(this);
		
//		view.getKnopf().addActionListener(this);
//		view.getText().setText(String.valueOf(model.getZahl()));
		
		view.setVisible(true);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0 == model){
//			view.getText().setText(String.valueOf(model.getZahl()));
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand()) {
/*		case ViewGUI.HOCHZAEHLEN:
			model.zaehlen();
			break;
*/
		case 
		default:
			System.out.println("Unbekannte Action " + ae.getActionCommand());
			break;
		}
		
	}

}
