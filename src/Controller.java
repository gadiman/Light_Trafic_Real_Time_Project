import javax.swing.*;

import static java.lang.Thread.sleep;

public class Controller {

    Ramzor ramzorim[];
    JRadioButton buttons[];
    TrafficLightFrame tlf;
    Event64 evShabatMode,evRestOfWeekMode;
    Event64 [] evAckRedLight;
    Event64 [] evStatWorking;
    enum StateMode {REST_OF_WEEK,SHABAT, ACK_WAITING};
    StateMode stateMode;
    boolean restOfWeekMode;
    enum Phase {START, PHASE_A, PHASE_B, PHASE_C, ACK_WAITING}
    Phase phase;
    enum  NumOfPhase{A,B,C} ;
    NumOfPhase numOfPhase;






    public Controller(Ramzor[] ramzorArray, JRadioButton[] buttonArray, TrafficLightFrame tlf_){
        ramzorim =ramzorArray;
        buttons = buttonArray;
        tlf = tlf_;
        restOfWeekMode = true;
        stateMode = StateMode.REST_OF_WEEK;
        phase = Phase.START;

        evShabatMode = new Event64();
        evRestOfWeekMode = new Event64();
        evAckRedLight = new Event64[16];
        evStatWorking = new Event64[16];

        for(int i=0; i<16; i++){
            evAckRedLight[i] = new Event64();
            evStatWorking[i] = new Event64();
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
                                    //create all the car's traffic light
                                    for(int i=0; i<4; i++) {
                                        new ShloshaAvot(ramzorim[i],tlf.myPanel,i+1,evShabatMode,evRestOfWeekMode,evAckRedLight[i],evStatWorking[i]);
                                    }

                                    //create all the walker's traffic light
                                    for(int i=4; i<=15; i++) {
                                        new ShneyLuchot(ramzorim[i],tlf.myPanel,evShabatMode,evRestOfWeekMode,evAckRedLight[i],evStatWorking[i]);
                                    }



                                    //create the Blinking light traffic
                                    new Echad(ramzorim[16],tlf.myPanel);

                                    //Waiting to Ack from All traffic lights (They are red now)
                                    for(int i=0; i<15; i++){
                                        evAckRedLight[i].waitEvent();
                                    }

                                    //Moving to Phase A
                                    phase = Phase.PHASE_A;
                                    break;

                                case PHASE_A:
                                    evStatWorking[0].sendEvent();
                                    evStatWorking[6].sendEvent();
                                    evStatWorking[7].sendEvent();
                                    evStatWorking[8].sendEvent();
                                    evStatWorking[9].sendEvent();
                                    evStatWorking[10].sendEvent();
                                    evStatWorking[11].sendEvent();
                                    evStatWorking[12].sendEvent();
                                    evStatWorking[13].sendEvent();

                                    phase = Phase.ACK_WAITING;
                                    numOfPhase = NumOfPhase.A;


                                    break;

                                case PHASE_B:
                                    evStatWorking[1].sendEvent();
                                    evStatWorking[2].sendEvent();
                                    evStatWorking[4].sendEvent();
                                    evStatWorking[5].sendEvent();
                                    evStatWorking[6].sendEvent();
                                    evStatWorking[7].sendEvent();
                                    evStatWorking[9].sendEvent();
                                    evStatWorking[10].sendEvent();
                                    evStatWorking[12].sendEvent();
                                    evStatWorking[13].sendEvent();

                                    phase = Phase.ACK_WAITING;
                                    numOfPhase = NumOfPhase.B;


                                    break;

                                case PHASE_C:

                                    evStatWorking[3].sendEvent();
                                    evStatWorking[4].sendEvent();
                                    evStatWorking[5].sendEvent();
                                    evStatWorking[8].sendEvent();
                                    evStatWorking[9].sendEvent();
                                    evStatWorking[10].sendEvent();
                                    evStatWorking[11].sendEvent();
                                    evStatWorking[14].sendEvent();
                                    evStatWorking[15].sendEvent();
                                    phase = Phase.ACK_WAITING;
                                    numOfPhase = NumOfPhase.C;
                                    break;


                                case ACK_WAITING:

                                    if(numOfPhase == NumOfPhase.A) {
                                        ackWaitingFromPhase_A();
                                    }
                                    else if(numOfPhase == NumOfPhase.B)
                                        ackWaitingFromPhase_B();
                                    else
                                        ackWaitingFromPhase_C();


                                    break;
                            }

                        }

                    case ACK_WAITING:

                        break;

                    case SHABAT:
                        sleep(1000);

                        }

                }

        } catch (InterruptedException e) {}

    }


    private void ackWaitingFromPhase_A() {
        evAckRedLight[0].waitEvent();
        evAckRedLight[6].waitEvent();
        evAckRedLight[7].waitEvent();
        evAckRedLight[8].waitEvent();
        evAckRedLight[9].waitEvent();
        evAckRedLight[10].waitEvent();
        evAckRedLight[11].waitEvent();
        evAckRedLight[12].waitEvent();
        evAckRedLight[13].waitEvent();
        phase =Phase.PHASE_B;
    }
    private void ackWaitingFromPhase_B() {
        evAckRedLight[1].waitEvent();
        evAckRedLight[2].waitEvent();
        evAckRedLight[4].waitEvent();
        evAckRedLight[5].waitEvent();
        evAckRedLight[6].waitEvent();
        evAckRedLight[7].waitEvent();
        evAckRedLight[9].waitEvent();
        evAckRedLight[10].waitEvent();
        evAckRedLight[12].waitEvent();
        evAckRedLight[13].waitEvent();
        phase =Phase.PHASE_C;

    }
    private void ackWaitingFromPhase_C() {
        evAckRedLight[3].waitEvent();
        evAckRedLight[4].waitEvent();
        evAckRedLight[5].waitEvent();
        evAckRedLight[8].waitEvent();
        evAckRedLight[9].waitEvent();
        evAckRedLight[10].waitEvent();
        evAckRedLight[11].waitEvent();
        evAckRedLight[14].waitEvent();
        evAckRedLight[15].waitEvent();
        phase =Phase.PHASE_A;


    }

}
