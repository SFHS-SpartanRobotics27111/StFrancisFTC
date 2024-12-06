package org.firstinspires.ftc.teamcode.tuning.util;

import androidx.annotation.Nullable;
import com.amarcolini.joos.drive.Drive;
import com.amarcolini.joos.geometry.Angle;
import com.amarcolini.joos.geometry.Pose2d;
import com.amarcolini.joos.hardware.Motor;
import com.amarcolini.joos.hardware.drive.*;
import com.amarcolini.joos.localization.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.function.Function;

public class TuningData {
    public final Drive drive;
    public final @Nullable DoubleSupplier forwardTicks;
    public final @Nullable DoubleSupplier lateralTicks;
    public final List<EncoderData> encoders;

    public TuningData(DriveComponent drive) {
        this((Drive) ComponentDrive.from(drive));
    }

    public TuningData(Drive drive) {
        this.drive = drive;
        Localizer localizer = drive.getLocalizer();

        if (drive instanceof MecanumDrive && localizer instanceof MecanumLocalizer) {
            MecanumDrive mecanumDrive = (MecanumDrive) drive;
            forwardTicks = () -> {
                List<Integer> wheelPositions = mecanumDrive.getMotors().getPositions();
                return (wheelPositions.get(0) + wheelPositions.get(1) + wheelPositions.get(2) + wheelPositions.get(3)) / 4.0;
            };
            lateralTicks = () -> {
                List<Integer> wheelPositions = mecanumDrive.getMotors().getPositions();
                return (-wheelPositions.get(0) + wheelPositions.get(1) - wheelPositions.get(2) + wheelPositions.get(3)) / 4.0;
            };
            encoders = Arrays.asList(
                    new EncoderData("leftWheels", Angle.rad(0), () -> {
                        List<Integer> wheelPositions = mecanumDrive.getMotors().getPositions();
                        return (wheelPositions.get(0) + wheelPositions.get(1)) / 2.0;
                    }),
                    new EncoderData("rightWheels", Angle.rad(0), () -> {
                        List<Integer> wheelPositions = mecanumDrive.getMotors().getPositions();
                        return (wheelPositions.get(2) + wheelPositions.get(3)) / 2.0;
                    })
            );
        } else if (drive instanceof TankDrive && localizer instanceof TankLocalizer) {
            TankDrive tankDrive = (TankDrive) drive;
            forwardTicks = () -> tankDrive.getMotors().getCurrentPosition();
            lateralTicks = null;
            encoders = Arrays.asList(
                    new EncoderData("leftWheels",
                            Angle.rad(0),
                            tankDrive.leftMotors::getCurrentPosition
                    ),
                    new EncoderData("rightWheels",
                            Angle.rad(0),
                            tankDrive.rightMotors::getCurrentPosition
                    )
            );
        } else if (localizer instanceof Standard3WheelLocalizer) {
            Standard3WheelLocalizer trackingWheelLocalizer = (Standard3WheelLocalizer) localizer;
            List<Pose2d> wheelPoses = trackingWheelLocalizer.wheelPoses;
            String[] names;
            if (wheelPoses.get(0).heading.epsilonEquals(
                    Angle.deg(0)
            ) && wheelPoses.get(1).heading.epsilonEquals(Angle.deg(0)) &&
                    wheelPoses.get(2).heading.epsilonEquals(Angle.deg(90))) {
                names = new String[]{"leftEnc", "rightEnc", "perpEnc"};
            } else names = new String[]{"enc0", "enc1", "enc2"};
            //TODO adjust tracking wheel localizers to actually get encoder values
            forwardTicks = () -> mapTicks(trackingWheelLocalizer.encoders,
                    wheelPoses, (p) -> p.heading.cos());
            lateralTicks = () -> mapTicks(trackingWheelLocalizer.encoders,
                    wheelPoses, (p) -> p.heading.sin());
            encoders = Arrays.asList(
                    new EncoderData(names[0], wheelPoses.get(0).heading, () -> trackingWheelLocalizer.encoders.get(0).getPosition()),
                    new EncoderData(names[1], wheelPoses.get(1).heading, () -> trackingWheelLocalizer.encoders.get(1).getPosition()),
                    new EncoderData(names[2], wheelPoses.get(2).heading, () -> trackingWheelLocalizer.encoders.get(2).getPosition())
            );
        } else if (localizer instanceof Standard2WheelLocalizer) {
            Standard2WheelLocalizer trackingWheelLocalizer = (Standard2WheelLocalizer) localizer;
            List<Pose2d> wheelPoses = trackingWheelLocalizer.wheelPoses;
            String[] names;
            if (wheelPoses.get(0).heading.epsilonEquals(
                    Angle.deg(0)
            ) && wheelPoses.get(1).heading.epsilonEquals(Angle.deg(90))) {
                names = new String[]{"parallelEnc", "perpEnc"};
            } else names = new String[]{"enc0", "enc1"};
            forwardTicks = () -> mapTicks(trackingWheelLocalizer.encoders,
                    wheelPoses, (p) -> p.heading.cos());
            lateralTicks = () -> mapTicks(trackingWheelLocalizer.encoders,
                    wheelPoses, (p) -> p.heading.sin());
            encoders = Arrays.asList(
                    new EncoderData(names[0], wheelPoses.get(0).heading, () -> trackingWheelLocalizer.encoders.get(0).getPosition()),
                    new EncoderData(names[1], wheelPoses.get(1).heading, () -> trackingWheelLocalizer.encoders.get(1).getPosition())
            );
        } else {
            forwardTicks = null;
            lateralTicks = null;
            encoders = Collections.emptyList();
        }
    }

    private double mapTicks(
            List<Motor.Encoder> encoders,
            List<Pose2d> wheelPoses,
            Function<Pose2d, Double> weightFunction
    ) {
        double ticks = 0;
        double norm = 0;
        for (int i = 0; i < 3; i++) {
            double mult = weightFunction.apply(wheelPoses.get(i));
            ticks += encoders.get(i).getPosition() * mult;
            norm += mult;
        }
        return ticks / norm;
    }
}