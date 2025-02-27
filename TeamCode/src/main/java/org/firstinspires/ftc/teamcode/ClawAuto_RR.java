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

@Autonomous(name = "1 Specimen on High Chamber", group = "Linear OpMode")
public final class ClawAuto_RR extends LinearOpMode {

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
                .lineToY(-56)
                .lineToY(-51)
                .build();

        TrajectoryActionBuilder splineToScore2and3 = drive
                .actionBuilder(new Pose2d(43, -51, -Math.PI / 2))
                .splineToLinearHeading(new Pose2d(0, -42, Math.PI / 2), Math.PI / 2);

        Action splineToPickUpLast = drive
                .actionBuilder(new Pose2d(0, -42, Math.PI / 2))
                .lineToY(-55)
                .splineToLinearHeading(new Pose2d(48, -25, -Math.PI / 2), -Math.PI / 2)
                .build();

        Action goHome = drive
                .actionBuilder(new Pose2d(48, -25, -Math.PI / 2))
                .strafeTo(new Vector2d(48, -65))
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
                        new SleepAction(0.45),
                        new ParallelAction(
                                arm.moveArm(arm.ARM_SCORE_SPECIMEN + 8),
                                splineToScore2and3.build()
                        ),
                        claw.moveClawAction(true),
                        new SleepAction(0.3),
                        splineToPickUpLast,
                        new ParallelAction(
                        arm.moveArm(arm.ARM_COLLAPSED_IN),
                        claw.moveClawAction(false),
                                goHome)
                )
        );
        sleep(560);
    }
}
