package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
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
        );// JOHNNY SCHWAN ASSIGNMENT - DELETE OLD AUTO FILES - RENAME USAGES OF CLAW + ARM - WORK IN NEW BRANCH - MAKE PR
        sleep(650);







//        Actions.runBlocking(
//                drive.actionBuilder(beginPose)
//
//                        .turn(Math.PI) //use radians, can also use Math.toRadians function
//
//
//                        .build());

        /*Actions.runBlocking(
                arm.moveArm(ARM_SCORE_SPECIMEN));;
        Actions.runBlocking(
                claw.OpenClaw());
        Actions.runBlocking(
                drive.actionBuilder(beginPose)

                        .strafeTo(new Vector2d(0, 70))
                        .build());

         */












    }


}
