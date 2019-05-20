import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;

/*
 * Created on Tevet 5770 
 */

/**
 * @author לויאן
 */


public class MyActionListener implements ActionListener
{
	Event64 evButtonPressed;
	Event64 evShabatButtonPressed;
	Event64 evShabtButtonUnPressed;
	JRadioButton [] buttons;

	public MyActionListener(){
		 evButtonPressed = new Event64();
		 evShabatButtonPressed = new Event64();
		 evShabtButtonUnPressed = new Event64();


	}

	public void init(Event64 evButtonPressed_,Event64 evShabatButtonPressed_,
					 Event64 evShabtButtonUnPressed_, JRadioButton [] buttons_){
		evButtonPressed = evButtonPressed_;
		evShabatButtonPressed = evShabatButtonPressed_;
		evShabtButtonUnPressed = evShabtButtonUnPressed_;
		buttons = buttons_;

	}

	public void actionPerformed(ActionEvent e) 
	{
		JRadioButton butt=(JRadioButton)e.getSource();
		int key = Integer.parseInt(butt.getName());

		if(key == 16){
			if (butt.isSelected()) {
				evShabatButtonPressed.sendEvent();

				buttons[0].setEnabled(false);
				buttons[1].setEnabled(false);
				buttons[2].setEnabled(false);
				buttons[3].setEnabled(false);
				buttons[4].setEnabled(false);
				buttons[5].setEnabled(false);
				buttons[6].setEnabled(false);
				buttons[7].setEnabled(false);
				buttons[8].setEnabled(false);
				buttons[9].setEnabled(false);
				buttons[10].setEnabled(false);
				buttons[11].setEnabled(false);

			}
			 else {
				evShabtButtonUnPressed.sendEvent();
			}
		}
		else {
			evButtonPressed.sendEvent(butt);
			buttons[0].setEnabled(false);
			buttons[1].setEnabled(false);
			buttons[2].setEnabled(false);
			buttons[3].setEnabled(false);
			buttons[4].setEnabled(false);
			buttons[5].setEnabled(false);
			buttons[6].setEnabled(false);
			buttons[7].setEnabled(false);
			buttons[8].setEnabled(false);
			buttons[9].setEnabled(false);
			buttons[10].setEnabled(false);
			buttons[11].setEnabled(false);

		}





	}

}
