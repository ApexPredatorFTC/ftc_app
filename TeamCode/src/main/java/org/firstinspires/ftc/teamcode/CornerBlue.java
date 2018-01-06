package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Admin on 11/29/2017.
 */
@Autonomous(name = "CornerBlue", group = "")
public class CornerBlue extends LinearOpMode {
    DcMotor motor_left;
    DcMotor motor_right;
    DcMotor motor_center;
    DcMotor motor_arm;
    Servo Servo_L;
    Servo Servo_R;
    float x = 0;
    float y = 0;


        public static final String TAG = "Vuforia VuMark Sample";

        OpenGLMatrix lastLocation = null;
        VuforiaLocalizer vuforia;

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
            //int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            //VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

            // OR...  Do Not Activate the Camera Monitor View, to save power
            // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        /*
         * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
         * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
         * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
         * web site at https://developer.vuforia.com/license-manager.
         *
         * Vuforia license keys are always 380 characters long, and look as if they contain mostly
         * random data. As an example, here is a example of a fragment of a valid key:
         *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
         * Once you've obtained a license key, copy the string from the Vuforia web site
         * and paste it in to your code onthe next line, between the double quotes.
         */
            //parameters.vuforiaLicenseKey = "AWckC9P/////AAAAGQAbrzfWpEF1qYwCpiUOr7RVSfW6jrhfxzTHAroEzCmsYWamxL3Ouv5nHuVtyVDiu0lzb1kWGfkCs5wTzyQIIiKDNYP70491X/5gnZgwXxEj2EkIM5u/ek+G14ilZlDIeaCh6nMXglX8Q/NVXfK1ox33KR68lYMIBWcJdLmLbKPUWylHXXSvFLHpUSvyvpXKwgovzlxtmXTCcYqvMoVwTBWcHPazaupqXBxp4aeF1w9xvIr5GYdq5kzzL7Vs/AUH5QU5PG/0UFLlM+frXCdJcWzLK0u8X+CyNIJjMskZBfBW+9bGC0uynIP4gP94HmGdviuoTtPuWXUTJrvCsrPHpD3rXDt6o6BIiNGQs28a3W2p";

        /*
         * We also indicate which camera on the RC that we wish to use.
         * Here we chose the back (HiRes) camera (for greater range), but
         * for a competition robot, the front camera might be more convenient.
         */
            //parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
            //this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

            /**
             * Load the data set containing the VuMarks for Relic Recovery. There's only one trackable
             * in this data set: all three of the VuMarks in the game were created from this one template,
             * but differ in their instance id information.
             * @see VuMarkInstanceId
             */
            //VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
            //VuforiaTrackable relicTemplate = relicTrackables.get(0);
            //relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
            closeClaw();
            waitForStart();


            //left

        /*turn(7.5, 0.3);

        sleep(1000);
        driveForward(3.3, 0.5);
        sleep(500);
        openClaw();
        driveForward(-0.2, 1);
        resetEncoders();
        _________________
        */

            //RIGHT
            turn(23, 0.3);

            sleep(1000);
            driveForward(3.4, 0.5);
            sleep(1000);
            openClaw();
            driveForward(-0.4, 1);
            sleep(1000);
            driveForward(1, 1);
            driveForward(-0.4, 1);
            resetEncoders();

            //CENTER
        /*turn(12, 0.3);

        sleep(1000);
        driveForward(3.4, 0.5);
        sleep(1000);
        openClaw();
        driveForward(-0.4, 1);
        resetEncoders();
        */


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
            } else {
                motor_left.setPower(-power);
                motor_right.setPower(-power);
            }
        }

        public void resetEncoders() {
            motor_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor_center.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            sleep(200);


        }

        public void closeClaw() {
            Servo_L.setPosition(.5);
            Servo_R.setPosition(.35);


            sleep(500);
        }

        public void openClaw() {
            Servo_R.setPosition(1);
            Servo_L.setPosition(0);
        }
    }


