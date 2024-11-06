package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

// The code assumes that you do NOT have encoders on the wheels,
// otherwise you would use RobotAutoDriveByEncoder;

@Autonomous(name = "Auto: Drive Towards Net Zone", group = "Robot")
public class RobotAutoDriveTowardsNetZone extends LinearOpMode {

    private static final String leftDriveName = "left_drive";
    private static final String rightDriveName = "right_drive";

    static final double FORWARD_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;

    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;

    private final ElapsedTime timer = new ElapsedTime();

    private void setUpDriveMotors() {
        leftDrive = hardwareMap.get(DcMotor.class, leftDriveName);
        rightDrive = hardwareMap.get(DcMotor.class, rightDriveName);

        // To drive forward, one motor must be reversed because the axles point in opposite directions
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void runOpMode() {

        setUpDriveMotors();

        telemetry.addData(">", "Setup complete. Press start");
        telemetry.update();

        waitForStart();

        // Drive forward
        leftDrive.setPower(FORWARD_SPEED);
        rightDrive.setPower(FORWARD_SPEED);
        timer.reset();
        while (opModeIsActive() && (timer.seconds() < 0.5)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", timer.seconds());
            telemetry.update();
        }

        // Drive backward
        leftDrive.setPower(-FORWARD_SPEED);
        rightDrive.setPower(-FORWARD_SPEED);
        timer.reset();
        while (opModeIsActive() && (timer.seconds() < 1.0)) {
            telemetry.addData("Path", "Leg 2: %4.1f S Elapsed", timer.seconds());
            telemetry.update();
        }

        // Stop
        leftDrive.setPower(0);
        rightDrive.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}
