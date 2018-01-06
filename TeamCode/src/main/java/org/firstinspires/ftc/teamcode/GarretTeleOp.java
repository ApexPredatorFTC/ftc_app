package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Admin on 10/25/2017.
 */

@TeleOp(name = "Garret TeleOp", group = "")
public class GarretTeleOp extends LinearOpMode {
    DcMotor motor_left;
    DcMotor motor_right;



    public int armPos = 0;
    @Override
    public void runOpMode(){
        motor_left = hardwareMap.get(DcMotor.class, "left");
        motor_right = hardwareMap.get(DcMotor.class,"right");





        waitForStart();

        while(opModeIsActive()) {


            handleDriveTrain();


            telemetry.update();
        }


        motor_left.setPower(0);
        //motor_left2.setPower(0);
        motor_right.setPower(0);
        //motor_right2.setPower(0);




    }

    public void handleDriveTrain()
    {
        double a = .75;

        double yvalue = a*Math.pow(gamepad1.right_stick_y,3) + ((1-a)*gamepad1.right_stick_y);;
        double xvalue = a*Math.pow(gamepad1.right_stick_x, 3) + ((1-a)*gamepad1.right_stick_x);
        xvalue=-xvalue;

        double W = (1-Math.abs(yvalue)) * (xvalue) + xvalue;
        double V = (1-Math.abs(xvalue)) * (yvalue) + yvalue;
        double R = (W+V)/2;
        double L = (W-V)/2;

        motor_left.setPower(L);
        //motor_left2.setPower(L);
        motor_right.setPower(R);
        //motor_right2.setPower(R);

    }

    ///////////////////////////
    //controls movment of the claw

    //controls movment of the Arm
    ///////////////////////////
   }
