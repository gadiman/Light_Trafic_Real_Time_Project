import java.awt.Color;

import javax.swing.JPanel;

/*
 * Created on Mimuna 5767  upDate on Tevet 5770 
 */

/**
 * @author לויאן
 */

public class ShloshaAvot extends Thread
{
	Ramzor ramzor;
	JPanel panel;
	private boolean stop=true;
	public ShloshaAvot( Ramzor ramzor,JPanel panel,int key)
	{
		this.ramzor=ramzor;
		this.panel=panel;
		new CarsMaker(panel,this,key);
		start();
	}

	public void run()
	{
		try 
		{
			while (true)
			{
				sleep(1000);
				setLight(2,Color.YELLOW);
				sleep(1000);
				setLight(1,Color.LIGHT_GRAY);
				setLight(2,Color.LIGHT_GRAY);
				setLight(3,Color.GREEN);
				stop=false;
				sleep(3000);
				stop=true;
				setLight(1,Color.LIGHT_GRAY);
				setLight(2,Color.YELLOW);
				setLight(3,Color.LIGHT_GRAY);
				sleep(1000);
				setLight(1,Color.RED);
				setLight(2,Color.LIGHT_GRAY);
				setLight(3,Color.LIGHT_GRAY);
			}
		} catch (InterruptedException e) {}

	}
	public void setLight(int place, Color color)
	{
		ramzor.colorLight[place-1]=color;
		panel.repaint();
	}

	public boolean isStop()
	{
		return stop;
	}
}
