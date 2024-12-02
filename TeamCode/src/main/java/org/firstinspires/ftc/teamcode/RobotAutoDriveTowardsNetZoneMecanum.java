package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

// The code assumes that you do NOT have encoders on the wheels,
// otherwise you would use RobotAutoDriveByEncoder;

@Autonomous(name = "Auto: Drive Towards Net Zone Mecanum ", group = "Robot")
public class RobotAutoDriveTowardsNetZoneMecanum extends LinearOpMode {
//get all motors


    public ElapsedTime timer = new ElapsedTime();

    double leftFrontPower  = 0.2;
    double rightFrontPower = 0.2;
    double leftBackPower   = -0.2;
    double rightBackPower  = -0.2;
    static final double TURN_SPEED = 0.5;

    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;

    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "left_front_drive");
        leftBackDrive  = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");

        // To drive forward, one motor must be reversed because the axles point in opposite directions
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData(">", "Setup complete. Press start");
        telemetry.update();

        waitForStart();

        // Drive forward
        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        timer.reset();
        while (opModeIsActive() && (timer.seconds() < 0.5)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", timer.seconds());
            telemetry.update();
        }

        // Drive backward
        leftBackDrive.setPower(rightBackPower);
        rightBackDrive.setPower(rightBackPower);
        timer.reset();
        while (opModeIsActive() && (timer.seconds() < 1.0)) {
            telemetry.addData("Path", "Leg 2: %4.1f S Elapsed", timer.seconds());
            telemetry.update();
        }

        // Stop
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}
