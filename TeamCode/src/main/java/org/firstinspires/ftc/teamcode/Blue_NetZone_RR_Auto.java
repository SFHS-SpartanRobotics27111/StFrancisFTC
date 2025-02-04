package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.ArmAuto;
import org.firstinspires.ftc.teamcode.hardware.ClawAuto;

@Autonomous(name = "ODOMETRY: Robot Auto Drive 3 Sample Net Zone + LV 1 Ascent", group = "Linear OpMode")
public final class Blue_NetZone_RR_Auto extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(35, 61, Math.PI / 2);
        Pose2d endPose = new Pose2d(31, 9, Math.PI);

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


        Actions.runBlocking(
                drive.actionBuilder(beginPose)

                        .strafeTo(new Vector2d(50, 61))
                        .strafeTo(new Vector2d(35, 61))
                        .strafeTo(new Vector2d(38, 15))
                        .strafeTo(new Vector2d(46, 9))
                        .strafeTo(new Vector2d(46, 56))
                        .strafeTo(new Vector2d(46, 9))
                        .strafeTo(new Vector2d(55, 9))
                        .strafeTo(new Vector2d(55, 56))
                        .strafeTo(new Vector2d(55, 9))
                        .strafeTo(new Vector2d(62, 9))
                        .strafeTo(new Vector2d(62, 56))
                        .strafeTo(new Vector2d(62, 9))
                        .strafeTo(new Vector2d(31, 9))
                        .turn(Math.toRadians(90))
                        .strafeTo(new Vector2d(17, 9))


                        .build());
        Actions.runBlocking(
                arm.moveArm(ARM_CONTACT_BAR_AUTO));


    }


}
