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

	public MyActionListener() {
		this.evButtonPressed = new Event64();
		this.evShabatButtonPressed = new Event64();
		this.evShabatButtonReleased = new Event64();
	}

	public void init(Event64 evButtonPressed, Event64 evShabatButtonPressed, Event64 evShabatButtonReleased) {
		this.evButtonPressed = evButtonPressed;
		this.evShabatButtonPressed = evShabatButtonPressed;
		this.evShabatButtonReleased = evShabatButtonReleased;
	}

	public void actionPerformed(ActionEvent e) {
		JRadioButton butt = (JRadioButton)e.getSource();
		int key = Integer.parseInt(butt.getName());

		if (key == 16) {
			if (butt.isSelected())
				evShabatButtonPressed.sendEvent();
			else
				evShabatButtonReleased.sendEvent();
		}
		else
			evButtonPressed.sendEvent(key);
	}
}
