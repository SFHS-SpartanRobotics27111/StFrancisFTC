package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
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

        TrajectoryActionBuilder Backup = drive.actionBuilder(drive.pinpoint.getPositionRR())
                .lineToY(-55);

        TrajectoryActionBuilder SplineToPush = drive.actionBuilder(drive.pinpoint.getPositionRR())
                .splineTo(new Vector2d(-46, 9), (3 * Math.PI) / 2);

        Actions.runBlocking(
            claw.moveClawAction(false)
        );

        waitForStart();

        /* List of Actions to make for this to work:
         * 1. Close claw onto the specimen on init
         * 2. Parallel Action: Raise arm to scoring position at the same time as pulling away from wall and turning toward high chamber X?
         * 3. Sequential Action: Move forward to clip on specimen then open claw X?
         * later 4. Arm down to clear specimen and back up, then put the arm vertical when not scoring or picking up a piece
         * */
         
        Actions.runBlocking(
            new SequentialAction(
                arm.moveArm(arm.ARM_SCORE_SPECIMEN + 8),
                    toChamber.build()
            )
        );
        sleep(560);
        
        Actions.runBlocking(
                claw.moveClawAction(true)
        );
        sleep(560);

        Actions.runBlocking(
                Backup.build()
        );
        sleep(560);

        Actions.runBlocking(
                new ParallelAction(
                arm.moveArm(arm.ARM_ATTACH_HANGING_HOOK),
                        SplineToPush.build()
                )
        );
        sleep(560);
    }
}
