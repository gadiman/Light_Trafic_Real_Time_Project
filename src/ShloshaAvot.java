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




	enum StateMode {REST_OF_WEEK,SHABAT,BLINK_ORANGE};
	enum ColorLight {RED, GREEN, ORANGE}

	StateMode stateMode;
	ColorLight color;
	Boolean  restOfWeekMode,shabt;



	private boolean stop=true;

	public ShloshaAvot( Ramzor ramzor,JPanel panel,int key, Event64 evShabat_,Event64 evRestOfWeek_,
                       Event64 evLightRed_,Event64 evStartWorking_)
	{
		this.ramzor=ramzor;
		this.panel=panel;
		this.restOfWeekMode = true;
		shabt = false;
		stateMode = StateMode.REST_OF_WEEK;
		color = ColorLight.RED;


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

                                        if(evShabat.arrivedEvent()) {
                                            evShabat.waitEvent();
                                            stateMode = StateMode.SHABAT;
                                            restOfWeekMode = false;
                                            break;
                                        }else if(evStartWorking.arrivedEvent()){
                                            evStartWorking.waitEvent();
                                            setLight(1,Color.GRAY);
                                            setLight(2,Color.ORANGE);
                                            setLight(3,Color.GRAY);


                                            color = ColorLight.ORANGE;

                                        }else
                                            yield();

                                        break;


									case ORANGE:
										new MyTimer72(1000, evTimer3);
										evTimer3.waitEvent();
										sleep(1000);
										setLight(1,Color.GRAY);
										setLight(2,Color.GRAY);
										setLight(3,Color.GREEN);
										color = ColorLight.GREEN;

										if(evShabat.arrivedEvent()) {
											evShabat.waitEvent();
											stateMode = StateMode.SHABAT;
											restOfWeekMode = false;
											break;
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

                                        //send Ack to controller I'm Red
                                        evLightRedAck.sendEvent();

										color = ColorLight.RED;

										if(evShabat.arrivedEvent()) {
											evShabat.waitEvent();
											stateMode = StateMode.SHABAT;
											restOfWeekMode = false;
											break;
										}

										break;


								}
							}

						case SHABAT:
							sleep(1000);
							setLight(1,Color.RED);
							setLight(2,Color.GRAY);
							setLight(3,Color.GRAY);
							sleep(2000);

							evLightRedAck.sendEvent();
							stateMode = StateMode.BLINK_ORANGE;

							break;

                        case BLINK_ORANGE:
                            if(evRestOfWeek.arrivedEvent())
                            {
                                evRestOfWeek.waitEvent();
                                stateMode = StateMode.REST_OF_WEEK;
                                restOfWeekMode = true;
                                color = ColorLight.RED;

                                setLight(1,Color.RED);
                                setLight(2,Color.GRAY);
                                setLight(3,Color.GRAY);

                                evLightRedAck.sendEvent();

                            }
                            else{
                                sleep(500);
                                setLight(1, Color.GRAY);
                                setLight(2, Color.ORANGE);
                                setLight(3, Color.GRAY);
                                sleep(500);

                                setLight(1, Color.GRAY);
                                setLight(2, Color.GRAY);
                                setLight(3, Color.GRAY);
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

	public boolean isStop()
	{
		return stop;
	}
}
