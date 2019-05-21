import javax.swing.JPanel;

/**
 * It represent a cars maker.
 * @author Arie and Gad.
 */
public class CarsMaker extends Thread
{
	private JPanel myPanel;
	private ShloshaAvot myRamzor;
	private int key;

	public CarsMaker(JPanel myPanel, ShloshaAvot myRamzor, int key) {
		this.myPanel = myPanel;
		this.myRamzor = myRamzor;
		this.key = key;
		setDaemon(true);
		start();
	}

	public void run() {
		try {
			while (true) {
				sleep(300);

				if (!myRamzor.isStop())
					new CarMoving(myPanel ,myRamzor ,key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
