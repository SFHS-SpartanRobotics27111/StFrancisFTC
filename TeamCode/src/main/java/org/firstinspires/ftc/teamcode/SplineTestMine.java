package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.hardware.Arm;
import org.firstinspires.ftc.teamcode.hardware.claw_auto;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;
@Autonomous(name="Robot Auto Drive 3 samples blue", group="Linear OpMode")
public final class SplineTestMine extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(-12, 61, Math.PI / 2);

        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
        Arm arm = new Arm(hardwareMap, telemetry);
        final double ARM_TICKS_PER_DEGREE =
                28
                        * 250047.0 / 4913.0
                        * 100.0 / 20.0
                        * 1 / 360.0;
        final double ARM_COLLAPSED_IN = 0;

        final double ARM_COLLECT = 260 * ARM_TICKS_PER_DEGREE; //needs to change
        final double ARM_CLEAR_BARRIER = 230 * ARM_TICKS_PER_DEGREE;
        final double ARM_SCORE_SPECIMEN = 160 * ARM_TICKS_PER_DEGREE; //might need to tweak
        final double ARM_ATTACH_HANGING_HOOK = 123 * ARM_TICKS_PER_DEGREE;
        final double ARM_WINCH_ROBOT = 15 * ARM_TICKS_PER_DEGREE;
        claw_auto claw = new claw_auto(hardwareMap, telemetry);

        waitForStart();
        Actions.runBlocking(
                claw.closeClaw());




        Actions.runBlocking(
                drive.actionBuilder(beginPose)

                        .strafeTo(new Vector2d(-12, 48))
                        .strafeTo(new Vector2d(-35, 48))
                        //claw.openClaw()
                        //.splineTo(new Vector2d(0, 36), -Math.PI / 2)
                        .strafeTo(new Vector2d(-38, 14))
                        .strafeTo(new Vector2d(-46, 6))
                        .strafeTo(new Vector2d(-46, 56))
                        .strafeTo(new Vector2d(-46, 6))
                        .strafeTo(new Vector2d(-55, 6))
                        .strafeTo(new Vector2d(-55, 56))
                        .strafeTo(new Vector2d(-55, 6))
                        .strafeTo(new Vector2d(-61, 6))
                        .strafeTo(new Vector2d(-61, 56))
                        .strafeTo(new Vector2d(-61, 45))
                        .strafeTo(new Vector2d(-54, 45))
                        .splineTo(new Vector2d(-55, 46 ), Math.PI)
                        .strafeTo(new Vector2d(-55, 55))
                //claw.openClaw()

                //.strafeTo(new Vector2d(-47, 1))

                /*.strafeTo(new Vector2d(-47, 55))
                        .waitSeconds(5)
                .strafeTo(new Vector2d(-47, 1))
                        .waitSeconds(5)
                .strafeTo(new Vector2d(-57, 1))
                        .waitSeconds(5)
                .strafeTo(new Vector2d(-57, 55))
                        .waitSeconds(5)
                .strafeTo(new Vector2d(-57, 1))
                        .waitSeconds(5)
                .strafeTo(new Vector2d(-64, 1))
                        .waitSeconds(5)
                .strafeTo(new Vector2d(-64, 55))
                        .waitSeconds(5)
                .strafeTo(new Vector2d(-64, 45))
                        .waitSeconds(5)
                .strafeTo(new Vector2d(-64, 55))*/
                //blocks END


                        .build());




    }


    }
