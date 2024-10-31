/*   MIT License
 *   Copyright (c) [2024] [Base 10 Assets, LLC]
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:

 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.

 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name = "Into the Deep Nightly", group = "Robot")
//@Disabled
public class GoBuildaDeep extends LinearOpMode {



    /* This constant is the number of encoder ticks for each degree of rotation of the arm.
    To find this, we first need to consider the total gear reduction powering our arm.
    First, we have an external 20t:100t (5:1) reduction created by two spur gears.
    But we also have an internal gear reduction in our motor.
    The motor we use for this arm is a 117RPM Yellow Jacket. Which has an internal gear
    reduction of ~50.9:1. (more precisely it is 250047/4913:1)
    We can multiply these two ratios together to get our final reduction of ~254.47:1.
    The motor's encoder counts 28 times per rotation. So in total you should see about 7125.16
    counts per rotation of the arm. We divide that by 360 to get the counts per degree. */
    final double ARM_TICKS_PER_DEGREE =
            28 // number of encoder ticks per rotation of the bare motor
                    * 250047.0 / 4913.0 // This is the exact gear ratio of the 50.9:1 Yellow Jacket gearbox
                    * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
                    * 1 / 360.0; // we want ticks per degree, not per rotation


    /* These constants hold the position that the arm is commanded to run to.
    These are relative to where the arm was located when you start the OpMode. So make sure the
    arm is reset to collapsed inside the robot before you start the program.

    In these variables you'll see a number in degrees, multiplied by the ticks per degree of the arm.
    This results in the number of encoder ticks the arm needs to move in order to achieve the ideal
    set position of the arm. For example, the ARM_SCORE_SAMPLE_IN_LOW is set to
    160 * ARM_TICKS_PER_DEGREE. This asks the arm to move 160° from the starting position.
    If you'd like it to move further, increase that number. If you'd like it to not move
    as far from the starting position, decrease it. */

    final double ARM_COLLAPSED_INTO_ROBOT = 0;
    final double ARM_COLLECT = 250 * ARM_TICKS_PER_DEGREE;
    final double ARM_CLEAR_BARRIER = 230 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SPECIMEN = 160 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SAMPLE_IN_LOW = 160 * ARM_TICKS_PER_DEGREE;
    final double ARM_ATTACH_HANGING_HOOK = 120 * ARM_TICKS_PER_DEGREE;
    final double ARM_WINCH_ROBOT = 15 * ARM_TICKS_PER_DEGREE;
    final double ARM_MOVEMENT = 1 * ARM_TICKS_PER_DEGREE;

    /* Variables to store the speed the intake servo should be set at to intake, and deposit game elements. */
    final double INTAKE_COLLECT = -1.0;
    final double INTAKE_OFF = 0.0;
    final double INTAKE_DEPOSIT = 0.5;

    /* Variables to store the positions that the wrist should be set to when folding in, or folding out. */
    final double WRIST_FOLDED_IN = 0.8333;
    final double WRIST_FOLDED_OUT = 0.5;
    final double WRIST_CENTERED = 0.6667;

    /* Variables that are used to set the arm to a specific position */
    double armPosition = (int) ARM_COLLAPSED_INTO_ROBOT;

    // Define motors
    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor armMotor;

    CRServo intake;
    Servo wrist;


    @Override
    public void runOpMode() {
        /* Define and Initialize Motors */
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive"); //the left drivetrain motor
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive"); //the right drivetrain motor
        armMotor = hardwareMap.get(DcMotor.class, "left_arm"); //the arm motor


        /* Most skid-steer/differential drive robots require reversing one motor to drive forward.
        for this robot, we reverse the right motor.*/
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);


        /* Setting zeroPowerBehavior to BRAKE enables a "brake mode". This causes the motor to slow down
        much faster when it is coasting. This creates a much more controllable drivetrain. As the robot
        stops much quicker. */
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /*This sets the maximum current that the control hub will apply to the arm before throwing a flag */
        ((DcMotorEx) armMotor).setCurrentAlert(5, CurrentUnit.AMPS);


        /* Before starting the armMotor. We'll make sure the TargetPosition is set to 0.
        Then we'll set the RunMode to RUN_TO_POSITION. And we'll ask it to stop and reset encoder.
        If you do not have the encoder plugged into this motor, it will not run in this code. */
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        /* Define and initialize servos.*/
        intake = hardwareMap.get(CRServo.class, "intake");
        wrist = hardwareMap.get(Servo.class, "wrist_pivot");

        /* Make sure that the intake is off, and the wrist is folded in. */
        intake.setPower(INTAKE_OFF);
        wrist.setPosition(WRIST_CENTERED);

        /* Send telemetry message to signify robot waiting */
        telemetry.addLine("Robot Ready.");
        telemetry.update();

        /* Wait for the game driver to press play */
        waitForStart();

        /* Run until the driver presses stop */
        while (opModeIsActive()) {
            mainLoop();
        }
    }

    private void mainLoop() {
        /* Set the drive and turn variables to follow the joysticks on the gamepad.
        the joysticks decrease as you push them up. So reverse the Y axis. */
        double forward = -gamepad1.left_stick_y;
        double rotate = gamepad1.right_stick_x;


        /* Here we "mix" the input channels together to find the power to apply to each motor.
        The both motors need to be set to a mix of how much you're retesting the robot move
        forward, and how much you're requesting the robot turn. When you ask the robot to rotate
        the right and left motors need to move in opposite directions. So we will add rotate to
        forward for the left motor, and subtract rotate from forward for the right motor. */

        double left = forward + rotate;
        double right = forward - rotate;

        /* Normalize the values so neither exceed +/- 1.0 */
        double max = Math.max(Math.abs(left), Math.abs(right));
        if (max > 1.0) {
            left /= max;
            right /= max;
        }

        /* Set the motor power to the variables we've mixed and normalized */
        leftDrive.setPower(left);
        rightDrive.setPower(right);


        /* Here we handle the three buttons that have direct control of the intake speed.
        These control the continuous rotation servo that pulls elements into the robot,
        If the user presses A, it sets the intake power to the final variable that
        holds the speed we want to collect at.
        If the user presses B it reveres the servo to spit out the element.
        And if the user doesn't press either set the servo to off
        */

        // probably extract this into a method
        // Theoretically A button should intake while it is held
        double intakeSpeed = 0;
        if (gamepad1.a) {
            while (gamepad1.a) {
                intakeSpeed = INTAKE_COLLECT;
                intake.setPower(intakeSpeed);
            }
        } else if (gamepad1.b) {
            while (gamepad1.b) {
                intakeSpeed = INTAKE_DEPOSIT;

                intake.setPower(intakeSpeed);
            }
        } else {
            intakeSpeed = INTAKE_OFF;
            intake.setPower(intakeSpeed);
          }




            /* Here we implement a set of if else statements to set our arm to different scoring positions.
            We check to see if a specific button is pressed, and then move the arm (and sometimes
            intake and wrist) to match. For example, if we click the right bumper we want the robot
            to start collecting. So it moves the armPosition to the ARM_COLLECT position,
            it folds out the wrist to make sure it is in the correct orientation to intake, and it
            turns the intake on to the COLLECT mode.*/

        //The number correlating to the Wrist Position is an arbitrary one. 0-1 is a range of 270 degrees
        double Wrist_Position = WRIST_CENTERED;
        if (gamepad1.left_bumper) {
                   Wrist_Position -= 0.1;
                   wrist.setPosition(Wrist_Position);
        } else if (gamepad1.right_bumper) {
                    Wrist_Position += 0.1;
                    wrist.setPosition(Wrist_Position);
        }

        //This code probably won't work cause of the code below it.
        if (gamepad1.dpad_up) {
            while (gamepad1.dpad_up) {
                armPosition += ARM_MOVEMENT;
            }
        } else if (gamepad1.dpad_down) {
            while (gamepad1.dpad_down) {
                armPosition -= ARM_MOVEMENT;
            }
        }

        /* Here we set the target position of our arm to match the variable that was selected
        by the driver.
        We also set the target velocity (speed) the motor runs at, and use setMode to run it.*/


        ((DcMotorEx) armMotor).setVelocity(2100);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        /* TECH TIP: Encoders, integers, and doubles
        Encoders report when the motor has moved a specified angle. They send out pulses which
        only occur at specific intervals (see our ARM_TICKS_PER_DEGREE). This means that the
        position our arm is currently at can be expressed as a whole number of encoder "ticks".
        The encoder will never report a partial number of ticks. So we can store the position in
        an integer (or int).
        A lot of the variables we use in FTC are doubles. These can capture fractions of whole
        numbers. Which is great when we want our arm to move to 122.5°, or we want to set our
        servo power to 0.5.

        setTargetPosition is expecting a number of encoder ticks to drive to. Since encoder
        ticks are always whole numbers, it expects an int. But we want to think about our
        arm position in degrees. And we'd like to be able to set it to fractions of a degree.
        So we make our arm positions Doubles. This allows us to precisely multiply together
        armPosition and our armPositionFudgeFactor. But once we're done multiplying these
        variables. We can decide which exact encoder tick we want our motor to go to. We do
        this by "typecasting" our double, into an int. This takes our fractional double and
        rounds it to the nearest whole number.
        */

        /* Check to see if our arm is over the current limit, and report via telemetry. */
        if (((DcMotorEx) armMotor).isOverCurrent()) {
            telemetry.addLine("MOTOR EXCEEDED CURRENT LIMIT!");
        }


        /* send telemetry to the driver of the arm's current position and target position */
        telemetry.addData("armTarget: ", armMotor.getTargetPosition());
        telemetry.addData("arm Encoder: ", armMotor.getCurrentPosition());
        telemetry.update();

    }
}