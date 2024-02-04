package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class IntakeTest extends LinearOpMode {
    DcMotor motorIntake;
    boolean wasAPrev = false,pow1 = false;
    public void runOpMode() throws InterruptedException {

        motorIntake = hardwareMap.get(DcMotor.class, "motorIntake");
        motorIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorIntake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorIntake.setTargetPosition(0);
        motorIntake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorIntake.setPower(0);
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive())
        {
            if(gamepad1.a && !wasAPrev)
            {
                if(!pow1) {
                    pow1 = true;
                    motorIntake.setPower(1);
                }
                else {
                    pow1 = false;
                    motorIntake.setPower(0);
                }
            }
            wasAPrev = gamepad1.a;
        }
    }
}
