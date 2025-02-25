package org.firstinspires.ftc.teamcode;

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

        TrajectoryActionBuilder toChamber = drive.actionBuilder(beginPose)
                .strafeToLinearHeading(new Vector2d(0, -42), Math.PI / 2);

        TrajectoryActionBuilder Backup = drive.actionBuilder(new Pose2d(0, -42, Math.PI / 2))
                .lineToY(-65);

        TrajectoryActionBuilder SplineToPush = drive.actionBuilder(new Pose2d(0, -65, Math.PI / 2))
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(48, -13, -Math.PI / 2), -Math.PI / 2);

        Actions.runBlocking(
            claw.moveClawAction(false)
        );

        waitForStart();

        Actions.runBlocking(
            new SequentialAction(
                    arm.moveArm(arm.ARM_SCORE_SPECIMEN + 8),
                    toChamber.build(),
                    claw.moveClawAction(true),
                    new SleepAction(0.3),
                    Backup.build(),
                    new ParallelAction(
                            arm.moveArm(arm.ARM_ATTACH_HANGING_HOOK),
                            SplineToPush.build()
                    )
            )
        );
        sleep(560);
    }
}
