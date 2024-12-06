package org.firstinspires.ftc.teamcode.tuning;

import com.amarcolini.joos.command.CommandOpMode;
import com.amarcolini.joos.geometry.Pose2d;
import com.amarcolini.joos.hardware.Motor;
import com.amarcolini.joos.hardware.drive.DriveComponent;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.SampleRobot;
import org.firstinspires.ftc.teamcode.tuning.util.TuningData;

@TeleOp(group = "Tuning")
public class ForwardPushTest extends CommandOpMode {
    @Register
    private SampleRobot robot;

    @Override
    public void preInit() {
        final TuningData data = new TuningData((DriveComponent) robot.drive);
        assert data.forwardTicks != null : "This drive does not support this test.";
        final double initTicks = data.forwardTicks.getAsDouble();

        if (robot.drive instanceof DriveComponent) ((DriveComponent) robot.drive).getMotors().setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        robot.drive.setDrivePower(new Pose2d());

        schedule(true, () -> {
            double ticks = data.forwardTicks.getAsDouble() - initTicks;
            telem.addData("Forward Encoder Ticks", ticks);
        });
    }
}