package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
//this is the claw file. this serves as a demo of working with servos in the FTCLib
public class Claw {
    //main parts you need for this is first a name for the control hub to look out for, and a Servo object
    private static final String clawName = "claw";
    private final Telemetry telemetry;
    public Servo claw;

    //Constructor makes the Claw that we will interface with. it has its own telemetry and we also do the hardware mapping here declaring it as a servo with the name claw name
    public Claw(LinearOpMode op) {
        telemetry = op.telemetry;
        claw = op.hardwareMap.get(Servo.class, clawName);
    }

    //moveClaw moves the claw, wow'o'wow
    //two buttons one for opening the claw another for closing
    public void moveClaw(boolean clawOpen, boolean clawClose) {
        //right now our claw opens and closes in a fixed 180 degree range
        //setPosition in my best understanding is a percentage of the range you have left
        //example: for clawOpen we setPosition to 0.3 which is about 70% open and 1.0 is 0% open
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
    // Actions are stuff from Roadrunner to make different moves within auto
    // this will most likely change as you switch to a new auto lib
    public Action moveClawAction(boolean open) {
        return new Action() {

            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                telemetry.addLine("attempting to move arm");
                if (open) {
                    claw.setPosition(0.5);
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
