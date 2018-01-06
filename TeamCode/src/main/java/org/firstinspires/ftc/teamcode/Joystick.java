package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Admin on 10/25/2017.
 */

@TeleOp(name = "Joystick", group = "")
public class Joystick extends LinearOpMode {
    DcMotor motor_left;
    DcMotor motor_right;

    DcMotor motor_arm;

    Servo Servo_L;
    Servo Servo_R;

    public int armPos = 0;
    @Override
    public void runOpMode(){
        motor_left = hardwareMap.get(DcMotor.class, "left");
        motor_right = hardwareMap.get(DcMotor.class,"right");

        motor_arm = hardwareMap.get(DcMotor.class, "arm");

        Servo_L = hardwareMap.get(Servo.class, "left_hand");
        Servo_R = hardwareMap.get(Servo.class, "right_hand");

        motor_arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motor_arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        motor_arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while(opModeIsActive()) {


            handleDriveTrain();

            InstructTheClaw();

            ManipulateArm();
            telemetry.update();
        }


        motor_left.setPower(0);
        //motor_left2.setPower(0);
        motor_right.setPower(0);
        //motor_right2.setPower(0);

        motor_arm.setPower(0);


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
    ///////////////////////////
    public void InstructTheClaw() {

        float x = gamepad2.right_trigger;
        if (x > 0) {
            Servo_R.setPosition(1);
            telemetry.addData("", "Servo Position is 1");

        } else if (gamepad2.right_bumper) {
            Servo_R.setPosition(0);
            telemetry.addData("", "Servo Position is 0");
        }
        float y = gamepad2.left_trigger;
        if (y > 0) {
            Servo_L.setPosition(0);
            telemetry.addData("", "Servo Position is 0");
        } else if (gamepad2.left_bumper) {
            Servo_L.setPosition(1);
            telemetry.addData("", "Servo Position is 1");
        }
    }
    ///////////////////////////
    //controls movment of the Arm
    ///////////////////////////
    public void ManipulateArm()
    {

        if(gamepad2.left_stick_y > 0.1){
            motor_arm.setPower(.7);

        }
        else if (gamepad2.left_stick_y < -.1){
            motor_arm.setPower(-0.25);

        }
        else{
            motor_arm.setPower(0);
        }
        /*if (gamepad2.dpad_down){
            armPos=0;
        }
        else if (gamepad2.dpad_left){
            armPos=159;
        }
        else if (gamepad2.dpad_up){
            armPos=188;
        }
        else if (gamepad2.dpad_right){
            armPos=340;
        }
        else if (gamepad2.right_stick_y!=0) {
            armPos = armPos + ((int)1.5 * Math.round(gamepad2.right_stick_y));
        }
        motor_arm. setTargetPosition(armPos);
        if((Math.abs(armPos-motor_arm.getCurrentPosition()) > 10) && (armPos < motor_arm.getCurrentPosition())){
            motor_arm.setPower(-.25);
        }
        else if ((Math.abs(armPos-motor_arm.getCurrentPosition()) > 10) && (armPos > motor_arm.getCurrentPosition())) {
            motor_arm.setPower(.75);
        }*/


        telemetry.addData("armPos",armPos);
        telemetry.addData("current position",motor_arm.getCurrentPosition());




    }
}
