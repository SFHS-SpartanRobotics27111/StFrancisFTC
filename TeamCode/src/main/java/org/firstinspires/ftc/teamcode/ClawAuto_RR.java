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

@Autonomous(name = "1 Specimen on High Chamber", group = "Linear OpMode")
public final class ClawAuto_RR extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 48, Math.PI / 2);

        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
        Arm arm = new Arm(this);
        Claw claw = new Claw(this);

        TrajectoryActionBuilder toChamber = drive.actionBuilder(beginPose)
                        .splineTo(new Vector2d(36, 48), 0);

        TrajectoryActionBuilder Score = drive.actionBuilder(drive.pinpoint.getPositionRR())
                .splineTo(new Vector2d(40, 48), 0);

        claw.moveClawAction(false);

        waitForStart();

        /* List of Actions to make for this to work:
         * 1. Close claw onto the specimen on init
         * 2. Parallel Action: Raise arm to scoring position at the same time as pulling away from wall and turning toward high chamber X?
         * 3. Sequential Action: Move forward to clip on specimen then open claw X?
         * later 4. Arm down to clear specimen and back up, then put the arm vertical when not scoring or picking up a piece
         * */
         
        Actions.runBlocking(
            new ParallelAction(
                arm.moveArm(arm.getARM_SCORE_SPECIMEN()),
                    toChamber.build()
            )
        );
        sleep(650);
        
        Actions.runBlocking(
            new SequentialAction(
                    Score.build(),
                    claw.moveClawAction(true),
                arm.moveArm(arm.getARM_ATTACH_HANGING_HOOK())
            )
        );
        sleep(650);
    }
}
