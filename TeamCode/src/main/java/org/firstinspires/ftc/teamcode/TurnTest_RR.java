package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.hardware.ArmAuto;
import org.firstinspires.ftc.teamcode.hardware.ClawAuto;
@Autonomous(name="ODOMETRY: Robot 180 turn test", group="Linear OpMode")
public final class TurnTest_RR extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 48, -Math.PI / 2);


        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
        ArmAuto arm = new ArmAuto(hardwareMap, telemetry);
        final double ARM_TICKS_PER_DEGREE =
                28
                        * 250047.0 / 4913.0
                        * 100.0 / 20.0
                        * 1 / 360.0;
        final double ARM_COLLAPSED_IN = 0;

        final double ARM_COLLECT = 256 * ARM_TICKS_PER_DEGREE; //needs to change
        final double ARM_CLEAR_BARRIER = 221 * ARM_TICKS_PER_DEGREE;
        final double ARM_SCORE_SPECIMEN = 176 * ARM_TICKS_PER_DEGREE; //might need to tweak
        final double ARM_ATTACH_HANGING_HOOK = 119 * ARM_TICKS_PER_DEGREE;
        final double ARM_CONTACT_BAR_AUTO = 180 * ARM_TICKS_PER_DEGREE;
        final double ARM_WINCH_ROBOT = 14 * ARM_TICKS_PER_DEGREE;
        final double FUDGE_FACTOR = 20 * ARM_TICKS_PER_DEGREE;
        ClawAuto claw = new ClawAuto(hardwareMap, telemetry);

        waitForStart();
        /*Actions.runBlocking(
                claw.OpenClaw());*/







        Actions.runBlocking(
                drive.actionBuilder(beginPose)

                        .turn(Math.PI) //use radians, can also use Math.toRadians function


                        .build());

        /*Actions.runBlocking(
                arm.moveArm(ARM_SCORE_SPECIMEN));;
        Actions.runBlocking(
                claw.OpenClaw());
        Actions.runBlocking(
                drive.actionBuilder(beginPose)

                        .strafeTo(new Vector2d(0, 70))
                        .build());

         */












    ;}


}
