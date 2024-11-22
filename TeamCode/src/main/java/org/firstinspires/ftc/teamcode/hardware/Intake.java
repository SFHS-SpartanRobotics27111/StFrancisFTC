package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
    private static final String intakeName = "intake";

    public CRServo intake;

    private final Telemetry telemetry;

    public Intake(HardwareMap hardwareMap, Telemetry telemetry1) {
        telemetry = telemetry1;

        intake = hardwareMap.get(CRServo.class, intakeName);
    }

    public void moveIntake(boolean in, boolean out) {
        // need to document which one is in and which one is out
        // also extract magic numbers into final members
        if (out) {
            intake.setPower(-1.0);
            telemetry.addData("Intake", "-1.0");
        } else if (in) {
            intake.setPower(0.5);
            telemetry.addData("Intake", "0.5");
        } else {
            intake.setPower(0.0);
            telemetry.addData("Intake", "0");
        }
    }
}
