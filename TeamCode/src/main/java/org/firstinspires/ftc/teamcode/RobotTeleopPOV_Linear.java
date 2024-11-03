/*
 * This OpMode is an example driver-controlled (TeleOp) mode for the goBILDA 2024-2025 FTC
 * Into The Deep Starter Robot
 * The code is structured as a LinearOpMode
 *
 * This robot has a two-motor differential-steered (sometimes called tank or skid steer) drivetrain.
 * With a left and right drive motor.
 * The drive on this robot is controlled in an "Arcade" style, with the left stick Y axis
 * controlling the forward movement and the right stick X axis controlling rotation.
 * This allows easy transition to a standard "First Person" control of a
 * mecanum or omnidirectional chassis.
 *
 * The drive wheels are 96mm diameter traction (Rhino) or omni wheels.
 * They are driven by 2x 5203-2402-0019 312RPM Yellow Jacket Planetary Gearmotors.
 *
 * This robot's main scoring mechanism includes an arm powered by a motor, a "wrist" driven
 * by a servo, and an intake driven by a continuous rotation servo.
 *
 * The arm is powered by a 5203-2402-0051 (50.9:1 Yellow Jacket Planetary Gearmotor) with an
 * external 5:1 reduction. This creates a total ~254.47:1 reduction.
 * This OpMode uses the motor's encoder and the RunToPosition method to drive the arm to
 * specific setpoints. These are defined as a number of degrees of rotation away from the arm's
 * starting position.
 *
 * Make super sure that the arm is reset into the robot, and the wrist is folded in before
 * you run start the OpMode. The motor's encoder is "relative" and will move the number of degrees
 * you request it to based on the starting position. So if it starts too high, all the motor
 * setpoints will be wrong.
 *
 * The wrist is powered by a goBILDA Torque Servo (2000-0025-0002).
 *
 * The intake wheels are powered by a goBILDA Speed Servo (2000-0025-0003) in Continuous Rotation mode.
 */


//Need to test tomorrow for wrist movement and intake
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/*
 * This OpMode executes a POV Game style Teleop for a direct drive robot
 * The code is structured as a LinearOpMode
 *
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the arm using the Gamepad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

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

            // Combine drive and turn for blended motion.
            double left = drive + turn;
            double right = drive - turn;

            // Normalize the values so neither exceed +/- 1.0
            double max = Math.max(Math.abs(left), Math.abs(right));
            if (max > 1.0) {
                left /= max;
                right /= max;
            }

            // Output the safe vales to the motor drives.
            leftDrive.setPower(left);
            rightDrive.setPower(right);

            // Use gamepad left & right Bumpers to open and close the claw
            if (gamepad1.right_bumper) {
                wristOffset += WRIST_SPEED;
            }
            else if (gamepad1.left_bumper) {
                wristOffset -= WRIST_SPEED;
            }

            wristOffset = Range.clip(wristOffset, WRIST_MIN, WRIST_MAX);
            wrist.setPosition(WRIST_MIDDLE + wristOffset);

            if (gamepad1.dpad_up)
                armMotor.setPower(ARM_UP);
            else if (gamepad1.dpad_down)
                armMotor.setPower(ARM_DOWN);
            else if (gamepad1.y)
                armMotor.setPower(0.0);

            // need to document which one is in and which one is out
            // also extract magic numbers into final members
            if (gamepad1.a)
                intake.setPower(-1.0);
            else if (gamepad1.b)
                intake.setPower(0.5);
            else if (gamepad1.x)
                intake.setPower(0.0);


            // Send telemetry message to signify robot running;
            telemetry.addData("wrist", "Offset = %.2f", wristOffset);
            telemetry.addData("left", "%.2f", left);
            telemetry.addData("right", "%.2f", right);
            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}
