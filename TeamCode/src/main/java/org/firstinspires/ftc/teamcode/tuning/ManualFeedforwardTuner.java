package org.firstinspires.ftc.teamcode.tuning;

import com.amarcolini.joos.command.*;
import com.amarcolini.joos.dashboard.JoosConfig;
import com.amarcolini.joos.drive.DriveSignal;
import com.amarcolini.joos.geometry.Angle;
import com.amarcolini.joos.geometry.Pose2d;
import com.amarcolini.joos.geometry.Vector2d;
import com.amarcolini.joos.profile.MotionProfile;
import com.amarcolini.joos.profile.MotionProfileGenerator;
import com.amarcolini.joos.profile.MotionState;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.SampleRobot;

@TeleOp(group = "Tuning")
@JoosConfig
public class ManualFeedforwardTuner extends CommandOpMode {
    @Register
    private SampleRobot robot;
    public static double distance = 48.0;
    public static double maxVel = 40.0;
    public static double maxAccel = 40.0;

    private double targetVelocity = 0.0;

    @Override
    public void preInit() {
        MotionProfile forward = MotionProfileGenerator.generateSimpleMotionProfile(
                new MotionState(0.0, 0.0),
                new MotionState(distance, 0.0),
                maxVel, maxAccel
        );
        MotionProfile back = forward.flipped();

        targetVelocity = 0.0;
        Command tuningCommand = new SequentialCommand(
                true,
                new TimeCommand((t, dt) -> {
                    MotionState state = forward.get(t);
                    targetVelocity = state.v;
                    robot.drive.setDriveSignal(new DriveSignal(new Pose2d(state.v), new Pose2d(state.a)));
                    return t >= forward.duration();
                }).onEnd((interrupted) -> robot.drive.setDriveSignal(new DriveSignal())),
                new WaitCommand(0.5),
                new TimeCommand((t, dt) -> {
                    MotionState state = back.get(t);
                    targetVelocity = state.v;
                    robot.drive.setDriveSignal(new DriveSignal(new Pose2d(state.v), new Pose2d(state.a)));
                    return t >= back.duration();
                }).onEnd((interrupted) -> robot.drive.setDriveSignal(new DriveSignal())),
                new WaitCommand(0.5)
        ).repeatForever().requires(robot.drive);

        Command resettingCommand = Command.of(() -> {
            Vector2d leftStick = gamepad().p1.getLeftStick();
            Vector2d rightStick = gamepad().p1.getRightStick();
            robot.drive.setDrivePower(
                    new Pose2d(
                            -leftStick.y,
                            -leftStick.x,
                            Angle.rad(-rightStick.x)
                    )
            );
        }).runForever().requires(robot.drive);

        Command loggingCommand = new InstantCommand(() -> {
            Pose2d actualVelocity = robot.drive.getLocalizer().getPoseVelocity();
            if (actualVelocity == null) return;
            telem.addData("targetVelocity", targetVelocity);
            telem.addData("actualVelocity", actualVelocity.x);
        }).repeatForever();
        loggingCommand.runBlocking();

        schedule(tuningCommand, loggingCommand);
        map(gamepad().p1.y0::isJustActivated, resettingCommand);
        map(gamepad().p1.b0::isJustActivated, tuningCommand);
    }
}