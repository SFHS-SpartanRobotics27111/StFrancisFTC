package org.firstinspires.ftc.teamcode; //teamcode package is necessary for code to function with out it you are missing hella classes and libs

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//import all of our hardware classes. this makes adding new things easier without borking anything else
import org.firstinspires.ftc.teamcode.hardware.Arm;
import org.firstinspires.ftc.teamcode.hardware.Claw;
import org.firstinspires.ftc.teamcode.hardware.OmniDrive;

//TeleOp Parameter is needed to tell the control hub that it should show up in the TeleOp menu and not the Auto menu
@TeleOp(name = "Field Centric Omni TeleOp", group = "Robot")
public class RobotTeleopPOV_Linear extends LinearOpMode {
    @Override
    public void runOpMode() {
        // set up motors
        OmniDrive drive = new OmniDrive(this);
        Arm arm = new Arm(this);

        // set up servos
        Claw claw = new Claw(this);

        telemetry.addData(">", "Setup complete. Press start");
        telemetry.update();

        waitForStart();
        
        //main event loop. while the op mode is active we can take in the following inputs and turn it actions on the Bot
        while (opModeIsActive()) {
            // joystick y is negative for forward, so negate it
            // labels are for Playstation controller but the library also has labels for Xbox controllers if you go back to it.
            drive.driveFirstPerson(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.options);
            arm.moveArmWithEncoder(gamepad2.cross, gamepad2.circle, gamepad2.square, gamepad2.triangle, gamepad2.dpad_up, gamepad2.dpad_down, gamepad2.right_trigger, gamepad2.left_trigger);
            claw.moveClaw(gamepad1.right_bumper, gamepad1.left_bumper);

            telemetry.update();
        }
    }
}
