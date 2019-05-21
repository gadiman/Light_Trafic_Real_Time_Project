import javax.swing.*;

import static java.lang.Thread.sleep;

public class Controller {

    //--------------Build Traffic staff-----------//
    private Ramzor ramzorim[];
    private JRadioButton[] buttons;
    private TrafficLightFrame trafficLightFrame;
    private MyActionListener myActionListener;
    //-------------------------------------------//

    private Event64 evButtonPressed, evShabatButtonPressed, evShabatButtonReleased; // Those for buttons.
    private Event64[] evAckRedLight, evStartWorking, evShabatMode, evRestOfWeekMode; // Those for Light Traffics.

    enum StateMode {REST_OF_WEEK, SHABAT, ACK_WAITING}
    private StateMode stateMode; // For Other switch.

    enum Phase {START, PHASE_A, PHASE_B, PHASE_C, ACK_WAITING}
    private Phase phase; // For Inner switch.

    enum NumOfPhase {A, B, C}
    private NumOfPhase numOfPhase; // For function that waiting for red light Ack.

    private boolean restOfWeekMode; // For While Loop - Inner switch.
    private JRadioButton button; // For Clear button selected.

    public Controller(Ramzor[] ramzorArray, JRadioButton[] buttonArray,
                      TrafficLightFrame trafficLightFrame, MyActionListener myActionListener) {
        this.ramzorim = ramzorArray;
        this.buttons = buttonArray;
        this.trafficLightFrame = trafficLightFrame;
        this.myActionListener = myActionListener;

        this.restOfWeekMode = true;
        this.stateMode = StateMode.REST_OF_WEEK;
        this.phase = Phase.START;

        this.evShabatMode = new Event64[16];
        this.evRestOfWeekMode = new Event64[16];
        this.evButtonPressed = new Event64();
        this.evShabatButtonPressed = new Event64();
        this.evShabatButtonReleased = new Event64();

        this.evAckRedLight = new Event64[16];
        this.evStartWorking = new Event64[16];

        this.myActionListener.init(evButtonPressed, evShabatButtonPressed, evShabatButtonReleased, buttons);

        for (int i = 0; i < 16; i++) {
            this.evAckRedLight[i] = new Event64();
            this.evStartWorking[i] = new Event64();
            this.evShabatMode[i] = new Event64();
            this.evRestOfWeekMode[i] = new Event64();
        }
    }

