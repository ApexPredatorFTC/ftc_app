package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Admin on 11/15/2017.
 */
@TeleOp(name = "MotorModes", group = "")
public class MotorModeTests extends LinearOpMode{
    DcMotor motor;

    @Override
    public void runOpMode(){
        motor = hardwareMap.dcMotor.get("motor");

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        while(opModeIsActive()){
            if(gamepad1.dpad_up){
                motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            if(gamepad1.dpad_right){

                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

            if(gamepad1.x){
                motor.setPower(0.5);

            }
        }
        motor.setPower(0);

    }


}
