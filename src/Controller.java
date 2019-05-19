import javax.swing.*;

import static java.lang.Thread.sleep;

public class Controller {
    Ramzor ramzorim[];
    JRadioButton buttons[];
    Boolean[] stillAlive;
    TrafficLightFrame tlf;
    MyActionListener myActionListener;
    int numOfButton;

    Event64  evButtonPressed, evShabatButtonPressed, evShabatButtonReleased;
    Event64[] evAckRedLight, evStartWorking,evShabatMode, evRestOfWeekMode;
    enum StateMode {REST_OF_WEEK, SHABAT, ACK_WAITING};
    StateMode stateMode;
    boolean restOfWeekMode;
    enum Phase {START, PHASE_A, PHASE_B, PHASE_C, ACK_WAITING}
    Phase phase;
    enum NumOfPhase{A, B, C}
    NumOfPhase numOfPhase;

    public Controller(Ramzor[] ramzorArray, JRadioButton[] buttonArray,
                      TrafficLightFrame tlf_, MyActionListener myActionListener_){
        ramzorim = ramzorArray;
        buttons = buttonArray;
        tlf = tlf_;
        myActionListener = myActionListener_;

        restOfWeekMode = true;
        stateMode = StateMode.REST_OF_WEEK;
        phase = Phase.START;


        evButtonPressed = new Event64();
        evShabatButtonPressed = new Event64();
        evShabatButtonReleased = new Event64();

        evAckRedLight = new Event64[16];
        evStartWorking = new Event64[16];
        stillAlive = new Boolean [16];
        evShabatMode = new Event64[16];
        evRestOfWeekMode = new Event64[16];

        myActionListener_.init(evButtonPressed, evShabatButtonPressed, evShabatButtonReleased);

        for(int i = 0; i < 16; i++){
            evAckRedLight[i] = new Event64();
            evStartWorking[i] = new Event64();
            stillAlive[i] = new Boolean(false);
            evShabatMode[i] = new Event64();
            evRestOfWeekMode[i] = new Event64();
        }
    }

