package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

//OmniDrive might get complicated
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
    //IMU is an acronym for something you will have to google, it handles all the positioning stuff for holonomic driving to work
    public IMU imu;

    //chonky ahh constructor
    public OmniDrive(LinearOpMode op) {
        telemetry = op.telemetry;
        leftFrontDrive = op.hardwareMap.get(DcMotor.class, LEFT_FRONT_DRIVE);
        leftBackDrive = op.hardwareMap.get(DcMotor.class, LEFT_BACK_DRIVE);
        rightFrontDrive = op.hardwareMap.get(DcMotor.class, RIGHT_FRONT_DRIVE);
        rightBackDrive = op.hardwareMap.get(DcMotor.class, RIGHT_BACK_DRIVE);
        imu = op.hardwareMap.get(IMU.class, "imu");

        // To drive forward, one motor must be reversed because the axles point in opposite directions
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //this lets the IMU know all the stuff needed for forward to be forward
        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.LEFT
                )
        );
        imu.initialize(parameters);
    }
    //our field centric drive. it has some simple trig, hope you know what a radian is
    public void driveFirstPerson(double driveY, double driveX, double turn, boolean resetYaw) {
        double max;
        
        //this the rotation of the robot in radians 
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        //calculate the transformation so forward continues to be forward even when turned
        double rotX = driveX * Math.cos(-botHeading) - driveY * Math.sin(-botHeading);
        double rotY = driveX * Math.sin(-botHeading) + driveY * Math.cos(-botHeading);

        //normalizing value for some weird strafing behavior
        rotX *= 1.1;
        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        // Unit circle ahh power delivery IYKYK
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(turn), 1);
        double leftFrontPower = (rotY + rotX + turn) / denominator;
        double rightFrontPower = (rotY - rotX - turn) / denominator;
        double leftBackPower = (rotY - rotX + turn) / denominator;
        double rightBackPower = (rotY + rotX - turn) / denominator;

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

        //if field centric drive gets cooked rotate to a cardinal direction and then reset the yaw
        if (resetYaw) {
            imu.resetYaw();
        }

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
        telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
    }
}
