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

            waitForStart();

            Actions.runBlocking(
                    drive.actionBuilder(beginPose)

                            .lineToX(38)
                            .splineTo(new Vector2d(38, 24), Math.PI / 2)
                            arm.moveArmWithEncoder(ARMC)

                            .build());
        }


        }
