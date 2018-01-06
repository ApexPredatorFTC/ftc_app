package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Admin on 11/29/2017.
 */
@Autonomous(name = "OtherCornerRed", group = "")
public class OtherCornerRed extends LinearOpMode {
    DcMotor motor_left;
    DcMotor motor_right;
    DcMotor motor_center;
    DcMotor motor_arm;

    ColorSensor cSensor;


    Servo Servo_L;
    Servo Servo_R;
    float x = 0;
    float y = 0;

    // hsvValues is an array that will hold the hue, saturation, and value information.
    float hsvValues[] = {0F, 0F, 0F};

    // values is a reference to the hsvValues array.
    final float values[] = hsvValues;
    final double SCALE_FACTOR = 255;

    @Override
    public void runOpMode() {
        motor_left = hardwareMap.get(DcMotor.class, "left");
        motor_right = hardwareMap.get(DcMotor.class, "right");
        motor_center = hardwareMap.get(DcMotor.class, "center");


        Servo_L = hardwareMap.get(Servo.class, "left_hand");
        Servo_R = hardwareMap.get(Servo.class, "right_hand");
        motor_arm = hardwareMap.get(DcMotor.class, "arm");

        motor_arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motor_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_center.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        motor_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_center.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        cSensor = hardwareMap.get(ColorSensor.class, "cSensor");
        waitForStart();



        yamTheClaw();
        sleep(1000);

        driveForward(2.7, 1);
        resetEncoders();
        turn(70, .2);
        driveForward(.6, 1);
        openClaw();
        sleep(1000);
        resetEncoders();
        driveForward(-.4, 1);

        resetEncoders();


    }

    public void driveForward(double feetDist, double power) {
        double distance = feetDist * 1080;
        motor_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motor_left.setTargetPosition((int) distance + motor_left.getCurrentPosition());
        motor_right.setTargetPosition((-(int) distance + motor_right.getCurrentPosition()));

        motor_left.setPower(power);
        motor_right.setPower(-power);


        while (opModeIsActive() && (motor_left.isBusy() && motor_right.isBusy())) {

            telemetry.addData("Encoder Target", distance);
            telemetry.addData("Left Encoder", motor_left.getCurrentPosition());
            telemetry.addData("Right Encoder", motor_right.getCurrentPosition());
            telemetry.update();

        }

        motor_left.setPower(0);
        motor_right.setPower(0);

    }

    public void turn(double degrees, double power) {
        //-2500 to both motors is 180 degree turn
        //-13.8 encoder counts is one degree
        double position = (-13.8 * degrees);
        motor_left.setTargetPosition((int) (position));
        motor_right.setTargetPosition((int) (position));

        motor_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (position < 0) {
            motor_left.setPower(power);
            motor_right.setPower(power);
        } else if (position > 0) {
            motor_left.setPower(-power);
            motor_right.setPower(-power);
        }
        while (opModeIsActive() && (motor_left.isBusy() && motor_right.isBusy())) {

            telemetry.addData("Encoder Target", position);
            telemetry.addData("Left Encoder", motor_left.getCurrentPosition());
            telemetry.addData("Right Encoder", motor_right.getCurrentPosition());
            telemetry.update();

        }
    }

    public void openClaw() {
        Servo_L.setPosition(0);
        Servo_R.setPosition(1);

        motor_left.setPower(0);
        motor_right.setPower(0);

        sleep(1000);

    }

    public void yamTheClaw() {
        Servo_L.setPosition(1);
        Servo_R.setPosition(0);

        motor_left.setPower(0);
        motor_right.setPower(0);

        sleep(1000);

    }

    public void resetEncoders() {
        motor_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_center.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(200);


    }

    public void detectColor(){
        Color.RGBToHSV((int) (cSensor.red() * SCALE_FACTOR),
                (int) (cSensor.green() * SCALE_FACTOR),
                (int) (cSensor.blue() * SCALE_FACTOR),
                hsvValues);

        if(cSensor.red() > 150){


        }

    }

}


