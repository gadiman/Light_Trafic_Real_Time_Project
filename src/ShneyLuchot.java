import javax.swing.JPanel;
import java.awt.*;

/**
 * It represent a walkers' traffic light.
 * @author Arie and Gad.
 */
class ShneyLuchot extends Thread {
    private Ramzor ramzor;
    private JPanel panel;
    private Event64 evTimer1, evTimer2, evShabat, evRestOfWeek, evLightRedAck, evStartWorking;

    enum StateMode {REST_OF_WEEK, SHABAT}

    enum ColorLight {RED, GREEN}

    private StateMode stateMode;
    private ColorLight color;

    private boolean restOfWeekMode;

    public ShneyLuchot(Ramzor ramzor, JPanel panel, Event64 evShabat,
                       Event64 evRestOfWeek, Event64 evLightRedAck, Event64 evStartWorking) {
        this.ramzor = ramzor;
        this.panel = panel;
        this.restOfWeekMode = true;
        this.stateMode = StateMode.REST_OF_WEEK;
        this.color = ColorLight.RED;

        this.evTimer1 = new Event64();
        this.evTimer2 = new Event64();

        this.evShabat = evShabat;
        this.evRestOfWeek = evRestOfWeek;
        this.evLightRedAck = evLightRedAck;
        this.evStartWorking = evStartWorking;

        start();
    }

    public void run() {
        try {
            while (true) {
                switch (stateMode) {
                    case REST_OF_WEEK:
                        while (restOfWeekMode) {
                            switch (color) {
                                case RED:
                                    if (evShabat.arrivedEvent()) {
                                        evShabat.waitEvent();
                                        stateMode = StateMode.SHABAT;
                                        restOfWeekMode = false;
                                    } else if (evStartWorking.arrivedEvent()) {
                                        evStartWorking.waitEvent();
                                        setLight(1, Color.GRAY);
                                        setLight(2, Color.GREEN);
                                        color = ColorLight.GREEN;
                                    } else
                                        yield();
                                    break;
                                case GREEN:
                                    if (evShabat.arrivedEvent()) {
                                        evShabat.waitEvent();
                                        stateMode = StateMode.SHABAT;
                                        restOfWeekMode = false;
                                    }

                                    new Timer(8000, evTimer2);
                                    evTimer2.waitEvent();
                                    sleep(1000);
                                    setLight(1, Color.RED);
                                    setLight(2, Color.GRAY);

                                    evLightRedAck.sendEvent();

                                    color = ColorLight.RED;
                                    break;
                            }
                        }
                    case SHABAT:
                        sleep(1000);
                        setLight(1, Color.RED);
                        setLight(2, Color.GRAY);
                        sleep(2000);

                        evLightRedAck.sendEvent();

                        sleep(1000);
                        setLight(1, Color.GRAY);
                        setLight(2, Color.GRAY);

                        evRestOfWeek.waitEvent();

                        stateMode = StateMode.REST_OF_WEEK;
                        restOfWeekMode = true;
                        color = ColorLight.RED;

                        setLight(1, Color.RED);
                        setLight(2, Color.GRAY);
                        evLightRedAck.sendEvent();
                        break;
                }
            }
        } catch (InterruptedException e) {}
    }

    public void setLight(int place, Color color) {
        ramzor.colorLight[place - 1] = color;
        panel.repaint();
    }
}
