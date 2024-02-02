package org.firstinspires.ftc.teamcode.drivee;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Elevatorpep;
import org.firstinspires.ftc.teamcode.Intake;
import org.firstinspires.ftc.teamcode.OutTakeq;
import org.firstinspires.ftc.teamcode.Elevator;

import java.util.List;

@TeleOp(name="Main")
public class Main extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        final HardwareMapping mapping = HardwareMapping.from(hardwareMap);
        DriveTrain dt = new DriveTrain(mapping);
        FieldCentric dtfc = new FieldCentric(mapping);
        Intake intake = new Intake(mapping);
        OutTakeq outtake = new OutTakeq(hardwareMap);
        Elevatorpep elevator = new Elevatorpep(hardwareMap, telemetry);
        boolean ok = true;
        Gamepad gmcur = gamepad1, gmprev;

        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive())
        {
            gmprev = gmcur; // gmcur - gamepadcurrent gmprev - gamepadprevious
            gmcur = gamepad1;

            if(gmcur.share && !gmprev.share)
                ok=!ok;

            if(ok)
                dt.update(gamepad1);
            else
                dtfc.update(gamepad1);

            dt.update(gamepad1);
            dtfc.update(gamepad1);
            intake.update(gamepad2);
            outtake.update(gamepad2);
            // elevator.update(gamepad2);
            telemetry.update();
            for (LynxModule hub : allHubs) {
                hub.clearBulkCache();
            }
            // gamepad1 - drivetrain
            // gamepad2 - intake, outtake, restul
            // share - schimba de la robot centric la field centric
            // a / x - acceleraza sper.......... gm1 (hold)
            // circle / square - intake gm2 (hold)
        }
    }
}
