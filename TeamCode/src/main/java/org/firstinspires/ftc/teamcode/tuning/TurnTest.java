package org.firstinspires.ftc.teamcode.tuning;

import com.amarcolini.joos.command.CommandOpMode;
import com.amarcolini.joos.command.TimeCommand;
import com.amarcolini.joos.dashboard.JoosConfig;
import com.amarcolini.joos.drive.DriveSignal;
import com.amarcolini.joos.geometry.Angle;
import com.amarcolini.joos.geometry.Pose2d;
import com.amarcolini.joos.profile.MotionProfile;
import com.amarcolini.joos.profile.MotionProfileGenerator;
import com.amarcolini.joos.profile.MotionState;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.SampleRobot;

@TeleOp(group = "Tuning")
@JoosConfig
public class TurnTest extends CommandOpMode {
    @Register
    private SampleRobot robot;
    public static Angle angle = Angle.deg(90);
    public static Angle maxAngVel = Angle.deg(180);
    public static Angle maxAngAccel = Angle.deg(180);

    public void preInit() {
        MotionProfile profile = MotionProfileGenerator.generateSimpleMotionProfile(
                new MotionState(0.0, 0.0, 0.0),
                new MotionState(angle.radians(), 0.0, 0.0),
                maxAngVel.radians(), maxAngAccel.radians()
        );

        new TimeCommand((t, dt) -> {
            MotionState state = profile.get(t);
            robot.drive.setDriveSignal(
                    new DriveSignal(
                            new Pose2d(0.0, 0.0, Angle.rad(state.v)),
                            new Pose2d(0.0, 0.0, Angle.rad(state.a))
                    )
            );
            return t >= profile.duration();
        }).onEnd((interrupted) -> robot.drive.setDriveSignal(new DriveSignal()))
                .thenStopOpMode()
                .schedule();
    }
}