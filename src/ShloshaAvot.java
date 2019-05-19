import java.awt.Color;

import javax.swing.JPanel;

/*
 * Created on Mimuna 5767  upDate on Tevet 5770
 */

/**
 * @author Arie & Gad.
 */

public class ShloshaAvot extends Thread
{
	Ramzor ramzor;
	JPanel panel;
	Event64 evTimer1;
	Event64 evTimer2;
	Event64 evTimer3;
	Event64 evShabat;
	Event64 evRestOfWeek;
	Event64 evLightRedAck;
	Event64 evStartWorking;
	Boolean isStillAlive;




	enum StateMode {REST_OF_WEEK,SHABAT};
	enum ColorLight {RED, GREEN, ORANGE, START}

	StateMode stateMode;
	ColorLight color;
	boolean  restOfWeekMode;


	private boolean stop=true;

	public ShloshaAvot( Ramzor ramzor,JPanel panel,int key, Event64 evShabat_,
                        Event64 evRestOfWeek_,Event64 evLightRed_,Event64 evStartWorking_, Boolean isStillAlive_)
	{
		this.ramzor=ramzor;
		this.panel=panel;
		this.restOfWeekMode = true;
		stateMode = StateMode.REST_OF_WEEK;
		color = ColorLight.RED;

		isStillAlive = isStillAlive_;

		evTimer1 = new Event64();
		evTimer2 = new Event64();
		evTimer3 = new Event64();

		evShabat = evShabat_;
		evRestOfWeek = evRestOfWeek_;
		evLightRedAck = evLightRed_;
		evStartWorking = evStartWorking_;

		new CarsMaker(panel,this,key);
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
                                        stop = true;

                                        //send Ack to controller I'm Red
                                        evLightRedAck.sendEvent();

                                        if(isStillAlive){
											color = ColorLight.GREEN;
											break;
										}


                                        evStartWorking.waitEvent();

										setLight(1,Color.GRAY);
										setLight(2,Color.ORANGE);
										setLight(3,Color.GRAY);


										color = ColorLight.ORANGE;

										if(evShabat.arrivedEvent()){
											evShabat.waitEvent();
											System.out.println("Shabat");
											stateMode = StateMode.SHABAT;
											restOfWeekMode = false;
										}else{
											yield();
										}

										break;

									case ORANGE:
										new MyTimer72(1000, evTimer3);
										evTimer3.waitEvent();
										sleep(1000);
										setLight(1,Color.GRAY);
										setLight(2,Color.GRAY);
										setLight(3,Color.GREEN);
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
									    stop = false;
										new MyTimer72(5000, evTimer2);
										evTimer2.waitEvent();
										sleep(1000);
										setLight(1,Color.RED);
										setLight(2,Color.GRAY);
										setLight(3,Color.GRAY);

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
							setLight(3,Color.RED);

							evLightRedAck.sendEvent();

							evRestOfWeek.waitEvent();
							stateMode = StateMode.REST_OF_WEEK;
							restOfWeekMode = true;
							color = ColorLight.RED;

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

	public boolean isStop()
	{
		return stop;
	}
}
