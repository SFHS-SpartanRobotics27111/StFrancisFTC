package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
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
}
