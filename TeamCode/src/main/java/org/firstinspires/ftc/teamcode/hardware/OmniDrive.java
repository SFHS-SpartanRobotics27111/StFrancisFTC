package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
 * TODO:
 *  Figure out if we should have encoders for drive motors
 */

public class OmniDrive {
    private final ElapsedTime runtime = new ElapsedTime();
    private final String LEFT_FRONT_DRIVE = "left_front_drive";
    private final String LEFT_BACK_DRIVE = "left_back_drive";
    private final String RIGHT_FRONT_DRIVE = "right_front_drive";
    private final String RIGHT_BACK_DRIVE = "right_back_drive";
    private final Telemetry telemetry;
    public DcMotor leftFrontDrive;
    public DcMotor leftBackDrive;
    public DcMotor rightFrontDrive;
    public DcMotor rightBackDrive;

    public OmniDrive(HardwareMap hardwareMap, Telemetry telemetry1) {
        telemetry = telemetry1;
        leftFrontDrive = hardwareMap.get(DcMotor.class, LEFT_FRONT_DRIVE);
        leftBackDrive = hardwareMap.get(DcMotor.class, LEFT_BACK_DRIVE);
        rightFrontDrive = hardwareMap.get(DcMotor.class, RIGHT_FRONT_DRIVE);
        rightBackDrive = hardwareMap.get(DcMotor.class, RIGHT_BACK_DRIVE);

        // To drive forward, one motor must be reversed because the axles point in opposite directions
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy
        // leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void driveFirstPerson(double driveY, double driveX, double turn) {
        double max;

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double leftFrontPower = driveY + driveX + turn;
        double rightFrontPower = driveY - driveX - turn;
        double leftBackPower = driveY - driveX + turn;
        double rightBackPower = driveY + driveX - turn;

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        // Send calculated power to wheels
        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
        telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
    }
}
