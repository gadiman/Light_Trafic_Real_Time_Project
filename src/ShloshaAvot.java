import java.awt.Color;

import javax.swing.JPanel;

/**
 * It represent a cars' traffic light.
 * @author Arie and Gad.
 */
public class ShloshaAvot extends Thread {
    private Ramzor ramzor;
    private JPanel panel;
    private Event64 evTimer1, evTimer2, evTimer3, evShabat, evRestOfWeek, evLightRedAck, evStartWorking;

    enum StateMode {REST_OF_WEEK, SHABAT, BLINK_ORANGE}
    enum ColorLight {RED, GREEN, ORANGE}

    private StateMode stateMode;
    private ColorLight color;
    private Boolean restOfWeekMode, shabat;

    private boolean stop = true;

    public ShloshaAvot(Ramzor ramzor, JPanel panel, int key, Event64 evShabat, Event64 evRestOfWeek,
                       Event64 evLightRedAck, Event64 evStartWorking) {
        this.ramzor = ramzor;
        this.panel = panel;
        this.restOfWeekMode = true;
        this.shabat = false;
        this.stateMode = StateMode.REST_OF_WEEK;
        this.color = ColorLight.RED;

        this.evTimer1 = new Event64();
        this.evTimer2 = new Event64();
        this.evTimer3 = new Event64();

        this.evShabat = evShabat;
        this.evRestOfWeek = evRestOfWeek;
        this.evLightRedAck = evLightRedAck;
        this.evStartWorking = evStartWorking;

        new CarsMaker(panel, this, key);
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
                                    stop = true;

                                    if (evShabat.arrivedEvent()) {
                                        evShabat.waitEvent();
                                        stateMode = StateMode.SHABAT;
                                        restOfWeekMode = false;
                                        break;
                                    } else if (evStartWorking.arrivedEvent()) {
                                        evStartWorking.waitEvent();
                                        setLight(1, Color.RED);
                                        setLight(2, Color.ORANGE);
                                        setLight(3, Color.GRAY);

                                        color = ColorLight.ORANGE;
                                    } else
                                        yield();
                                    break;
                                case ORANGE:
                                    new Timer(1000, evTimer3);
                                    evTimer3.waitEvent();
                                    sleep(1000);

                                    setLight(1, Color.GRAY);
                                    setLight(2, Color.GRAY);
                                    setLight(3, Color.GREEN);
                                    color = ColorLight.GREEN;

                                    if (evShabat.arrivedEvent()) {
                                        evShabat.waitEvent();
                                        stateMode = StateMode.SHABAT;
                                        restOfWeekMode = false;
                                    }
                                    break;
                                case GREEN:
                                    stop = false;
                                    new Timer(5000, evTimer2);
                                    evTimer2.waitEvent();
                                    sleep(1000);
                                    stop = true;

                                    setLight(1, Color.RED);
                                    setLight(2, Color.GRAY);
                                    setLight(3, Color.GRAY);

                                    // Send Ack to controller that I'm Red:
                                    evLightRedAck.sendEvent();

                                    color = ColorLight.RED;

                                    if (evShabat.arrivedEvent()) {
                                        evShabat.waitEvent();
                                        stateMode = StateMode.SHABAT;
                                        restOfWeekMode = false;
                                    }
                                    break;
                            }
                        }
                    case SHABAT:
                        sleep(1000);
                        setLight(1, Color.RED);
                        setLight(2, Color.GRAY);
                        setLight(3, Color.GRAY);
                        sleep(2000);

                        evLightRedAck.sendEvent();
                        stateMode = StateMode.BLINK_ORANGE;
                        break;
                    case BLINK_ORANGE:
                        if (evRestOfWeek.arrivedEvent()) {
                            evRestOfWeek.waitEvent();
                            stateMode = StateMode.REST_OF_WEEK;
                            restOfWeekMode = true;
                            color = ColorLight.RED;

                            setLight(1, Color.RED);
                            setLight(2, Color.GRAY);
                            setLight(3, Color.GRAY);

                            evLightRedAck.sendEvent();
                        } else {
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

    public void setLight(int place, Color color) {
        ramzor.colorLight[place - 1] = color;
        panel.repaint();
    }

    public boolean isStop() {
        return stop;
    }
}
