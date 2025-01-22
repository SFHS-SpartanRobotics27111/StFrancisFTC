package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import com.acmerobotics.roadrunner.ftc.Actions;


public class Arm {
    private static final String armName = "left_arm";
    final double ARM_TICKS_PER_DEGREE =
            28
                    * 250047.0 / 4913.0
                    * 100.0 / 20.0
                    * 1 / 360.0;
    final double ARM_COLLAPSED_IN = 0;

    final double ARM_COLLECT = 260 * ARM_TICKS_PER_DEGREE; //needs to change
    final double ARM_CLEAR_BARRIER = 230 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SPECIMEN = 160 * ARM_TICKS_PER_DEGREE; //might need to tweak
    final double ARM_ATTACH_HANGING_HOOK = 123 * ARM_TICKS_PER_DEGREE;
    final double ARM_WINCH_ROBOT = 15 * ARM_TICKS_PER_DEGREE;
    final double FUDGE_FACTOR = 20 * ARM_TICKS_PER_DEGREE;
    private final Telemetry telemetry;
    public DcMotor arm;
    double armPosition = (int) ARM_COLLAPSED_IN;
    double armPositionFudgeFactor;

    public Arm(HardwareMap hardwareMap, Telemetry telemetry1) {
        telemetry = telemetry1;
        arm = hardwareMap.get(DcMotor.class, armName);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ((DcMotorEx) arm).setCurrentAlert(5, CurrentUnit.AMPS);

        arm.setTargetPosition((int) ARM_COLLAPSED_IN);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public Action moveArm(double Arm_position) {
        return new Action() {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                arm.setTargetPosition((int) (Arm_position));

                ((DcMotorEx) arm).setVelocity(2100);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if (((DcMotorEx) arm).isOverCurrent()) {
                    telemetry.addLine("MOTOR EXCEEDED CURRENT LIMIT!!");


                }
                return false;
            }


        };


    }
}