package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class MotorRPMTester extends LinearOpMode {
    DcMotorEx l1,l2;
    boolean wasAPrev = false,pow1 = false;
    int ticks1 = 0 , ticks2 = 0;
    ElapsedTime timer;
    public void runOpMode() throws InterruptedException {
        l1 = hardwareMap.get(DcMotorEx.class,"motor1");
        l2 = hardwareMap.get(DcMotorEx.class, "motor2");
        l1.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        l2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        l1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        l2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        l1.setTargetPosition(0);
        l2.setTargetPosition(0);
        l1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        l2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        l1.setPower(0);
        l2.setPower(0);
        l1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        l2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        timer.reset();
        while(opModeIsActive())
        {
            if(gamepad1.a && !wasAPrev)
            {
                if(!pow1) {
                    pow1 = true;
                    l1.setPower(1);
                    l2.setPower(1);
                    timer.reset();
                }
                else {
                    pow1 = false;
                    l1.setPower(0);
                    l2.setPower(0);
                }
            }
            wasAPrev = gamepad1.a;
            ticks1 = l1.getCurrentPosition();
            ticks2 = l2.getCurrentPosition();

            telemetry.addData("l1 ticks",ticks1 );
            telemetry.addData("l2 ticks",ticks2 );

            telemetry.addData("RPM 1", ticks1/timer.seconds()*60);
            telemetry.addData("RPM 2", ticks2/timer.seconds()*60);

            telemetry.update();
        }
    }
}
