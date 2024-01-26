package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.drivee.HardwareMapping;

public class Intake {

    private final HardwareMapping mapping;

    public Intake(HardwareMapping mapping)
    {
        this.mapping = mapping;
    }

    public void update(Gamepad gamepad)
    {
        // la outtake se roteste motor 2 sensuri ata slidere
        // lift 3 servouri crap

        if (gamepad.circle)
        {
            mapping.intakeMotor.setPower(1);
        }
        else mapping.intakeMotor.setPower(0);
        if (gamepad.square)
        {
            mapping.intakeMotor.setPower(-1);
        }
        else mapping.intakeMotor.setPower(0);

    }

}
