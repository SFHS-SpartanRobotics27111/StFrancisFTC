package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Arm;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.OmniDrive;
import org.firstinspires.ftc.teamcode.hardware.Wrist;

@TeleOp(name = "Robot Centric Omni TeleOp", group = "Robot")
public class RobotTeleopPOV_Linear extends LinearOpMode {
    @Override
    public void runOpMode() {
        // set up motors
        OmniDrive drive = new OmniDrive(hardwareMap, telemetry);
        Arm arm = new Arm(hardwareMap, telemetry);

        // set up servos
        Wrist wrist = new Wrist(hardwareMap, telemetry);
        Intake intake = new Intake(hardwareMap, telemetry);

        telemetry.addData(">", "Setup complete. Press start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // joystick y is negative for forward, so negate it
            drive.driveFirstPerson(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            arm.moveArmWithEncoder(gamepad2.a, gamepad2.b, gamepad2.x, gamepad2.y, gamepad2.dpad_up, gamepad2.dpad_down, gamepad2.right_trigger, gamepad2.left_trigger);
            wrist.moveWrist(gamepad1.right_bumper, gamepad1.left_bumper);
            intake.moveIntake(gamepad1.a, gamepad1.b);

            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}
