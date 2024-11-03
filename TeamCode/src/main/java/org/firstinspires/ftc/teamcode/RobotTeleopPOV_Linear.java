package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/*
 * TODO:
 *  Figure out if we should have encoders for drive motors
 */

@TeleOp(name = "Main Teleop", group = "Robot")
public class RobotTeleopPOV_Linear extends LinearOpMode {

    // device names
    private static final String leftDriveName = "left_drive";
    private static final String rightDriveName = "right_drive";
    private static final String armName = "left_arm";
    private static final String wristName = "wrist_pivot";
    private static final String intakeName = "intake";

    private static final double WRIST_MIN = -0.8;
    private static final double WRIST_MAX = 0.8;
    private static final double WRIST_MIDDLE = 0.6667;
    private static final double WRIST_SPEED = 0.02;
    private static final double ARM_UP = 0.45;
    private static final double ARM_DOWN = -0.45;

    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor armMotor = null;
    private Servo wrist = null;
    private CRServo intake = null;

    private double wristOffset = 0;

    private void setUpDriveMotors() {
        leftDrive = hardwareMap.get(DcMotor.class, leftDriveName);
        rightDrive = hardwareMap.get(DcMotor.class, rightDriveName);

        // To drive forward, one motor must be reversed because the axles point in opposite directions
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy
        // leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void setUpArmMotor() {
        armMotor = hardwareMap.get(DcMotor.class, armName);
    }

    private void setUpWristServo() {
        wrist = hardwareMap.get(Servo.class, wristName);
        wrist.setPosition(WRIST_MIDDLE);
    }

    private void setUpIntakeServo() {
        intake = hardwareMap.get(CRServo.class, intakeName);
    }

    private void driveFirstPerson(double drive, double turn) {
        // Combine drive and turn for blended motion.
        double left = drive + turn;
        double right = drive - turn;

        // Normalize the values so neither exceed +/- 1.0
        double max = Math.max(Math.abs(left), Math.abs(right));
        if (max > 1.0) {
            left /= max;
            right /= max;
        }

        leftDrive.setPower(left);
        rightDrive.setPower(right);

        telemetry.addData("left", "%.2f", left);
        telemetry.addData("right", "%.2f", right);
    }

    private void moveArm(boolean up, boolean down, boolean stop) {
        if (up) {
            armMotor.setPower(ARM_UP);
        } else if (down) {
            armMotor.setPower(ARM_DOWN);
        } else if (stop) {
            armMotor.setPower(0.0);
        }
    }

    private void moveWrist(boolean right, boolean left) {
        if (right) {
            wristOffset += WRIST_SPEED;
        } else if (left) {
            wristOffset -= WRIST_SPEED;
        }

        wristOffset = Range.clip(wristOffset, WRIST_MIN, WRIST_MAX);
        wrist.setPosition(WRIST_MIDDLE + wristOffset);

        telemetry.addData("wrist", "Offset = %.2f", wristOffset);
    }

    private void moveIntake(boolean in, boolean out, boolean stop) {
        // need to document which one is in and which one is out
        // also extract magic numbers into final members
        if (out) {
            intake.setPower(-1.0);
        } else if (in) {
            intake.setPower(0.5);
        } else if (stop) {
            intake.setPower(0.0);
        }
    }

    @Override
    public void runOpMode() {
        // set up motors
        setUpDriveMotors();
        setUpArmMotor();

        // set up servos
        setUpWristServo();
        setUpIntakeServo();

        telemetry.addData(">", "Setup complete. Press start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // joystick y is negative for forward, so negate it
            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            driveFirstPerson(drive, turn);
            moveArm(gamepad1.dpad_up, gamepad2.dpad_down, gamepad1.y);
            moveWrist(gamepad1.right_bumper, gamepad1.left_bumper);
            moveIntake(gamepad1.b, gamepad1.a, gamepad1.x);

            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}
