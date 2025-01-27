package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.acmerobotics.roadrunner.ftc.Actions;

public class claw_auto {
    private static final String clawName = "claw";
    public boolean previousInput = false;
    public boolean clawOpen = false;
    public Servo claw;

    private final Telemetry telemetry;

    public claw_auto(HardwareMap hardwareMap, Telemetry telemetry1) {
        telemetry = telemetry1;

        claw = hardwareMap.get(Servo.class, clawName);
    }

    public Action openClaw() {
        return new Action() {

            //@Override
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


        };


    }

    public Action closeClaw() {
        return new Action() {

            //@Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                // need to document which one is in and which one is out
                // also extract magic numbers into final members
        /*
        if ((previousInput != clawToggle) && clawToggle) {
            clawOpen = !clawOpen;
        }
        */

                claw.setPosition(1.0);
                telemetry.addData("Claw", "Close");


                return false;
            }


        };
    }
}