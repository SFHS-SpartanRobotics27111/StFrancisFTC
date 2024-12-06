package org.firstinspires.ftc.teamcode;

import com.amarcolini.joos.control.DCMotorFeedforward;
import com.amarcolini.joos.dashboard.Immutable;
import com.amarcolini.joos.dashboard.JoosConfig;
import com.amarcolini.joos.followers.RamseteFollower;
import com.amarcolini.joos.followers.TrajectoryFollower;
import com.amarcolini.joos.geometry.Angle;
import com.amarcolini.joos.geometry.Pose2d;
import com.amarcolini.joos.hardware.Motor;
import com.amarcolini.joos.hardware.MotorGroup;
import com.amarcolini.joos.hardware.drive.DriveTrajectoryFollower;
import com.amarcolini.joos.hardware.drive.FollowTrajectoryCommand;
import com.amarcolini.joos.hardware.drive.TankDrive;
import com.amarcolini.joos.localization.AngleSensor;
import com.amarcolini.joos.trajectory.Trajectory;
import com.amarcolini.joos.trajectory.constraints.TankConstraints;
import com.amarcolini.joos.trajectory.constraints.TrajectoryConstraints;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.tuning.util.DashboardTrajectoryCommand;
import org.jetbrains.annotations.NotNull;

@JoosConfig
public class SampleTankDrive extends TankDrive implements DriveTrajectoryFollower {
    public static double trackWidth = 18.0;
    @Immutable
    public static TankConstraints constraints = new TankConstraints(
            40.0,
            18.0,
            40.0,
            40.0,
            Angle.deg(180.0),
            Angle.deg(180.0)
    );
    public static double b = 0.051;
    public static double zeta = 0.018;
    private final RamseteFollower trajectoryFollower = new RamseteFollower(
            b, zeta,
            new Pose2d(0.5, 0.5, Angle.deg(5)), 0.5
    );
    public static final DCMotorFeedforward feedforward = new DCMotorFeedforward();
    public static double distancePerTick = 1.0;

    public SampleTankDrive(HardwareMap hMap, AngleSensor headingSensor) {
        super(
                new MotorGroup(
                        new Motor(hMap, "front_left", Motor.Type.GOBILDA_312), //add .reversed() as needed
                        new Motor(hMap, "back_left", Motor.Type.GOBILDA_312)
                ),
                new MotorGroup(
                        new Motor(hMap, "back_right", Motor.Type.GOBILDA_312),
                        new Motor(hMap, "front_right", Motor.Type.GOBILDA_312)
                ),
                trackWidth, headingSensor
        );
        getMotors().setDistancePerTick(distancePerTick);
        getMotors().setFeedforward(feedforward);
    }

    @NotNull
    @Override
    public TrajectoryConstraints getConstraints() {
        return constraints;
    }

    @NotNull
    @Override
    public TrajectoryFollower getTrajectoryFollower() {
        return trajectoryFollower;
    }

    @NotNull
    @Override
    public FollowTrajectoryCommand followTrajectory(@NotNull Trajectory trajectory) {
        return new DashboardTrajectoryCommand(trajectory, trajectoryFollower, this);
    }
}