package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Admin on 10/25/2017.
 */

@TeleOp(name = "Joystick", group = "")
public class Joystick extends LinearOpMode {
    DcMotor motor_left;
    //DcMotor motor_left2;
    DcMotor motor_right;
    //DcMotor motor_right2;
    DcMotor motor_center;

    @Override
    public void runOpMode(){
        motor_left = hardwareMap.get(DcMotor.class, "left");
        //motor_left2 = hardwareMap.get(DcMotor.class, "left2");
        motor_right = hardwareMap.get(DcMotor.class,"right");
        //motor_right2 = hardwareMap.get(DcMotor.class, "right2");
        motor_center = hardwareMap.get(DcMotor.class, "center");


        waitForStart();
        while(opModeIsActive()) {
            double yvalue = gamepad1.right_stick_y;
            double xvalue = gamepad1.right_stick_x;

            double W = (1-Math.abs(yvalue)) * (xvalue) + xvalue;
            double V = (1-Math.abs(xvalue)) * (yvalue) + yvalue;
            double R = (W+V)/2;
            double L = (W-V)/2;

            motor_left.setPower(L);
            //motor_left2.setPower(L);
            motor_right.setPower(R);
            //motor_right2.setPower(R);
            motor_center.setPower(gamepad1.left_stick_x);
        }
        motor_left.setPower(0);
        //motor_left2.setPower(0);
        motor_right.setPower(0);
        //motor_right2.setPower(0);
        motor_center.setPower(gamepad1.left_stick_x);


    }
}
