
/**
 *
 * @author levian
 *
 */
 
public class Timer75 extends Thread
{
    private final long time;
    private final Event64 evTime;
    private boolean cancel;

    public Timer75(long time,Event64 evTime)
    {
    	this.cancel=false;
        this.time=time;
        this.evTime=evTime;
        setDaemon(true);
        start();
    }
	
	public void cancel()
	{
		cancel=true;
	}

    public void run()
    {
        try 
		{
            sleep(time);
        } catch (InterruptedException ex) {}
        if (!cancel)
			evTime.sendEvent();
    }

}