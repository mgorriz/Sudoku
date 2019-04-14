import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

@SuppressWarnings("serial")
public class ViewGUI extends JFrame {

    private JPanel contentPane;
    private JPanel squaresPane;


    private JFormattedTextField[] jField = new JFormattedTextField[81];
    private final static int POSSTART = 30;
    private final static int BOXWIDTH = 40;
    private final static int BOXGAP = 0;

    private class SquaresPanel extends JPanel {

	SquaresPanel() {
	    this.setOpaque(false);
	    this.setSize(420, 500);
	    this.setVisible(true);
	}

	@Override
	public void paintComponent(Graphics g) {
	    Graphics2D g2= (Graphics2D) g;
	    super.paintComponent(g2);

	    final int LINEGAP = 0;

	    for (int i=0;i<81;i++) {
		g2.drawRect(POSSTART+ (i%9)*(BOXWIDTH+BOXGAP), 
			POSSTART+ (i/9)*(BOXWIDTH+BOXGAP), 
			BOXWIDTH, BOXWIDTH);
	    };

	    g2.setStroke (new BasicStroke (3));

	    for(int i=0;i<9;i++) {
		g2.drawRect(POSSTART+ (i%3)*3*(BOXWIDTH+BOXGAP), 
			POSSTART+ (i/3)*3*(BOXWIDTH+BOXGAP), 
			3*BOXWIDTH, 3*BOXWIDTH);
	    };

	}

    }

    public ViewGUI() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(0, 0, 420, 500);
	contentPane = new JPanel();
	setContentPane(contentPane);

	JButton btnStore = new JButton("Store Values");
	btnStore.setFont(new Font("Dialog", Font.PLAIN, 18));
	btnStore.setBounds(POSSTART, 440, 160, BOXWIDTH);
	btnStore.setOpaque(false);
	btnStore.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
	    }
	});

	JButton btnSolve = new JButton("Solve");
	btnSolve.setFont(new Font("Dialog", Font.PLAIN, 18));
	btnSolve.setBounds(POSSTART+160+BOXWIDTH, 440, 160, BOXWIDTH);
	btnSolve.setOpaque(false);
	btnSolve.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
	    }
	});

	contentPane.setLayout(null);
	contentPane.add(btnSolve);
	contentPane.add(btnStore);
	contentPane.setVisible(true);

	squaresPane = new SquaresPanel();
	contentPane.add(squaresPane);



	for (int i=0;i<81;i++) {
	    jField[i] = new JFormattedTextField();
	    jField[i].setHorizontalAlignment(JFormattedTextField.CENTER);

	    jField[i].setFont(new Font("Dialog", Font.PLAIN, 24));
	    jField[i].setName("R"+Integer.toString(i%9+1)+"C"+Integer.toString(i/9));
	    jField[i].setBounds(POSSTART+ (i%9)*(BOXWIDTH+BOXGAP), 
		    POSSTART+ (i/9)*(BOXWIDTH+BOXGAP), 
		    BOXWIDTH, BOXWIDTH);
	    contentPane.add(jField[i]);
	    contentPane.setLayout(null);
	}


    }

}




