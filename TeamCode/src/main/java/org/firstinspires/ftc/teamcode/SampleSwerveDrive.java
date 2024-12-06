package org.firstinspires.ftc.teamcode;

import com.amarcolini.joos.control.DCMotorFeedforward;
import com.amarcolini.joos.control.PIDCoefficients;
import com.amarcolini.joos.dashboard.Immutable;
import com.amarcolini.joos.dashboard.JoosConfig;
import com.amarcolini.joos.drive.AbstractSwerveDrive;
import com.amarcolini.joos.drive.SwerveModule;
import com.amarcolini.joos.followers.HolonomicPIDVAFollower;
import com.amarcolini.joos.followers.TrajectoryFollower;
import com.amarcolini.joos.geometry.Angle;
import com.amarcolini.joos.geometry.Pose2d;
import com.amarcolini.joos.hardware.MotorGroup;
import com.amarcolini.joos.hardware.drive.DriveTrajectoryFollower;
import com.amarcolini.joos.hardware.drive.FollowTrajectoryCommand;
import com.amarcolini.joos.trajectory.Trajectory;
import com.amarcolini.joos.trajectory.constraints.SwerveConstraints;
import com.amarcolini.joos.trajectory.constraints.TrajectoryConstraints;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.tuning.util.DashboardTrajectoryCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JoosConfig
public class SampleSwerveDrive extends AbstractSwerveDrive implements DriveTrajectoryFollower {
    //TODO Update swerve trackwidth/wheelbase to match your drive configuration.
    public static double trackWidth = 12.0;
    public static double wheelBase = 12.0;
    private final List<SampleSwerveModule> modules;
    private final MotorGroup motorGroup;

    @NotNull
    @Override
    public MotorGroup getMotors() {
        return motorGroup;
    }

    @Immutable
    public static SwerveConstraints constraints = new SwerveConstraints(
            40.0,
            18.0,
            18.0,
            40.0,
            40.0,
            Angle.deg(180.0),
            Angle.deg(180.0)
    );

    //TODO Tune swerve module offsets.
    public static Angle frontLeftOffset = Angle.deg(0);
    public static Angle backLeftOffset = Angle.deg(0);
    public static Angle backRightOffset = Angle.deg(0);
    public static Angle frontRightOffset = Angle.deg(0);

    public static final PIDCoefficients axialCoeffs = new PIDCoefficients();
    public static final PIDCoefficients lateralCoeffs = new PIDCoefficients();
    public static final PIDCoefficients headingCoeffs = new PIDCoefficients();
    private final HolonomicPIDVAFollower trajectoryFollower = new HolonomicPIDVAFollower(
            axialCoeffs, lateralCoeffs, headingCoeffs,
            new Pose2d(0.5, 0.5, Angle.deg(5)), 0.5
    );
    public static final DCMotorFeedforward feedforward = new DCMotorFeedforward();
    public static double distancePerTick = 1.0;

    public SampleSwerveDrive(HardwareMap hMap) {
        this(
                new SampleSwerveModule(
                        hMap,
                        "front_left_motor",
                        "front_left_servo",
                        "front_left_angle", frontLeftOffset
                ),
                new SampleSwerveModule(
                        hMap,
                        "back_left_motor",
                        "back_left_servo",
                        "back_left_angle", backLeftOffset
                ),
                new SampleSwerveModule(
                        hMap,
                        "back_right_motor",
                        "back_right_servo",
                        "back_right_angle", backRightOffset
                ),
                new SampleSwerveModule(
                        hMap,
                        "front_right_motor",
                        "front_right_servo",
                        "front_right_angle", frontRightOffset
                )
        );
        //TODO Reverse motors/servos/angle sensors like this:
//        modules.get(0).motor.setReversed(true);
//        modules.get(1).servo.setReversed(true);
//        modules.get(2).moduleOrientationSensor.setReversed(true);
    }

    public SampleSwerveDrive(
            @NotNull SampleSwerveModule frontLeft,
            @NotNull SampleSwerveModule backLeft,
            @NotNull SampleSwerveModule backRight,
            @NotNull SampleSwerveModule frontRight
    ) {
        super(frontLeft, backLeft, backRight, frontRight, trackWidth, wheelBase);
        modules = Arrays.asList(
                frontLeft, backLeft, backRight, frontRight
        );
        motorGroup = new MotorGroup(
                modules.stream().map((i) -> i.motor).collect(Collectors.toList())
        );
        getMotors().setDistancePerTick(distancePerTick);
        getMotors().setFeedforward(feedforward);
    }

    @Override
    public void update() {
        for (SwerveModule module : modules) {
            module.update();
        }
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