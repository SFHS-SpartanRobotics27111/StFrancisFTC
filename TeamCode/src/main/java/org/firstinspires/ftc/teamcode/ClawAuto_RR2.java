package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Arm;
import org.firstinspires.ftc.teamcode.hardware.Claw;
import org.firstinspires.ftc.teamcode.hardware.PinpointDrive;

@Autonomous(name = "2 Specimen on High Chamber: Default", group = "Linear OpMode")
public final class ClawAuto_RR2 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(10, -65, -Math.PI);

        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
        Arm arm = new Arm(this);
        Claw claw = new Claw(this);

        Action toChamber = drive
                .actionBuilder(beginPose)
                .strafeToLinearHeading(new Vector2d(0, -42), Math.toRadians(90))
                .build();

        Action backup = drive
                .actionBuilder(new Pose2d(0, -42, Math.PI / 2))
                .lineToY(-65)
                .build();

        Action splineToPush = drive
                .actionBuilder(new Pose2d(0, -65, Math.PI / 2))
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(48, -13, -Math.PI / 2), -Math.PI / 2)
                .strafeTo(new Vector2d(43, -13))
                .build();

        Action pushCycle1 = drive
                .actionBuilder(new Pose2d(43, -13, -Math.PI / 2))
                .strafeTo(new Vector2d(43, -56))
                .lineToY(-50.275)
                .build();

        Action splineToScore2 = drive
                .actionBuilder(new Pose2d(43, -50, -Math.PI / 2))
                .setTangent(1)
                .splineToLinearHeading(new Pose2d(0, -50, Math.PI / 2), Math.PI / 2)
                .strafeTo(new Vector2d(0, -42))
                .build();

        Action splineToPushLast = drive
                .actionBuilder(new Pose2d(0, -42, Math.PI / 2))
                .lineToY(-60)
                .splineToLinearHeading(new Pose2d(54, -25, -Math.PI / 2), -Math.PI / 2)
                .build();

        Action goHome = drive
                .actionBuilder(new Pose2d(54, -25, -Math.PI / 2))
                .strafeTo(new Vector2d(54, -58))
                .turn(Math.toRadians(175))
                .build();

        Actions.runBlocking(
                claw.moveClawAction(false)
        );

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        arm.moveArm(arm.ARM_SCORE_SPECIMEN + 8),
                        toChamber,
                        claw.moveClawAction(true),
                        new SleepAction(0.3),
                        backup,
                        new ParallelAction(
                                arm.moveArm(arm.ARM_ATTACH_HANGING_HOOK),
                                splineToPush
                        ),
                        pushCycle1,
                        arm.moveArm(arm.ARM_CLEAR_BARRIER),
                        claw.moveClawAction(false),
                        new SleepAction(0.5),
                        new ParallelAction(
                                arm.moveArm(arm.ARM_SCORE_SPECIMEN + 8),
                                splineToScore2
                        ),
                        claw.moveClawAction(true),
                        new SleepAction(0.3),
                        splineToPushLast,
                        new ParallelAction(
                        arm.moveArm(arm.ARM_COLLAPSED_IN),
                        claw.moveClawAction(false),
                                goHome)
                )
        );
    }
}
