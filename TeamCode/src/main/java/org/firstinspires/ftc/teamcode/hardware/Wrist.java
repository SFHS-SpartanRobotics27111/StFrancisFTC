package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Wrist {
    private static final String wristName = "wrist_pivot";

    private static final double WRIST_MIDDLE = 0.69;
    private static final double WRIST_RIGHT = 1.25;

    public Servo wrist;
    private final Telemetry telemetry;

    public Wrist(HardwareMap hardwareMap, Telemetry telemetry1) {
        telemetry = telemetry1;

        wrist = hardwareMap.get(Servo.class, wristName);
    }

    public void moveWrist(boolean right, boolean left) {
        if (right) {
            wrist.setPosition(WRIST_RIGHT);
            telemetry.addData("Wrist", "Right");
        } else if (left) {
            wrist.setPosition(WRIST_MIDDLE);
            telemetry.addData("Wrist", "Middle");
        }
    }
}
