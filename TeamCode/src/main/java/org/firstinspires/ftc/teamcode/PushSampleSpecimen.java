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

@Autonomous(name = "Push in sample + score Drive only", group = "Linear OpMode")
public final class PushSampleSpecimen extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(-46, 9, Math.PI / 2);

        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
        Arm arm = new Arm(this);
        Claw claw = new Claw(this);


        waitForStart();

        /* List of Actions to make for this to work:
         * 1. Close claw onto the specimen on init
         * 2. Parallel Action: Raise arm to scoring position at the same time as pulling away from wall and turning toward high chamber X?
         * 3. Sequential Action: Move forward to clip on specimen then open claw X?
         * later 4. Arm down to clear specimen and back up, then put the arm vertical when not scoring or picking up a piece
         * */
        Actions.runBlocking(
                drive.actionBuilder(beginPose)

                        .strafeTo(new Vector2d(-46, 56))
                        .strafeTo(new Vector2d(-46, 31))
                        .splineToLinearHeading(new Pose2d(0, 45, -Math.PI /2), -Math.PI / 2)
                        .splineToLinearHeading(new Pose2d(-46, 30, -Math.PI /2), -Math.PI /2)
                        .splineToLinearHeading(new Pose2d(0, 45, -Math.PI /2), -Math.PI / 2)
                        .strafeTo(new Vector2d(-39, 65))
                        .build()

        );
    }
}
