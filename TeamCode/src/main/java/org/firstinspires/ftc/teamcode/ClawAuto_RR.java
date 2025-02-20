package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Arm;
import org.firstinspires.ftc.teamcode.hardware.ArmAuto;
import org.firstinspires.ftc.teamcode.hardware.Claw;
import org.firstinspires.ftc.teamcode.hardware.ClawAuto;
@Autonomous(name="ODOMETRY: Robot 180 turn test", group="Linear OpMode")
public final class ClawAuto_RR extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 48, -Math.PI / 2);


        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
        Arm arm = new Arm(this);

        Claw claw = new Claw(this);

        waitForStart();

        /* List of Actions to make for this to work:
        * 1. Close claw onto the specimen on init
        * 2. Parallel Action: Raise arm to scoring position at the same time as pulling away from wall and turning toward high chamber
        * 3. Sequential Action: Move forward to clip on specimen then open claw
        * later 4. Arm down to clear specimen and back up, then put the arm vertical when not scoring or picking up a piece
        * */

        Actions.runBlocking(
        new ParallelAction(
                claw.moveClawAction(true),
                arm.moveArm(arm.getARM_SCORE_SPECIMEN())
        )
       );
       sleep(650);

        // required time for claw to run to position 650 milliseconds
        Actions.runBlocking(
                claw.moveClawAction(false)
        );
        sleep(650);
    }
}
