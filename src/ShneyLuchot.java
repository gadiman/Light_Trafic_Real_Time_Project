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
	enum ColorLight {RED, GREEN}

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


                                        if(evShabat.arrivedEvent()){
											evShabat.waitEvent();
											stateMode = StateMode.SHABAT;
											restOfWeekMode = false;
										}else if(evStartWorking.arrivedEvent()){
                                            evStartWorking.waitEvent();
                                            setLight(1,Color.GRAY);
                                            setLight(2,Color.GREEN);
                                            color = ColorLight.GREEN;
                                        }
                                        else
                                            yield();

										break;

									case GREEN:
										if(evShabat.arrivedEvent()){
											evShabat.waitEvent();
											stateMode = StateMode.SHABAT;
											restOfWeekMode = false;
										}

										new MyTimer72(8000, evTimer2);
										evTimer2.waitEvent();
										sleep(1000);
										setLight(1,Color.RED);
										setLight(2,Color.GRAY);

                                        evLightRedAck.sendEvent();



                                        color = ColorLight.RED;

										break;
								}
					        }
					case SHABAT:
						sleep(1000);
						setLight(1,Color.RED);
						setLight(2,Color.GRAY);
						sleep(2000);

						evLightRedAck.sendEvent();

                        sleep(1000);
                        setLight(1,Color.GRAY);
                        setLight(2,Color.GRAY);


						evRestOfWeek.waitEvent();


						stateMode = StateMode.REST_OF_WEEK;
						restOfWeekMode = true;
						color = ColorLight.RED;

                        setLight(1,Color.RED);
                        setLight(2,Color.GRAY);
                        evLightRedAck.sendEvent();


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
