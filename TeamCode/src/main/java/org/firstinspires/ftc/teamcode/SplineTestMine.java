package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;
@Autonomous(name="Lazarus", group="Linear OpMode")
public final class SplineTestMine extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(49, -45, 90);

            PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);

            waitForStart();

            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .splineTo(new Vector2d(46, 40), Math.PI / 2)
                            //.splineTo(new Vector2d(30, 30), Math.PI / 2)
                            .splineTo(new Vector2d(0, 50), Math.PI)
                            .build());
        }


        }
