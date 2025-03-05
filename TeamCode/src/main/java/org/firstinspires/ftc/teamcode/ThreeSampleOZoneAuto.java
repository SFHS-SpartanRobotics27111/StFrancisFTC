package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.PinpointDrive;

@Autonomous(name = "ODOMETRY Robot Auto Drive 3 samples Observation Zone", group = "Linear OpMode")
public final class ThreeSampleOZoneAuto extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(-12, 61, Math.PI / 2);

        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);

        waitForStart();


        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .strafeTo(new Vector2d(-12, 48))
                        .strafeTo(new Vector2d(-35, 48))
                        .strafeTo(new Vector2d(-38, 14))
                        .strafeTo(new Vector2d(-46, 9))
                        .strafeTo(new Vector2d(-46, 56))
                        .strafeTo(new Vector2d(-46, 9))
                        .strafeTo(new Vector2d(-55, 9))
                        .strafeTo(new Vector2d(-55, 56))
                        .strafeTo(new Vector2d(-55, 9))
                        .strafeTo(new Vector2d(-62, 9))
                        .strafeTo(new Vector2d(-62, 56))
                        .strafeTo(new Vector2d(-62, 45))
                        .strafeTo(new Vector2d(-54, 45))
                        .turn(Math.toRadians(180))
                        .strafeTo(new Vector2d(-55, 55))
                        .build()
        );
    }
}