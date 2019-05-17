import javax.swing.JPanel;
import java.awt.*;

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
	Event64 evTimer1;
	Event64 evTimer2;
	Event64 evShabat;
	Event64 evRestOfWeek;
	Event64 evLightRedAck;
	Event64 evStartWorking;



	MyTimer72 timer ;
	enum StateMode {REST_OF_WEEK,SHABAT};
	enum ColorLight {RED, GREEN, START}

	StateMode stateMode;
	ColorLight color;


	boolean restOfWeekMode;

	public ShneyLuchot( Ramzor ramzor,JPanel panel,Event64 evShabat_,
						Event64 evRestOfWeek_,Event64 evRedLigtAck_,Event64 evStartWorking_)
	{
		this.ramzor=ramzor;
		this.panel=panel;
		stateMode = StateMode.REST_OF_WEEK;
		color = ColorLight.RED;
		this.restOfWeekMode = true;
		evTimer1 = new Event64();
		evTimer2 = new Event64();
		evShabat = evShabat_;
		evLightRedAck = evRedLigtAck_;
		evRestOfWeek = evRestOfWeek_;
		evStartWorking = evStartWorking_;
		start();
	}



	public void run()
	{
		try
		{
			while (true)
			{
				switch (stateMode) {

					case REST_OF_WEEK:

							while(restOfWeekMode) {

								switch (color) {


									case RED:
										evLightRedAck.sendEvent();
										evStartWorking.waitEvent();

										setLight(1,Color.GRAY);
										setLight(2,Color.GREEN);
										color = ColorLight.GREEN;

										if(evShabat.arrivedEvent()){
											evShabat.waitEvent();
											stateMode = StateMode.SHABAT;
											restOfWeekMode = false;
										}else{
											yield();
										}

										break;

									case GREEN:
										new MyTimer72(8000, evTimer2);
										evTimer2.waitEvent();
										sleep(1000);
										setLight(1,Color.RED);
										setLight(2,Color.GRAY);
										color = ColorLight.RED;

										if(evShabat.arrivedEvent()){
											evShabat.waitEvent();
											stateMode = StateMode.SHABAT;
											restOfWeekMode = false;
										}else{
											yield();
										}

										break;
								}
					        }
					case SHABAT:
						sleep(1000);
						setLight(1,Color.GRAY);
						setLight(2,Color.GRAY);

						if(evRestOfWeek.arrivedEvent()){
							evRestOfWeek.waitEvent();
							stateMode = StateMode.REST_OF_WEEK;
							restOfWeekMode = true;
							color = ColorLight.START;
						}

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
