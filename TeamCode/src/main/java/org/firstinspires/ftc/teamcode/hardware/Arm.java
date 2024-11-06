package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {
    private static final String armName = "left_arm";

    private static final double ARM_UP = 0.45;
    private static final double ARM_DOWN = -0.45;

    public DcMotor arm;

    private final Telemetry telemetry;

    public Arm(HardwareMap hardwareMap, Telemetry telemetry1) {
        telemetry = telemetry1;
        arm = hardwareMap.get(DcMotor.class, armName);
    }

    public void moveArm(boolean up, boolean down, boolean stop) {
        if (up) {
            arm.setPower(ARM_UP);
            telemetry.addData("Arm", "Moving up");
        } else if (down) {
            arm.setPower(ARM_DOWN);
            telemetry.addData("Arm", "Moving down");
        } else if (stop) {
            arm.setPower(0.0);
            telemetry.addData("Arm", "Stopping");
        }
    }
}
