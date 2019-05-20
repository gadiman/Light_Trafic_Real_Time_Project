import javax.swing.JFrame;

/**
 * It represent a traffic light frame.
 * @author Arie and Gad.
 */
public class TrafficLightFrame extends JFrame 
{
	private final int WIDTH = 800, HEIGHT = 750;
	TrafficLightPanel myPanel;

	public TrafficLightFrame(String h, Ramzor[] ramzorim) {
		super(h);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(90, -15);
		this.myPanel = new TrafficLightPanel(ramzorim);
		add(myPanel);
		pack();
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setVisible(true);
	}
}
