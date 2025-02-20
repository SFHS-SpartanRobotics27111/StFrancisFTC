package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "ODOMETRY: Robot Auto Drive 3 Sample Net Zone + LV 1 Ascent", group = "Linear OpMode")
public final class Blue_NetZone_RR_Auto extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(35, 61, Math.PI / 2);

        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);

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
                        .build()
        );
    }
}
