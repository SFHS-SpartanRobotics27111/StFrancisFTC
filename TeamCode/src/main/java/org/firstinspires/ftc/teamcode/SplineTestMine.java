package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.hardware.Arm;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;
@Autonomous(name="Lazarus", group="Linear OpMode")
public final class SplineTestMine extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(-24, 58, 0);

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

            waitForStart();
        Actions.runBlocking(
                arm.moveArm(ARM_SCORE_SPECIMEN)



        );


            Actions.runBlocking(
                    drive.actionBuilder(beginPose)

                            .lineToX(38)
                            .splineTo(new Vector2d(38, 24), Math.PI / 2)


                            .build());




        }


        }
