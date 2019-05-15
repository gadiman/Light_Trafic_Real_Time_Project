
/**
 *
 * @author levian
 *
 */
 
public class DanTimer75 extends Thread
{
    private final long time;
 
    public DanTimer75(long time)
    {
        this.time=time;
        setDaemon(true);
        start();
    }
	

    public void run()
    {
        try 
		{
            sleep(time);
        } catch (InterruptedException ex) {}
    }

}