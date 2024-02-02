package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class OutTakeq {

    Servo miniservo1, miniservo2, servo1, servo2, servo3, servo4;

    enum State {
        STAGE_ONE, // asteapta sa urce liftul
        STAGE_TWO, // power = 1 se invarte outtakeul idk
        STAGE_THREE // pixeli


    }
    public OutTakeq(HardwareMap hardwareMap)
    {
        miniservo1 = hardwareMap.get(Servo.class, "mini1");
        miniservo2 = hardwareMap.get(Servo.class, "mini2");
        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        servo3 = hardwareMap.get(Servo.class, "servo3");
        servo4 = hardwareMap.get(Servo.class, "servo4");

    }

    public void update(Gamepad gamepad)
    {



    }

}


