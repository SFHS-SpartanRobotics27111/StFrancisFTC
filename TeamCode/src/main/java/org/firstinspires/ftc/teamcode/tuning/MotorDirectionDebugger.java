package org.firstinspires.ftc.teamcode.tuning;

import com.amarcolini.joos.command.CommandOpMode;
import com.amarcolini.joos.gamepad.GamepadEx;
import com.amarcolini.joos.hardware.MotorGroup;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.SampleRobot;
import org.firstinspires.ftc.teamcode.SampleMecanumDrive;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * This OpMode allows you to individually set the power of the first 4 motors of your drivetrain.
 * The ordering of the motors depends on your drivetrain, but {@link SampleMecanumDrive} has them in this order:
 * front left, back left, back right, and front right. These correspond to X, A, B, and Y on the Logitech/Xbox controllers
 * and their PS4/5 equivalents.
 */
@TeleOp(group = "Tuning")
public class MotorDirectionDebugger extends CommandOpMode {
    @Register
    private SampleRobot robot;

    @Override
    public void preInit() {
        MotorGroup motors = robot.drive.getMotors();

        List<Function<GamepadEx, Boolean>> buttonMap = Arrays.asList(
                (p1) -> p1.x0.isActive(),
                (p1) -> p1.a0.isActive(),
                (p1) -> p1.b0.isActive(),
                (p1) -> p1.y0.isActive()
        );

        schedule(true, () -> {
            for (int i = 0; i < motors.size(); i++) {
                double power = buttonMap.get(i).apply(gamepad().p1) ? 1.0 : 0.0;
                motors.get(i).setPower(power);
                telem.addData("motor " + i + " power", power);
            }
        });
    }
}