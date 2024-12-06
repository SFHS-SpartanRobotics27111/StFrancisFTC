package org.firstinspires.ftc.teamcode;

import com.amarcolini.joos.control.DCMotorFeedforward;
import com.amarcolini.joos.control.PIDCoefficients;
import com.amarcolini.joos.dashboard.Immutable;
import com.amarcolini.joos.dashboard.JoosConfig;
import com.amarcolini.joos.followers.HolonomicPIDVAFollower;
import com.amarcolini.joos.followers.TrajectoryFollower;
import com.amarcolini.joos.geometry.Angle;
import com.amarcolini.joos.geometry.Pose2d;
import com.amarcolini.joos.hardware.Motor;
import com.amarcolini.joos.hardware.MotorGroup;
import com.amarcolini.joos.hardware.drive.DriveTrajectoryFollower;
import com.amarcolini.joos.hardware.drive.FollowTrajectoryCommand;
import com.amarcolini.joos.hardware.drive.MecanumDrive;
import com.amarcolini.joos.localization.AngleSensor;
import com.amarcolini.joos.trajectory.Trajectory;
import com.amarcolini.joos.trajectory.constraints.MecanumConstraints;
import com.amarcolini.joos.trajectory.constraints.TrajectoryConstraints;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.tuning.util.DashboardTrajectoryCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@JoosConfig
public class SampleMecanumDrive extends MecanumDrive implements DriveTrajectoryFollower {
    @Immutable
    public static MecanumConstraints constraints = new MecanumConstraints(
            40.0,
            18.0,
            18.0, 1.0,
            40.0,
            40.0,
            Angle.deg(180.0),
            Angle.deg(180.0)
    );
    public static final PIDCoefficients axialCoeffs = new PIDCoefficients();
    public static final PIDCoefficients lateralCoeffs = new PIDCoefficients();
    public static final PIDCoefficients headingCoeffs = new PIDCoefficients();
    private final HolonomicPIDVAFollower trajectoryFollower = new HolonomicPIDVAFollower(
            axialCoeffs, lateralCoeffs, headingCoeffs,
            new Pose2d(0.5, 0.5, Angle.deg(5)), 0.5
    );
    public static final DCMotorFeedforward feedforward = new DCMotorFeedforward();
    public static double distancePerTick = 1.0;
    public static double lateralDistancePerTick = 1.0;
    public static double trackWidth = 18.0;

    public SampleMecanumDrive(HardwareMap hMap, AngleSensor headingSensor) {
        super(
                new MotorGroup(
                        new Motor(hMap, "front_left", Motor.Type.GOBILDA_312), //add .reversed() as needed
                        new Motor(hMap, "front_left", Motor.Type.GOBILDA_312),
                        new Motor(hMap, "front_left", Motor.Type.GOBILDA_312),
                        new Motor(hMap, "front_left", Motor.Type.GOBILDA_312)
                ),
                trackWidth, trackWidth, lateralDistancePerTick / distancePerTick, headingSensor
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