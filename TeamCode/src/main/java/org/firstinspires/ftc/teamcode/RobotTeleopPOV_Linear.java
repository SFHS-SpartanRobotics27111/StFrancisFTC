package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Arm;
import org.firstinspires.ftc.teamcode.hardware.Drive;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Wrist;

@TeleOp(name = "Main Teleop", group = "Robot")
public class RobotTeleopPOV_Linear extends LinearOpMode {
    @Override
    public void runOpMode() {
        // set up motors
        Drive drive = new Drive(hardwareMap, telemetry);
        Arm arm = new Arm(hardwareMap, telemetry);

        // set up servos
        Wrist wrist = new Wrist(hardwareMap, telemetry);
        Intake intake = new Intake(hardwareMap, telemetry);

        telemetry.addData(">", "Setup complete. Press start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // joystick y is negative for forward, so negate it
            drive.driveFirstPerson(-gamepad1.left_stick_y, gamepad1.right_stick_x);
            arm.moveArmWithEncoder(gamepad1.dpad_up, gamepad1.dpad_down);
            wrist.moveWrist(gamepad1.right_bumper, gamepad1.left_bumper);
            intake.moveIntake(gamepad1.b, gamepad1.a, gamepad1.x);

            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}
