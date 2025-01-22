package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {
    private static final String clawName = "claw";
    public boolean previousInput = false;
    public boolean clawOpen = false;
    public Servo claw;

    private final Telemetry telemetry;

    public Claw(HardwareMap hardwareMap, Telemetry telemetry1) {
        telemetry = telemetry1;

        claw = hardwareMap.get(Servo.class, clawName);
    }

    public void moveClaw(boolean clawOpen, boolean clawClose) {
        // need to document which one is in and which one is out
        // also extract magic numbers into final members
        /*
        if ((previousInput != clawToggle) && clawToggle) {
            clawOpen = !clawOpen;
        }
        */

       if (clawOpen) {
           claw.setPosition(0.1);
           telemetry.addData("Claw", "Open");
       } else if (clawClose) {
           claw.setPosition(1.0);
           telemetry.addData("Claw", "Closing");
       }
    }
}
