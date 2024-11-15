package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class Arm {
    private static final String armName = "left_arm";

    final double ARM_TICKS_PER_DEGREE =
            28
                * 250047.0 / 4913.0
                * 100.0 / 20.0
                * 1 / 360.0;

    final double ARM_COLLAPSED_IN = 0;
    double armPosition = (int)ARM_COLLAPSED_IN;
    private static final double ARM_UP = 0.45;
    private static final double ARM_DOWN = -0.45;

    public DcMotor arm;

    private final Telemetry telemetry;

    public Arm(HardwareMap hardwareMap, Telemetry telemetry1) {
        telemetry = telemetry1;
        arm = hardwareMap.get(DcMotor.class, armName);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ((DcMotorEx) arm).setCurrentAlert(5, CurrentUnit.AMPS);

        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /*public void moveArm(boolean up, boolean down, boolean stop) {
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
    }*/

    public void moveArmWithEncoder(boolean up, boolean down) {
        if (up) {
            armPosition += 1 * ARM_TICKS_PER_DEGREE;
            telemetry.addData("Arm position: ", arm.getCurrentPosition());
            telemetry.update();
        } else if (down) {
            armPosition -= 1 * ARM_TICKS_PER_DEGREE;
            telemetry.addData("Arm position: ", arm.getCurrentPosition());
            telemetry.update();
        }

        if (((DcMotorEx) arm).isOverCurrent()) {
            telemetry.addLine("MOTOR EXCEEDED CURRENT LIMIT!!");
            telemetry.update();
        }
    }
}
/* Positions to add:
grabbing sample
getting into the submersible
folded in
scoring sample
scoring specimen
 */
