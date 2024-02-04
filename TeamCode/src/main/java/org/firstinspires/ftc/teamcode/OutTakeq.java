/*package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class OutTakeq {

    public final Elevatorpep elevatorpep;
    Servo miniservo1, miniservo2, virutal1, virutal2, angle, pivot;

    enum StateOT() { //StateOT nextState?
        WAIT_FOR_ELEVATOR, // asteapta sa urce liftul
        WAIT_FOR_OUTTAKE, // power = 1 se invarte outtakeul idk
        READY_TO_PLACE_PIXELS, // pixeli
        RESETTING,
        PIXELS_READY,
        STARTING_POINT // power = -1


    }
    StateOT stateOT = StateOT.STARTING_POINT;
    public OutTakeq(HardwareMap hardwareMap, Telemetry telemetry)
    {
        miniservo1 = hardwareMap.get(Servo.class, "mini1");
        miniservo2 = hardwareMap.get(Servo.class, "mini2");
       // servoVirutal1 = hardwareMap.get(Servo.class, "servo1ot");
        //servo2 = hardwareMap.get(Servo.class, "servo2");
       // servo3 = hardwareMap.get(Servo.class, "servo3");
       // servo4 = hardwareMap.get(Servo.class, "servo4");
        elevatorpep = new Elevatorpep(hardwareMap, telemetry);


    }

    public void update(Gamepad gamepad)
    {

        switch (StateOT)
        {

        }

    }

}
*/

