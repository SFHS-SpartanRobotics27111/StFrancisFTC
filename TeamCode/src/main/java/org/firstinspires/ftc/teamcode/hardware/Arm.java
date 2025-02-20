package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

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
    final double ARM_COLLECT = 256 * ARM_TICKS_PER_DEGREE;
    final double ARM_CLEAR_BARRIER = 221 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SPECIMEN = 176 * ARM_TICKS_PER_DEGREE;
    final double ARM_ATTACH_HANGING_HOOK = 119 * ARM_TICKS_PER_DEGREE;
    final double ARM_WINCH_ROBOT = 14 * ARM_TICKS_PER_DEGREE;
    final double FUDGE_FACTOR = 20 * ARM_TICKS_PER_DEGREE;
    private final Telemetry telemetry;
    public DcMotor arm;
    double armPosition = (int) ARM_COLLAPSED_IN;
    double armPositionFudgeFactor;

    public Arm(LinearOpMode op) {
        telemetry = op.telemetry;
        arm = op.hardwareMap.get(DcMotor.class, armName);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ((DcMotorEx) arm).setCurrentAlert(5, CurrentUnit.AMPS);

        arm.setTargetPosition((int) ARM_COLLAPSED_IN);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void moveArmWithEncoder(boolean cross, boolean circle, boolean square, boolean triangle, boolean up, boolean down, double r2, double l2) {
        if (cross) {
            armPosition = ARM_COLLECT;
            telemetry.addData("Arm position: ", arm.getCurrentPosition());
        } else if (circle) {
            armPosition = ARM_CLEAR_BARRIER;
            telemetry.addData("Arm position: ", arm.getCurrentPosition());
        } else if (square) {
            armPosition = ARM_SCORE_SPECIMEN;
            telemetry.addData("Arm position: ", arm.getCurrentPosition());
        } else if (triangle) {
            armPosition = ARM_COLLAPSED_IN;
            telemetry.addData("Arm position: ", arm.getCurrentPosition());
        } else if (up) {
            armPosition = ARM_ATTACH_HANGING_HOOK;
            telemetry.addData("Arm position: ", arm.getCurrentPosition());
        } else if (down) {
            armPosition = ARM_WINCH_ROBOT;
            telemetry.addData("Arm position: ", arm.getCurrentPosition());
        }

        armPositionFudgeFactor = FUDGE_FACTOR * (r2 + (-l2));

        arm.setTargetPosition((int) (armPosition + armPositionFudgeFactor));

        ((DcMotorEx) arm).setVelocity(2100);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (((DcMotorEx) arm).isOverCurrent()) {
            telemetry.addLine("MOTOR EXCEEDED CURRENT LIMIT!!");
        }
    }

    public Action moveArm(double Arm_position) {
        return new Action() {

            //@Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                telemetry.addLine("attempting to move arm");
                arm.setTargetPosition((int) (Arm_position));

                ((DcMotorEx) arm).setVelocity(2100);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if (((DcMotorEx) arm).isOverCurrent()) {
                    telemetry.addLine("MOTOR EXCEEDED CURRENT LIMIT!!");
                }
                return arm.isBusy();
            }
        };
    }
}