    public void startTraffic() {
        try {
            while (true) {
                switch (stateMode) {
                    case REST_OF_WEEK:
                        while (restOfWeekMode) {
                            switch (phase) {
                                case START:
                                    sleep(1000);

                                    // Create all the car's traffic light:
                                    for (int i = 0; i < 4; i++) {
                                        new ShloshaAvot(ramzorim[i], trafficLightFrame.myPanel, i + 1, evShabatMode[i], evRestOfWeekMode[i], evAckRedLight[i], evStartWorking[i]);
                                    }

                                    // Create all the walker's traffic light:
                                    for (int i = 4; i <= 15; i++) {
                                        new ShneyLuchot(ramzorim[i], trafficLightFrame.myPanel, evShabatMode[i], evRestOfWeekMode[i], evAckRedLight[i], evStartWorking[i]);
                                    }

                                    // Create the Blinking light traffic:
                                    new Echad(ramzorim[16], trafficLightFrame.myPanel);

                                    // Moving to Phase A:
                                    phase = Phase.PHASE_A;
                                    break;
                                case PHASE_A:
                                    if (CheckButtons())
                                        break;

                                    // Sending events for group A - Start working:
                                    evStartWorking[0].sendEvent(false);
                                    evStartWorking[6].sendEvent(false);
                                    evStartWorking[7].sendEvent(false);
                                    evStartWorking[9].sendEvent(false);
                                    evStartWorking[10].sendEvent(false);
                                    evStartWorking[12].sendEvent(false);
                                    evStartWorking[13].sendEvent(false);

                                    // Enable just the relevant buttons:
                                    buttons[0].setEnabled(true);
                                    buttons[1].setEnabled(true);
                                    buttons[2].setEnabled(false);
                                    buttons[3].setEnabled(false);
                                    buttons[4].setEnabled(true);
                                    buttons[5].setEnabled(false);
                                    buttons[6].setEnabled(false);
                                    buttons[7].setEnabled(true);
                                    buttons[8].setEnabled(false);
                                    buttons[9].setEnabled(false);
                                    buttons[10].setEnabled(true);
                                    buttons[11].setEnabled(true);

                                    // Moving to Ack_Waiting state:
                                    phase = Phase.ACK_WAITING;
                                    numOfPhase = NumOfPhase.A;
                                    break;
                                case PHASE_B:
                                    if (CheckButtons())
                                        break;

                                    // Sending events for group B - Start working:
                                    evStartWorking[1].sendEvent(false);
                                    evStartWorking[4].sendEvent(false);
                                    evStartWorking[5].sendEvent(false);
                                    evStartWorking[6].sendEvent(false);
                                    evStartWorking[7].sendEvent(false);
                                    evStartWorking[9].sendEvent(false);
                                    evStartWorking[10].sendEvent(false);
                                    evStartWorking[12].sendEvent(false);
                                    evStartWorking[13].sendEvent(false);

                                    // Enable jest the relevant buttons
                                    buttons[0].setEnabled(false); // 4
                                    buttons[1].setEnabled(false); // 5
                                    buttons[2].setEnabled(false); // 6
                                    buttons[3].setEnabled(false); // 7
                                    buttons[4].setEnabled(true); // 8
                                    buttons[5].setEnabled(false); // 9
                                    buttons[6].setEnabled(false); // 10
                                    buttons[7].setEnabled(true); // 11
                                    buttons[8].setEnabled(false); // 12
                                    buttons[9].setEnabled(false); // 13
                                    buttons[10].setEnabled(true); // 14
                                    buttons[11].setEnabled(true); // 15

                                    // Moving to Ack_Waiting state:
                                    phase = Phase.ACK_WAITING;
                                    numOfPhase = NumOfPhase.B;
                                    break;
                                case PHASE_C:
                                    if (CheckButtons())
                                        break;

                                    // Sending events for group C - Start working:
                                    evStartWorking[2].sendEvent(false);
                                    evStartWorking[3].sendEvent(false);
                                    evStartWorking[4].sendEvent(false);
                                    evStartWorking[5].sendEvent(false);
                                    evStartWorking[8].sendEvent(false);
                                    evStartWorking[11].sendEvent(false);
                                    sleep(1500);
                                    evStartWorking[14].sendEvent(false);
                                    evStartWorking[15].sendEvent(false);

                                    // Enable jest the relevant buttons:
                                    buttons[0].setEnabled(false);
                                    buttons[1].setEnabled(false);
                                    buttons[2].setEnabled(true);
                                    buttons[3].setEnabled(true);
                                    buttons[4].setEnabled(false);
                                    buttons[5].setEnabled(true);
                                    buttons[6].setEnabled(true);
                                    buttons[7].setEnabled(false);
                                    buttons[8].setEnabled(true);
                                    buttons[9].setEnabled(true);
                                    buttons[10].setEnabled(false);
                                    buttons[11].setEnabled(false);

                                    // Moving to Ack_Waiting state:
                                    phase = Phase.ACK_WAITING;
                                    numOfPhase = NumOfPhase.C;
                                    break;
                                case ACK_WAITING:
                                    if (numOfPhase == NumOfPhase.A)
                                        ackWaitingFromPhase_A(); // Waiting for red light Ack.
                                    else if (numOfPhase == NumOfPhase.B)
                                        ackWaitingFromPhase_B(); // Waiting for red light Ack.
                                    else if (numOfPhase == NumOfPhase.C)
                                        ackWaitingFromPhase_C();// Waiting for red light Ack.
                                    break;
                            }
                        }
                    case ACK_WAITING:
                        // Unable all the buttons:
                        buttons[0].setEnabled(false);
                        buttons[1].setEnabled(false);
                        buttons[2].setEnabled(false);
                        buttons[3].setEnabled(false);
                        buttons[4].setEnabled(false);
                        buttons[5].setEnabled(false);
                        buttons[6].setEnabled(false);
                        buttons[7].setEnabled(false);
                        buttons[8].setEnabled(false);
                        buttons[9].setEnabled(false);
                        buttons[10].setEnabled(false);
                        buttons[11].setEnabled(false);

                        // Sending event to all - get in to Shabat state:
                        for (int i = 0; i < 16; i++)
                            evShabatMode[i].sendEvent();

                        // Wait until the all the light traffic will send Ack for red light:
                        for (int i = 0; i < 16; i++)
                            evAckRedLight[i].waitEvent();

                        buttons[12].setEnabled(true);

                        // Move to Shabat state:
                        stateMode = StateMode.SHABAT;
                        break;
                    case SHABAT:
                        //wait until the Shabat mode button will pushed again
                        evShabatButtonReleased.waitEvent();

                        //send event for all the light traffic- get out of shabat state
                        for (int i = 0; i < 16; i++)
                            evRestOfWeekMode[i].sendEvent();

                        //wait until the all the light traffic will send Ack for red light
                        for (int i = 0; i < 16; i++)
                            evAckRedLight[i].waitEvent();
                        sleep(2000);

                        // Back to inner switch:
                        restOfWeekMode = true;
                        stateMode = StateMode.REST_OF_WEEK;
                        phase = Phase.PHASE_A;

                        break;
                }
            }
        } catch (InterruptedException e) {}
    }

