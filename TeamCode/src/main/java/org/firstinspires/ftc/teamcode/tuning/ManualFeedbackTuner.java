package org.firstinspires.ftc.teamcode.tuning;

import com.amarcolini.joos.command.CommandOpMode;
import com.amarcolini.joos.command.SequentialCommand;
import com.amarcolini.joos.command.WaitCommand;
import com.amarcolini.joos.dashboard.JoosConfig;
import com.amarcolini.joos.geometry.Angle;
import com.amarcolini.joos.geometry.Pose2d;
import com.amarcolini.joos.hardware.drive.DrivePathFollower;
import com.amarcolini.joos.hardware.drive.DriveTrajectoryFollower;
import com.amarcolini.joos.path.Path;
import com.amarcolini.joos.path.PathBuilder;
import com.amarcolini.joos.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.SampleRobot;

@TeleOp
@JoosConfig
public class ManualFeedbackTuner extends CommandOpMode {
    @Register
    private SampleRobot robot;
    public static double distance = 48.0;

    @Override
    public void preInit() {
        robot.drive.getLocalizer().setPoseEstimate(new Pose2d());
        if (robot.drive instanceof DriveTrajectoryFollower) {
            DriveTrajectoryFollower follower = (DriveTrajectoryFollower) robot.drive;
            Trajectory forwardTrajectory = follower.trajectoryBuilder(new Pose2d())
                    .lineToSplineHeading(new Pose2d(distance, 0.0, Angle.deg(180)))
                    .build();
            Trajectory backwardTrajectory = follower.trajectoryBuilder(forwardTrajectory.end())
                    .lineToSplineHeading(new Pose2d())
                    .build();

            new SequentialCommand(
                    false,
                    follower.followTrajectory(forwardTrajectory),
                    new WaitCommand(0.5),
                    follower.followTrajectory(backwardTrajectory),
                    new WaitCommand(0.5)
            ).repeatForever().schedule();
        } else if (robot.drive instanceof DrivePathFollower) {
            DrivePathFollower follower = (DrivePathFollower) robot.drive;
            Path forwardPath = new PathBuilder(new Pose2d())
                    .lineToSplineHeading(new Pose2d(distance, 0.0, Angle.deg(180)))
                    .build();
            Path backwardPath = new PathBuilder(forwardPath.end())
                    .lineToSplineHeading(new Pose2d())
                    .build();

            new SequentialCommand(
                    false,
                    follower.followPath(forwardPath),
                    new WaitCommand(0.5),
                    follower.followPath(backwardPath),
                    new WaitCommand(0.5)
            ).repeatForever().schedule();
        } else throw new IllegalStateException("Robot must have a trajectory/path follower!");
    }
}