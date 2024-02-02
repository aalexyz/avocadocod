package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import org.firstinspires.ftc.teamcode.MultiTickUpdater;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Elevatorpep {

	public LiftStates STATE = LiftStates.STATIC;
	public enum LiftDirection{
		STOP_AND_RESET,
		GO_DOWN,
		NORMAL;
	};
	private LiftDirection liftDirection;

	final private DcMotorEx left, right;

	public static final double fullExtend = 880;
	public static final double oneStep = fullExtend/11;
	private int level = 0;
	private double gotoPos = 0, rightMotorPos = 0, leftMotorPos = 0;
	private double speedLeft = 0, speedRight = 0;

	Telemetry telemetry;
	public MultiTickUpdater goHardDownUpdater;

	public Elevatorpep(HardwareMap hardwareMap, Telemetry tel){
		left = hardwareMap.get(DcMotorEx.class, "ll");
		right = hardwareMap.get(DcMotorEx.class, "lr");

		goHardDownUpdater = new MultiTickUpdater(5);

		left.setDirection(DcMotorEx.Direction.REVERSE);

		MotorConfigurationType mct = left.getMotorType().clone();
		mct.setAchieveableMaxRPMFraction(1.0);
		left.setMotorType(mct);

		mct = right.getMotorType().clone();
		mct.setAchieveableMaxRPMFraction(1.0);
		right.setMotorType(mct);

		left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		left.setTargetPosition(0);
		right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		right.setTargetPosition(0);

		left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		left.setPower(1);
		right.setPower(1);

		telemetry = tel;

		liftDirection = LiftDirection.NORMAL;

	}
	public void update_values(){
		double d = rightMotorPos;
		rightMotorPos = right.getCurrentPosition();
		speedRight = Math.abs(d - rightMotorPos);

		d = leftMotorPos;
		leftMotorPos = left.getCurrentPosition();
		speedLeft = Math.abs(d - leftMotorPos);
	}
	public void loop(){

		switch (liftDirection){
			case NORMAL:
				left.setTargetPosition((int)gotoPos);
				right.setTargetPosition((int)gotoPos);

				left.setPower(1.0);
				right.setPower(1.0);
				break;
			case STOP_AND_RESET:
				left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
				right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

				left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

				left.setTargetPosition(0);
				right.setTargetPosition(0);

				left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
				right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
				goHardDownUpdater.reset();
				liftDirection = LiftDirection.NORMAL;
				break;
			case GO_DOWN:
				left.setPower(-0.5);
				right.setPower(-0.5);
				goHardDownUpdater.update();

				if(speedRight == 0 && speedLeft == 0 && goHardDownUpdater.getState()){
					liftDirection = LiftDirection.STOP_AND_RESET;
					left.setPower(0);
					right.setPower(0);
				}
				break;

		}


		telemetry.addData("right current position", rightMotorPos);
		telemetry.addData("left current position", leftMotorPos);
		telemetry.addData("left velocity", speedLeft);
		telemetry.addData("right velocity", speedRight);
		telemetry.addData("level", level);
		telemetry.addData("target position", gotoPos);
		telemetry.addData("STATE", STATE.toString());


		telemetry.update();
	}

	public int getLevel(){
		return level;
	}

	public void setLevel(int lvl){
		int correctedLevel;
		if(lvl > 11) correctedLevel = 11;
		else if(lvl < 0) correctedLevel = 0;
		else correctedLevel = lvl;

		if(correctedLevel > level) STATE = LiftStates.GO_UP;
		else if(correctedLevel < level) STATE = LiftStates.GO_DOWN;
		else STATE = LiftStates.STATIC;

		gotoPos = (double)(correctedLevel * oneStep);

		level = correctedLevel;

		if(level == 0){
			right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
			left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
			liftDirection = LiftDirection.GO_DOWN;
		} else liftDirection = LiftDirection.NORMAL;

	}

	public double getLevelNow(){
		return (double)rightMotorPos/oneStep;
	}


}
