/*package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {
    private static final String clawName = "claw";
    public CRServo claw;

    private final Telemetry telemetry;

    public Claw(HardwareMap hardwareMap, Telemetry telemetry1) {
        telemetry = telemetry1;

        claw = hardwareMap.get(CRServo.class, clawName);
    }

    public void moveClaw(boolean open, boolean close) {
        // need to document which one is in and which one is out
        // also extract magic numbers into final members
       if (open) {
           claw.setPower(0.07);
           telemetry.addData("Claw", "Open");
       } else if (close) {
           claw.setPower(-0.07);
           telemetry.addData("Claw", "Closing");
       } else {
           claw.setPower(0);
       }
    }
}
