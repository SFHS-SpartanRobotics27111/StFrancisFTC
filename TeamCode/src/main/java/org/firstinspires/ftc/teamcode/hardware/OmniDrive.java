package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
 * TODO:
 *  Figure out if we should have encoders for drive motors
 */

public class Drive {
    private static final String leftDriveName = "left_drive";
    private static final String rightDriveName = "right_drive";

    public DcMotor driveRight;
    public DcMotor driveLeft;

    private final Telemetry telemetry;

    public Drive(HardwareMap hardwareMap, Telemetry telemetry1) {
        telemetry = telemetry1;
        driveLeft = hardwareMap.get(DcMotor.class, leftDriveName);
        driveRight = hardwareMap.get(DcMotor.class, rightDriveName);

        // To drive forward, one motor must be reversed because the axles point in opposite directions
        driveLeft.setDirection(DcMotor.Direction.FORWARD);
        driveRight.setDirection(DcMotor.Direction.REVERSE);

        // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy
        // leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void driveFirstPerson(double drive, double turn) {
        // Combine drive and turn for blended motion.
        double left = drive + turn;
        double right = drive - turn;

        // Normalize the values so neither exceed +/- 1.0
        double max = Math.max(Math.abs(left), Math.abs(right));
        if (max > 1.0) {
            left /= max;
            right /= max;
        }

        driveLeft.setPower(left);
        driveRight.setPower(right);

        telemetry.addData("left", "%.2f", left);
        telemetry.addData("right", "%.2f", right);
    }
}
