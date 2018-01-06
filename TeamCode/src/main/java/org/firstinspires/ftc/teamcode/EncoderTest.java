package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Admin on 11/4/2017.
 */
@Autonomous(name = "Encoder Test", group = " ")
public class EncoderTest extends LinearOpMode{

    DcMotor motor_left;
    DcMotor motor_right;
    DcMotor motor_center;


    @Override
    public void runOpMode(){

        motor_left = hardwareMap.dcMotor.get("left");
        motor_right = hardwareMap.dcMotor.get("right");
        motor_center = hardwareMap.dcMotor.get("center");



        motor_left.setTargetPosition(-2500);
        motor_right.setTargetPosition(-2500);


        motor_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        waitForStart();
        while(opModeIsActive()){
            motor_right.setPower(.5);
            motor_left.setPower(.5);

            telemetry.addData("motor_right", motor_right.getCurrentPosition());
            telemetry.addData("motor_left", motor_left.getCurrentPosition());
            telemetry.update();


            idle();
        }

        motor_right.setPower(0);
        motor_left.setPower(0);
        motor_center.setPower(0);

        motor_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_center.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



    }
}
