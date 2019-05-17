import java.awt.Color;

import javax.swing.JPanel;

/*
 * Created on Mimuna 5767  upDate on Tevet 5770 
 */

/**
 * @author לויאן
 */
class Echad extends Thread
{
	Ramzor ramzor;
	JPanel panel;
	String color;

	public Echad( Ramzor ramzor,JPanel panel)
	{
		this.ramzor=ramzor;
		this.panel=panel;
		this.color="Orange";
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
						setLight(1, Color.yellow);
						color="Gray";
						break;

					case "Gray" :
						sleep(500);
						setLight(1, Color.gray);
						color="Orange";
						break;
				}
			}
		} catch (InterruptedException e) {}

	}
	public void setLight(int place, Color color)
	{
		ramzor.colorLight[place-1]=color;
		panel.repaint();
	}
}
