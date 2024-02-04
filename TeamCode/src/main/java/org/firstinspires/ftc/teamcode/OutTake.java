package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.BratOutTake;
import org.firstinspires.ftc.teamcode.drivee.Controls;
import org.firstinspires.ftc.teamcode.Grippers;

@Config
public class OutTake {
    public enum STATES_OOUTTAKE {
        RESET_ELEVATOR,
        SET_FEED_FORWARD,
        SHOUD_RETRACT,
        WAIT_FOR_ARM_REST,
        WAIT_FOR_ELEVATOR_REST,
        READY_FOR_PIXELS,
        FEED;

        public static int pos = 0;
        public static int saved_level = 7;
    }
    public STATES_OOUTTAKE STATE;
    public final Elevatorpep elevator;
    public final BratOutTake arm;
    public final Grippers grippers;
    private final Telemetry telemetry;
    private final MultiTickUpdater feedUpdater;

    public int RetractLevel = 5, FeedForwardPos = 4, stallPixels = 2;

    private final Controls controls;
    public OutTake(HardwareMap hm, Controls c, Telemetry tele){
        elevator = new Elevatorpep(hm, tele);
        arm = new BratOutTake(hm, tele);
        grippers = new Grippers(hm, tele, c);

        telemetry = tele;
        controls = c;
        STATE = STATES_OOUTTAKE.READY_FOR_PIXELS;
        grippers.reset();
        feedUpdater = new MultiTickUpdater(10);
    }
    private int closed = 0;
    private void handleControls(){

        ///////// ELEVATOR /////////
        if(controls.retractElevtor && elevator.getLevelNow() > 0){
            STATE = STATES_OOUTTAKE.SHOUD_RETRACT;
            grippers.reset();
        }
        if(controls.extendElevator){
            elevator.setLevel(STATES_OOUTTAKE.saved_level);
        }
        if(controls.stepElevtorUp){
            elevator.setLevel(elevator.getLevel() + 1);
        }
        if(controls.stepElevatorDown){
            elevator.setLevel(elevator.getLevel() - 1);
        }

        ///////// ARM ///////////
        if(controls.rotatePixels){
            if(arm.isRotated) arm.antiRotate90();
            else arm.rotate90();
        }
        if(controls.feedForward){
            STATE = STATES_OOUTTAKE.SET_FEED_FORWARD;
        }
        if(controls.rotatePixels){
            if(arm.isRotated)
                arm.antiRotate90();
            else arm.rotate90();
        }

        //////// CLAW //////////
        if(controls.dropPixel1){
            grippers.Drop1();
            closed++;
        }
        if(controls.dropPilex2){
            grippers.Drop2();
            closed++;
        }
        if(controls.pixelFail){
            grippers.reset();
        }

    }

    public void update(){
        elevator.update_values();
        grippers.update_values();
        switch (STATE){
            case SHOUD_RETRACT:

                arm.deactivate();
                arm.update();
                STATES_OOUTTAKE.saved_level = elevator.getLevel();
                elevator.setLevel(RetractLevel);

                STATE = STATES_OOUTTAKE.WAIT_FOR_ELEVATOR_REST;
                STATES_OOUTTAKE.pos = RetractLevel;
                closed = 0;

                break;

            case RESET_ELEVATOR:

                STATE = STATES_OOUTTAKE.WAIT_FOR_ELEVATOR_REST;
                STATES_OOUTTAKE.pos = 0;
                elevator.setLevel(0);

                break;
            case SET_FEED_FORWARD:
                // TODO:
                elevator.setLevel(FeedForwardPos);
                STATES_OOUTTAKE.pos = FeedForwardPos;
                STATE = STATES_OOUTTAKE.WAIT_FOR_ELEVATOR_REST;
                break;
            case FEED:
                if(arm.isAtRest() && feedUpdater.getState()){
                    STATE = STATES_OOUTTAKE.WAIT_FOR_ELEVATOR_REST;
                    elevator.setLevel(stallPixels);
                    STATES_OOUTTAKE.pos = stallPixels;
                    feedUpdater.reset();
                }
                feedUpdater.update();
                break;

            case WAIT_FOR_ARM_REST:
                if(arm.isAtRest()){
                    STATE = STATES_OOUTTAKE.RESET_ELEVATOR;
                }
                break;
            case WAIT_FOR_ELEVATOR_REST:
                if(elevator.getLevelNow() <= STATES_OOUTTAKE.pos + 0.1 &&
                    elevator.getLevelNow() >= STATES_OOUTTAKE.pos - 0.1){
                    if(STATES_OOUTTAKE.pos == RetractLevel) {
                        STATE = STATES_OOUTTAKE.WAIT_FOR_ARM_REST;
                    } else if(STATES_OOUTTAKE.pos == 0){
                        STATE = STATES_OOUTTAKE.READY_FOR_PIXELS;
                    } else if(STATES_OOUTTAKE.pos == FeedForwardPos){
                        arm.setFeedPos();
                        STATE = STATES_OOUTTAKE.FEED;
                    } else if(STATES_OOUTTAKE.pos == stallPixels){
                        grippers.Drop1();
                        grippers.Drop2();
                        elevator.setLevel(RetractLevel);
                        STATE = STATES_OOUTTAKE.SHOUD_RETRACT;
                    }
                }
                break;
            case READY_FOR_PIXELS:
                if(elevator.getLevelNow() > 3.3){
                    arm.activate();
                    arm.rotate90();
                }
                break;
        }

        if(elevator.getLevelNow() > 0.03){
            grippers.dezactivateAuto();
        } else grippers.ActivateAuto();

//        if(closed == 2){
//            STATE = STATES_OOUTTAKE.SHOUD_RETRACT;
//        }


        handleControls();

        arm.update();
        grippers.update();
        elevator.loop();
        telemetry.addData("Out take state", STATE.toString());
        telemetry.addData("state pos stalling", STATES_OOUTTAKE.pos);
        telemetry.addData("level now", elevator.getLevelNow());
        telemetry.addData("shoud go to", STATES_OOUTTAKE.pos);
    }
}
