package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "pid4elevator")
@Config
public class pidelevator extends LinearOpMode
{
    private PIDController controller;
    public static double p = 0, i = 0, d = 0, f = 0;

    public static int targetpos = 0; // ticks
    private final double ticksid = 0; // ticks in degree
    private final double ZERO_OFFSET = 0; // measured val

    private DcMotorEx liftmotor;

    @Override
    public void runOpMode() throws InterruptedException {
        controller = new PIDController(p, i, d);
        liftmotor = hardwareMap.get(DcMotorEx.class, "liftMotor");
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive())
        {
            controller.setPID(p, i, d);
            int pos = liftmotor.getCurrentPosition();
            double pid = controller.calculate(pos, targetpos);
            double ff = Math.sin(Math.toRadians(pos / ticksid + ZERO_OFFSET)) * f;

            double power = pid + ff;

            liftmotor.setPower(power);

        }

    }
}