    /**
     * It checks if shabat button or other buttons are pressed and handle if needed.
     * @return true if some button has pressed, false otherwise
     */
    private boolean CheckButtons() {
        if (evShabatButtonPressed.arrivedEvent()) {
            evShabatButtonPressed.waitEvent();
            // For a case that evShabat came but we have a selected button either:
            if (evButtonPressed.arrivedEvent()) {
                button = (JRadioButton)evButtonPressed.waitEvent();
                button.setSelected(false);
            }
            restOfWeekMode = false;
            stateMode = StateMode.ACK_WAITING;
            return true;
        } else if (evButtonPressed.arrivedEvent()) {
            button = (JRadioButton)evButtonPressed.waitEvent();
            buttonPressed(button);
            return true;
        }
        return false;
    }

    /**
     * This function waiting to all of the A's light traffic send Ack for Red light.
     */
    private void ackWaitingFromPhase_A() {
        evAckRedLight[0].waitEvent();
        evAckRedLight[6].waitEvent();
        evAckRedLight[7].waitEvent();
        evAckRedLight[9].waitEvent();
        evAckRedLight[10].waitEvent();
        evAckRedLight[12].waitEvent();
        evAckRedLight[13].waitEvent();
        System.out.println("A group");

        phase = Phase.PHASE_B;
    }

    /**
     * This function waiting to all of the B's light traffic send Ack for Red light.
     */
    private void ackWaitingFromPhase_B() {
        evAckRedLight[1].waitEvent();
        evAckRedLight[4].waitEvent();
        evAckRedLight[5].waitEvent();
        evAckRedLight[6].waitEvent();
        evAckRedLight[7].waitEvent();
        evAckRedLight[9].waitEvent();
        evAckRedLight[10].waitEvent();
        evAckRedLight[12].waitEvent();
        evAckRedLight[13].waitEvent();
        System.out.println("B group");

        phase = Phase.PHASE_C;
    }

    /**
     * This function waiting to all of the C's light traffic send Ack for Red light.
     */
    private void ackWaitingFromPhase_C() {
        evAckRedLight[2].waitEvent();
        evAckRedLight[3].waitEvent();
        evAckRedLight[4].waitEvent();
        evAckRedLight[5].waitEvent();
        evAckRedLight[8].waitEvent();
        evAckRedLight[11].waitEvent();
        evAckRedLight[14].waitEvent();
        evAckRedLight[15].waitEvent();
        System.out.println("C group");

        phase = Phase.PHASE_A;
    }

    /**
     * This function switch between Phases if one of the button has selected.
     */
    private void buttonPressed(JRadioButton butt) {
        int numOfButton = Integer.parseInt(butt.getName());

        if (numOfButton == 4 || numOfButton == 5 || numOfButton == 6 || numOfButton == 7 || numOfButton == 9 ||
                numOfButton == 10 || numOfButton == 12 || numOfButton == 13) {
            butt.setSelected(false); // Just fall.
        } else if (numOfButton == 8 || numOfButton == 11 || numOfButton == 14 || numOfButton == 15) {
            phase = Phase.PHASE_C;
            butt.setSelected(false);
        }
    }
}
