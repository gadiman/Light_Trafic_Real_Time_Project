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

	public MyActionListener(){
		 evButtonPressed = new Event64();
		 evShabatButtonPressed = new Event64();
		 evShabtButtonUnPressed = new Event64();

	}

	public void init(Event64 evButtonPressed_,Event64 evShabatButtonPressed_,Event64 evShabtButtonUnPressed_){
		evButtonPressed = evButtonPressed_;
		evShabatButtonPressed = evShabatButtonPressed_;
		evShabtButtonUnPressed = evShabtButtonUnPressed_;

	}

	public void actionPerformed(ActionEvent e) 
	{
		JRadioButton butt=(JRadioButton)e.getSource();
		int key = Integer.parseInt(butt.getName());

		if(key == 16){
			if (butt.isSelected())
				evShabatButtonPressed.sendEvent();
			 else
				evShabtButtonUnPressed.sendEvent();
		}
		else
			evButtonPressed.sendEvent(key);





	}

}