    public void startTraffic() {
        try
        {
            while (true)
            {
                switch (stateMode) {
                    case REST_OF_WEEK:
                        while(restOfWeekMode) {
                            switch (phase) {
                                case START:
                                    sleep(1000);

                                    // Create all the car's traffic light:
                                    for(int i = 0; i < 4; i++) {
                                        new ShloshaAvot(ramzorim[i],tlf.myPanel,i+1,evShabatMode[i],evRestOfWeekMode[i],evAckRedLight[i], evStartWorking[i],stillAlive[i]);
                                    }

                                    // Create all the walker's traffic light:
                                    for(int i = 4; i <= 15; i++) {
                                        new ShneyLuchot(ramzorim[i],tlf.myPanel,evShabatMode[i],evRestOfWeekMode[i],evAckRedLight[i], evStartWorking[i],stillAlive[i]);
                                    }

                                    // Create the Blinking light traffic:
                                    new Echad(ramzorim[16],tlf.myPanel);

                                    // Waiting to Ack from All traffic lights (They are red now):
                                    for( int i = 0; i < 15; i++) {
                                        evAckRedLight[i].waitEvent();
                                    }

                                    // Moving to Phase A:
                                    phase = Phase.PHASE_A;
                                    break;
                                case PHASE_A:
                                    if (evShabatButtonPressed.arrivedEvent()) {
                                        evShabatButtonPressed.waitEvent();
                                        restOfWeekMode = false;
                                        stateMode = StateMode.ACK_WAITING;
                                        break;
                                    }
                                    else if (evButtonPressed.arrivedEvent()){
                                        numOfButton = (int)evButtonPressed.waitEvent();
                                        buttonPressed(numOfButton);
                                        break;
                                    }

                                    evStartWorking[0].sendEvent();
                                    evStartWorking[6].sendEvent();
                                    evStartWorking[7].sendEvent();
                                    evStartWorking[9].sendEvent();
                                    evStartWorking[10].sendEvent();
                                    evStartWorking[12].sendEvent();
                                    evStartWorking[13].sendEvent();

                                    stillAlive[6] = true;
                                    stillAlive[7] = true;
                                    stillAlive[9] = true;
                                    stillAlive[10] = true;
                                    stillAlive[12] = true;
                                    stillAlive[13] = true;

                                    phase = Phase.ACK_WAITING;
                                    numOfPhase = NumOfPhase.A;
                                    break;
                                case PHASE_B:
                                    if (evShabatButtonPressed.arrivedEvent()) {
                                        evShabatButtonPressed.waitEvent();
                                        restOfWeekMode = false;
                                        stateMode = StateMode.ACK_WAITING;
                                        break;
                                    }
                                    else if(evButtonPressed.arrivedEvent()) {
                                        numOfButton = (int)evButtonPressed.waitEvent();
                                        buttonPressed(numOfButton);
                                        break;
                                    }

                                    evStartWorking[1].sendEvent();
                                    evStartWorking[4].sendEvent();
                                    evStartWorking[5].sendEvent();
                                    evStartWorking[6].sendEvent();
                                    evStartWorking[7].sendEvent();
                                    evStartWorking[9].sendEvent();
                                    evStartWorking[10].sendEvent();
                                    evStartWorking[12].sendEvent();
                                    evStartWorking[13].sendEvent();

                                    stillAlive[6] = false;
                                    stillAlive[7] = false;
                                    stillAlive[9] = false;
                                    stillAlive[10] = false;
                                    stillAlive[12] = false;
                                    stillAlive[13] = false;

                                    stillAlive[4] = true;
                                    stillAlive[5] = true;

                                    phase = Phase.ACK_WAITING;
                                    numOfPhase = NumOfPhase.B;
                                    break;
                                case PHASE_C:
                                    if (evShabatButtonPressed.arrivedEvent()) {
                                        evShabatButtonPressed.waitEvent();
                                        restOfWeekMode = false;
                                        stateMode = StateMode.ACK_WAITING;
                                        break;
                                    }
                                    else if (evButtonPressed.arrivedEvent()) {
                                        numOfButton = (int)evButtonPressed.waitEvent();
                                        buttonPressed(numOfButton);
                                        break;
                                    }

                                    evStartWorking[2].sendEvent();
                                    evStartWorking[3].sendEvent();
                                    evStartWorking[4].sendEvent();
                                    evStartWorking[5].sendEvent();
                                    evStartWorking[8].sendEvent();
                                    evStartWorking[11].sendEvent();
                                    evStartWorking[14].sendEvent();
                                    evStartWorking[15].sendEvent();

                                    stillAlive[4] = false;
                                    stillAlive[5]=false;

                                    phase = Phase.ACK_WAITING;
                                    numOfPhase = NumOfPhase.C;
                                    break;
                                case ACK_WAITING:
                                    if (numOfPhase == NumOfPhase.A)
                                        ackWaitingFromPhase_A();
                                    else if (numOfPhase == NumOfPhase.B)
                                        ackWaitingFromPhase_B();
                                    else if (numOfPhase == NumOfPhase.C)
                                        ackWaitingFromPhase_C();
                                    break;
                            }
                        }
                    case ACK_WAITING:
                        for(int i=0; i<16;i++)
                            evShabatMode[i].sendEvent();
                        System.out.println("E mode.");

                       // for(int i=0; i<16; i++)
                          // evAckRedLight[i].waitEvent();

                        stateMode =StateMode.SHABAT;
                        break;

                    case SHABAT:
                        System.out.println("Entry to Shabat mode.");
                        evShabatButtonReleased.waitEvent();

                        for(int i=0;i<16;i++)
                            evRestOfWeekMode[i].sendEvent();

                        restOfWeekMode = true;
                        stateMode = StateMode.REST_OF_WEEK;
                        phase = Phase.PHASE_A;

                        for(int i = 0; i < 16; i++)
                            stillAlive[i] = false;
                        break;


                }
            }
        } catch (InterruptedException e) {}
    }

    private void ackWaitingFromPhase_A() {
        evAckRedLight[0].waitEvent();
        evAckRedLight[6].waitEvent();
        evAckRedLight[7].waitEvent();
        evAckRedLight[9].waitEvent();
        evAckRedLight[10].waitEvent();
        evAckRedLight[12].waitEvent();
        evAckRedLight[13].waitEvent();

        phase =Phase.PHASE_B;
    }

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

        phase = Phase.PHASE_C;
    }
    private void ackWaitingFromPhase_C() {
        evAckRedLight[2].waitEvent();
        evAckRedLight[3].waitEvent();
        evAckRedLight[4].waitEvent();
        evAckRedLight[5].waitEvent();
        evAckRedLight[8].waitEvent();
        evAckRedLight[11].waitEvent();
        evAckRedLight[14].waitEvent();
        evAckRedLight[15].waitEvent();
        phase =Phase.PHASE_A;
    }

    private void buttonPressed(int numOfButton) {
        if(numOfButton == 16){

        }
    }
}
