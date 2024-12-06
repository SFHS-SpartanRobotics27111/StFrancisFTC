package org.firstinspires.ftc.teamcode.tuning;

import com.amarcolini.joos.command.Command;
import com.amarcolini.joos.command.CommandOpMode;
import com.amarcolini.joos.command.SequentialCommand;
import com.amarcolini.joos.command.TimeCommand;
import com.amarcolini.joos.dashboard.JoosConfig;
import com.amarcolini.joos.geometry.Angle;
import com.amarcolini.joos.geometry.Pose2d;
import com.amarcolini.joos.geometry.Vector2d;
import com.amarcolini.joos.hardware.drive.DriveComponent;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.SampleRobot;
import org.firstinspires.ftc.teamcode.tuning.util.EncoderData;
import org.firstinspires.ftc.teamcode.tuning.util.TuningData;

import java.util.ArrayList;
import java.util.List;

import static com.amarcolini.joos.util.MathUtil.doLinearRegressionNoIntercept;

@TeleOp(group = "Tuning")
@JoosConfig
public class AngularRampLogger extends CommandOpMode {
    @Register
    private SampleRobot robot;
    public static double totalTime = 5.0;

//    private val headingSensor by getHardware<IMU>("imu").map {
//        getAngleSensor(
//                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
//                RevHubOrientationOnRobot.UsbFacingDirection.UP
//        )
//    }

    @Override
    public void preInit() {
        ArrayList<Double> angVels = new ArrayList<>();
        List<EncoderData> encoders = new TuningData((DriveComponent) robot.drive).encoders;
        assert !encoders.isEmpty() : "This robot does not support this test.";

        new SequentialCommand(true, new TimeCommand((t, dt) -> {
            robot.drive.setDrivePower(new Pose2d(0, 0, Angle.rad(t / totalTime)));
            angVels.add(robot.headingSensor.getAngularVelocity().radians());
            for (EncoderData encoder : encoders) {
                encoder.data.add(encoder.encoder.getAsDouble());
            }
            return t > totalTime;
        }), Command.of(() -> {
//            telem.addData("heading", angVels).setRetained(true);
//            telem.addData("left", leftVels).setRetained(true);
//            telem.addData("right", rightVels).setRetained(true);
//            telem.addData("perp", perpVels).setRetained(true);
//            telem.update();
            for (EncoderData encoder : encoders) {
                double solution = doLinearRegressionNoIntercept(angVels, encoder.data);
                Vector2d pos = new Vector2d(solution * encoder.phi.sin(), -solution * encoder.phi.cos());
                telem.addData(encoder.name + " solution", solution).setRetained(true);
                telem.addData(encoder.name + " pos", pos).setRetained(true);
            }
        })).onEnd((interrupted) -> {
            robot.drive.setDrivePower(new Pose2d());
            telem.addLine("finished!").setRetained(true);
        }).schedule();
    }
}