/*package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drivee.HardwareMapping;

public class Elevator {


    private pidelevator pid = new pidelevator();
    enum State
    {
        DOWN,
        UP,
        WAITFO,
        GOINGDDOWN

    }
    State state = State.DOWN;

    DcMotorEx liftMotor , liftMotorR;
    DcMotorEx sensor;
    public Elevator(HardwareMap hardwareMap)
    {
        liftMotor = hardwareMap.get(DcMotorEx.class, "liftMotor");
        liftMotorR = hardwareMap.get(DcMotorEx.class, "liftMotorR");
        liftMotorR.setDirection(DcMotorSimple.Direction.REVERSE);
        sensor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sensor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void update(Gamepad gamepad)
    {

        liftMotor.setPower(0);
        switch (state)
        {
            case DOWN:
                //
        }
    }
}
*/