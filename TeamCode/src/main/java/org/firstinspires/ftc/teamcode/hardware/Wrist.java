package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Wrist {
    private static final String wristName = "wrist_pivot";

    private static final double WRIST_MIN = -0.8;
    private static final double WRIST_MAX = 0.8;
    private static final double WRIST_MIDDLE = 0.6667;
    private static final double WRIST_SPEED = 0.02;

    public Servo wrist;

    private double wristOffset = 0;

    private final Telemetry telemetry;

    public Wrist(HardwareMap hardwareMap, Telemetry telemetry1) {
        telemetry = telemetry1;

        wrist = hardwareMap.get(Servo.class, wristName);
    }

    public void moveWrist(boolean right, boolean left) {
        if (right) {
            wristOffset += WRIST_SPEED;
        } else if (left) {
            wristOffset -= WRIST_SPEED;
        }

        wristOffset = Range.clip(wristOffset, WRIST_MIN, WRIST_MAX);
        wrist.setPosition(WRIST_MIDDLE + wristOffset);

        telemetry.addData("Wrist", "Offset = %.2f", wristOffset);
    }
}
