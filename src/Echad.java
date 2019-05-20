import java.awt.Color;

import javax.swing.JPanel;

/**
 * It represent a traffic light with one orange blinked light.
 * @author Arie and Gad.
 */
class Echad extends Thread
{
	private Ramzor ramzor;
	private JPanel panel;
	private String color;

	public Echad(Ramzor ramzor, JPanel panel)
	{
		this.ramzor = ramzor;
		this.panel = panel;
		this.color = "Orange";
		start();
	}

	public void run()
	{
		try 
		{
			while (true)
			{
				switch (color) {
					case "Orange" :
						sleep(500);
						setLight(1, Color.orange);
						color = "Gray";
						break;
					case "Gray" :
						sleep(500);
						setLight(1, Color.gray);
						color = "Orange";
						break;
				}
			}
		} catch (InterruptedException e) {}
	}

	private void setLight(int place, Color color)
	{
		ramzor.colorLight[place - 1] = color;
		panel.repaint();
	}
}
