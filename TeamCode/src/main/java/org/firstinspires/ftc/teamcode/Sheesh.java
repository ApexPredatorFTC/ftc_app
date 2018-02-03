package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.Locale;

/**
 * Created by Admin on 1/31/2018.
 */
@TeleOp
public class Sheesh extends LinearOpMode {
    boolean on=true;
    BNO055IMU imu;
    DcMotor frontLeft;
    DcMotor backLeft;
    DcMotor frontRight;
    DcMotor backRight;
    //DcMotor rightSpin;
    //DcMotor leftSpin;
    //DcMotor lifter;
    // State used for updating telemetry
    Orientation angles;

    static final double COUNTS_PER_INCH = 104;
    static final double P_DRIVE_COEFF = .01;
    static final double HEADING_THRESHOLD = 1;      // As tight as we can make it with an integer gyro
    static final double P_TURN_COEFF = 0.017;
    @Override public void runOpMode() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        //rightSpin = hardwareMap.get(DcMotor.class, "rightSpin");
        //leftSpin = hardwareMap.get(DcMotor.class, "leftSpin");
        // lifter = hardwareMap.get(DcMotor.class, "lifter");

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.loggingEnabled = true;
        parameters.loggingTag     = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        //composeTelemetry();
        waitForStart();

        while(opModeIsActive() && on == true){

            GyroTurn(30);

            on=false;

        }

}
    public double getError(double targetAngle) {

        double robotError;

        // calculate error in -179 to +180 range  (
        robotError = targetAngle + angles.firstAngle;
        while (robotError > 180) robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }

    public double getSteer(double error, double PCoeff) {
        return Range.clip(error * PCoeff, -1, 1);
    }

    public void Move(double distance, double speed, double direction) {
        if (opModeIsActive()) {
            direction = direction * Math.PI / 180;

            //Reset Encoder Counts 0
            resetEncoders();
            double yDist = Math.cos(direction) * distance;
            double xDist = Math.sin(direction)* distance;

            double ySpeed = Math.cos(direction) * speed;
            double xSpeed = Math.sin(direction)* speed;

            double var = 1/Math.sqrt(2);
            double frontLeftDistOffset = -(var * xDist) - (var*yDist);
            double backLeftDistOffset = (var * xDist) - (var*yDist);
            double backRightDistOffset = (var * xDist) + (var*yDist);
            double frontRightDistOffset = -(var * xDist) + (var*yDist);


            int frontLeftPosition = (int)(frontLeft.getCurrentPosition()+(frontLeftDistOffset*COUNTS_PER_INCH));
            int backLeftPosition = (int)(backLeft.getCurrentPosition()+(backLeftDistOffset*COUNTS_PER_INCH));
            int backRightPosition = (int)(backRight.getCurrentPosition()+(backRightDistOffset*COUNTS_PER_INCH));
            int frontRightPosition = (int)(frontRight.getCurrentPosition()+(frontRightDistOffset*COUNTS_PER_INCH));

            frontLeft.setTargetPosition(frontLeftPosition);
            frontRight.setTargetPosition(frontRightPosition);
            backLeft.setTargetPosition(backLeftPosition);
            backRight.setTargetPosition(backRightPosition);


            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (opModeIsActive() &&
                    (frontLeft.isBusy() && backRight.isBusy() && backLeft.isBusy() && frontRight.isBusy())) {

                // adjust relative speed based on heading error.
                angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                double error = getError(0);
                double steer = getSteer(error, P_DRIVE_COEFF);

                double frontLeftSpeed = -(var * xSpeed) - (var*ySpeed) -steer;
                double backLeftSpeed = (var * xSpeed) - (var*ySpeed) - steer;
                double backRightSpeed = (var * xSpeed) + (var*ySpeed) - steer;
                double frontRightSpeed  = -(var * xSpeed) + (var*ySpeed) - steer;

                // Normalize speeds if any one exceeds +/- 1.0;
                double max1 = Math.max(Math.abs(frontLeftSpeed), Math.abs(frontRightSpeed));
                double max2 = Math.max(Math.abs(backRightSpeed), Math.abs(backLeftSpeed));
                double max = Math.max(max1, max2);
                if (max > 1.0) {
                    frontLeftSpeed /= max;
                    frontRightSpeed /= max;
                    backRightSpeed /= max;
                    backLeftSpeed /= max;

                }

                frontLeft.setPower(frontLeftSpeed);
                frontRight.setPower(frontRightSpeed);
                backLeft.setPower(backLeftSpeed);
                backRight.setPower(backRightSpeed);
                // Display drive status for the driver.
                telemetry.addData("Err/St", "%5.1f/%5.1f", error, steer);
                telemetry.addData("Target", "%7d:%7d:%7d:%7d", frontLeftPosition, frontRightPosition, backLeftPosition, backRightPosition);
                telemetry.addData("Actual", "%7d:%7d:%7d:%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(), backLeft.getCurrentPosition(), backRight.getCurrentPosition());

                telemetry.addData("Speed", "%5.2f:%5.2f:%5.2f:%5.2f", frontLeftSpeed, frontRightSpeed, backLeftSpeed, backRightSpeed);
                telemetry.addData("Heading", angles.firstAngle);
                telemetry.update();

            }

            // Stop all motion;
            frontRight.setPower(0);
            backLeft.setPower(0);
            frontLeft.setPower(0);
            backRight.setPower(0);

            // Turn off RUN_TO_POSITION
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        }
    }

    public void resetEncoders(){
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void EncoderMove(double distance, double speed, double direction) {
        if (opModeIsActive()) {
            //Convert direction to radians
            direction = direction * Math.PI / 180;

            //Reset Encoder Counts 0
            resetEncoders();

            double yDist = Math.cos(direction) * distance;
            double xDist = Math.sin(direction)* distance;

            double ySpeed = Math.cos(direction) * speed;
            double xSpeed = Math.sin(direction)* speed;

            double var = 1/Math.sqrt(2);
            double frontLeftDistOffset = -(var * xDist) - (var*yDist);
            double backLeftDistOffset = (var * xDist) - (var*yDist);
            double backRightDistOffset = (var * xDist) + (var*yDist);
            double frontRightDistOffset = -(var * xDist) + (var*yDist);

            double frontLeftSpeed = -(var * xSpeed) - (var*ySpeed);
            double backLeftSpeed = (var * xSpeed) - (var*ySpeed);
            double backRightSpeed = (var * xSpeed) + (var*ySpeed);
            double frontRightSpeed  = -(var * xSpeed) + (var*ySpeed);

            int frontLeftPosition = (int)(frontLeft.getCurrentPosition()+(frontLeftDistOffset*COUNTS_PER_INCH));
            int backLeftPosition = (int)(backLeft.getCurrentPosition()+(backLeftDistOffset*COUNTS_PER_INCH));
            int backRightPosition = (int)(backRight.getCurrentPosition()+(backRightDistOffset*COUNTS_PER_INCH));
            int frontRightPosition = (int)(frontRight.getCurrentPosition()+(frontRightDistOffset*COUNTS_PER_INCH));

            frontLeft.setTargetPosition(frontLeftPosition);
            frontRight.setTargetPosition(frontRightPosition);
            backLeft.setTargetPosition(backLeftPosition);
            backRight.setTargetPosition(backRightPosition);

            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (opModeIsActive() &&
                    (frontLeft.isBusy() && backRight.isBusy() && backLeft.isBusy() && frontRight.isBusy())) {


                // Normalize speeds if any one exceeds +/- 1.0;
                double max1 = Math.max(Math.abs(frontLeftSpeed), Math.abs(frontRightSpeed));
                double max2 = Math.max(Math.abs(backRightSpeed), Math.abs(backLeftSpeed));
                double max = Math.max(max1, max2);
                if (max > 1.0) {
                    frontLeftSpeed /= max;
                    frontRightSpeed /= max;
                    backRightSpeed /= max;
                    backLeftSpeed /= max;

                }

                frontLeft.setPower(frontLeftSpeed);
                frontRight.setPower(frontRightSpeed);
                backLeft.setPower(backLeftSpeed);
                backRight.setPower(backRightSpeed);
                // Display drive status for the driver.

                telemetry.addData("Target", "%7d:%7d:%7d:%7d", frontLeftPosition, frontRightPosition, backLeftPosition, backRightPosition);
                telemetry.addData("Actual", "%7d:%7d:%7d:%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(), backLeft.getCurrentPosition(), backRight.getCurrentPosition());

                telemetry.addData("Speed", "%5.2f:%5.2f:%5.2f:%5.2f", frontLeftSpeed, frontRightSpeed, backLeftSpeed, backRightSpeed);
                telemetry.update();

            }

            // Stop all motion;
            frontRight.setPower(0);
            backLeft.setPower(0);
            frontLeft.setPower(0);
            backRight.setPower(0);

            // Turn off RUN_TO_POSITION
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        }
    }

    void composeTelemetry() {

        // At the beginning of each telemetry update, grab a bunch of data
        // from the IMU that we will then display in separate lines.
        telemetry.addAction(new Runnable() { @Override public void run()
        {
            // Acquiring the angles is relatively expensive; we don't want
            // to do that in each of the three items that need that info, as that's
            // three times the necessary expense.
            angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        }
        });

        telemetry.addLine()
                .addData("status", new Func<String>() {
                    @Override public String value() {
                        return imu.getSystemStatus().toShortString();
                    }
                })
                .addData("calib", new Func<String>() {
                    @Override public String value() {
                        return imu.getCalibrationStatus().toString();
                    }
                });

        telemetry.addLine()
                .addData("heading", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, -angles.firstAngle);
                    }
                })
                .addData("roll", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.secondAngle);
                    }
                })
                .addData("pitch", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.thirdAngle);
                    }
                });
    }

    public void GyroTurn(double degrees) {
        if (opModeIsActive()) {

            double turnError;
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            double initialError = getError(0);

            boolean turn = false;

            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            while (opModeIsActive() && (turn == false)) {
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

                turnError = getError(degrees) + initialError;

                telemetry.addData("turnerror", turnError);
                telemetry.update();

                if (Math.abs(turnError) <= HEADING_THRESHOLD) {
                    turn = true;
                } else {

                    double steer = getSteer(turnError, P_TURN_COEFF);

                    double frontLeftSpeed = (steer);
                    double frontRightSpeed = (steer);
                    double backRightSpeed = (steer);
                    double backLeftSpeed = (steer);

                    double max1 = Math.max(Math.abs(frontLeftSpeed), Math.abs(frontRightSpeed));
                    double max2 = Math.max(Math.abs(backRightSpeed), Math.abs(backLeftSpeed));
                    double max = Math.max(max1, max2);
                    if (max > 1.0) {
                        frontLeftSpeed /= max;
                        frontRightSpeed /= max;
                        backRightSpeed /= max;
                        backLeftSpeed /= max;

                    }

                    frontLeft.setPower(frontLeftSpeed);
                    frontRight.setPower(frontRightSpeed);
                    backLeft.setPower(backLeftSpeed);
                    backRight.setPower(backRightSpeed);
                }
            }
            frontRight.setPower(0);
            backLeft.setPower(0);
            frontLeft.setPower(0);
            backRight.setPower(0);
        }
    }


    //----------------------------------------------------------------------------------------------
    // Formatting
    //----------------------------------------------------------------------------------------------



    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }
    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}
