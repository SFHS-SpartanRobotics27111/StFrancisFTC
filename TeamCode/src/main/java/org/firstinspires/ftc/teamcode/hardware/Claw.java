package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {
    private static final String clawName = "claw";
    private final Telemetry telemetry;
    public Servo claw;

    public Claw(LinearOpMode op) {
        telemetry = op.telemetry;

        claw = op.hardwareMap.get(Servo.class, clawName);
    }

    public void moveClaw(boolean clawOpen, boolean clawClose) {
        if (clawOpen) {
            claw.setPosition(0.3);
            telemetry.addData("Claw", "Open");
        } else if (clawClose) {
            claw.setPosition(1.0);
            telemetry.addData("Claw", "Closing");
        }
    }

    // true means open, false mans close.
    // We have no way of blocking while the claw is moving, so this action will complete right
    // away while the servo takes its time to move.
    public Action moveClawAction(boolean open) {
        return new Action() {

            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                telemetry.addLine("attempting to move arm");
                if (open) {
                    claw.setPosition(0.3);
                    telemetry.addData("Claw", "Open");
                } else {
                    claw.setPosition(1.0);
                    telemetry.addData("Claw", "Closing");
                }

                return false;
            }
        };
    }
}
