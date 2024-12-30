package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {
    private static final String clawName = "claw";

    public Servo claw;

    private final Telemetry telemetry;

    public Claw(HardwareMap hardwareMap, Telemetry telemetry1) {
        telemetry = telemetry1;

        claw = hardwareMap.get(Servo.class, clawName);
    }

    public void moveClaw(boolean close, boolean open) {
        // need to document which one is in and which one is out
        // also extract magic numbers into final members
       if (close) {
           claw.setPosition(0.69);
           telemetry.addData("Claw", "Closed");
       } else if (open) {
            claw.setPosition(1.25);
            telemetry.addData("Claw", "Open");
        }
    }
}
