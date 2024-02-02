package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoZero extends LinearOpMode {

    Servo servo1, servo2;
    boolean wasAPrevPressed = false, wasBPrevPressed = false, wasYPrevPressed = false, zero1 = true, zero2 = true, zero3 = true;

    public void runOpMode() throws InterruptedException {

        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        servo1.setPosition(0);
        servo2.setPosition(0);

        waitForStart();

        while (opModeIsActive()&&isStopRequested())
        {
            if (gamepad1.a&&!wasAPrevPressed)
            {
                if(zero1)
                {
                    servo1.setPosition(0.5);
                    zero1 = false;
                }
                else {
                    servo1.setPosition(0);
                    zero1 = true;
                }

            }
            wasAPrevPressed = gamepad1.a;
            if (gamepad1.b&&!wasBPrevPressed)
            {
                if(zero2) {

                    servo2.setPosition(0.5);
                    zero2 = false;
                }
                else {
                    servo2.setPosition(0);
                    zero2 = true;
                }
            }
            wasBPrevPressed = gamepad1.b;
            if (gamepad1.y&&!wasYPrevPressed)
            {
               if(zero3)
               {
                   servo1.setPosition(0.5);
                   servo2.setPosition(0.5);
                   zero3 = true;
               }
               else
               {
                   servo1.setPosition(0);
                   servo2.setPosition(0);
                   zero3 = false;
               }
            }
            wasYPrevPressed = gamepad1.y;

        }
    }
}
