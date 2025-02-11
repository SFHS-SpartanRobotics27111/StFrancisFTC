package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ClawAuto {
    private static final String clawName = "claw";
    private final Telemetry telemetry;
    public boolean previousInput = false;
    public boolean clawOpen = false;
    public Servo claw;

    public ClawAuto(HardwareMap hardwareMap, Telemetry telemetry1) {
        telemetry = telemetry1;

        claw = hardwareMap.get(Servo.class, clawName);
    }

    public Action OpenClaw() {
        return new Openclaw();
    }

    public Action closeClaw() {
        return new closeClaw();
    }

    public class Openclaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            // need to document which one is in and which one is out
            // also extract magic numbers into final members
        /*
        if ((previousInput != clawToggle) && clawToggle) {
            clawOpen = !clawOpen;
        }
        */


            claw.setPosition(0.1);

            telemetry.addData("Claw", "Open");


            return false;
        }


    }

    public class closeClaw implements Action {
        //@Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            // need to document which one is in and which one is out
            // also extract magic numbers into final members
        /*
        if ((previousInput != clawToggle) && clawToggle) {
            clawOpen = !clawOpen;
        }
        */
            try {
                claw.setPosition(1.0);
                telemetry.addData("Claw", "Close");
            } catch (Exception e) {
                return false;
            }

            return false;
        }


    }
}



