import java.awt.Color;

import javax.swing.JPanel;

/*
 * Created on Mimuna 5767  upDate on Tevet 5770 
 */

/**
 * @author לויאן
 */

class ShneyLuchot extends Thread
{
	Ramzor ramzor;
	JPanel panel;
	public ShneyLuchot( Ramzor ramzor,JPanel panel)
	{
		this.ramzor=ramzor;
		this.panel=panel;
		start();
	}

	public void run()
	{
		try 
		{
			while (true)
			{
				sleep(1000);
				setLight(1,Color.GRAY);
				setLight(2,Color.GREEN);
				sleep(1000);
				setLight(1,Color.RED);
				setLight(2,Color.GRAY);
			}
		} catch (InterruptedException e) {}

	}
	public void setLight(int place, Color color)
	{
		ramzor.colorLight[place-1]=color;
		panel.repaint();
	}
}
