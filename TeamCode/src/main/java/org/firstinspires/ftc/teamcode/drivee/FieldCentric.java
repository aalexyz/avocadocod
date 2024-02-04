package org.firstinspires.ftc.teamcode.drivee;


import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class FieldCentric {

    private final HardwareMapping mapping;
    IMU imu;
    double botHeading;
    public FieldCentric(HardwareMapping hardwareMap)
    {
        this.mapping = hardwareMap;

        IMU.Parameters parameters = new IMU.Parameters
                (new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.DOWN)
        );
        mapping.imu.initialize(parameters);

// le schimb dupa daca treb wtv o iau razna in muiesil asylum



    }
    public void update (Gamepad gamepad)
    {

        double x = gamepad.left_stick_x * 1.1;
        double y = -gamepad.left_stick_y;
        double r = gamepad.right_trigger-gamepad.left_trigger;



        botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;
        // pana aici gm0.......................... trb sa adaug butonul a pt accelerare cred?????
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(r), 1);
        double frontleftpower = (rotY + rotX + r) / denominator;
        double backleftpower = (rotY - rotX + r) / denominator;
        double frontrightpower = (rotY - rotX - r) / denominator;
        double backrightpower = (rotY + rotX - r) / denominator;
        mapping.frontRightMotor.setPower(frontrightpower);
        mapping.frontLeftMotor.setPower(frontleftpower);
        mapping.backRightMotor.setPower(backrightpower);
        mapping.backLeftMotor.setPower(backleftpower);



    }
}
