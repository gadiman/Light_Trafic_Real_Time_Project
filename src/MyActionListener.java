import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;

/**
 * It represent the action listener for the traffic system.
 * @author Arie and Gad.
 */
public class MyActionListener implements ActionListener
{
	private Event64 evButtonPressed;
	private Event64 evShabatButtonPressed;
	private Event64 evShabatButtonReleased;
	private JRadioButton[] buttons;

	public MyActionListener() {
		this.evButtonPressed = new Event64();
		this.evShabatButtonPressed = new Event64();
		this.evShabatButtonReleased = new Event64();
	}

	public void init(Event64 evButtonPressed,Event64 evShabatButtonPressed,
					 Event64 evShabatButtonReleased, JRadioButton[] buttons) {
		this.evButtonPressed = evButtonPressed;
		this.evShabatButtonPressed = evShabatButtonPressed;
		this.evShabatButtonReleased = evShabatButtonReleased;
		this.buttons = buttons;
	}

	public void actionPerformed(ActionEvent e) 
	{
		JRadioButton butt=(JRadioButton)e.getSource();
		int key = Integer.parseInt(butt.getName());

		if (key == 16) {
			if (butt.isSelected()) {
				buttons[12].setEnabled(false);
				evShabatButtonPressed.sendEvent(butt);

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
				evShabatButtonReleased.sendEvent();
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
