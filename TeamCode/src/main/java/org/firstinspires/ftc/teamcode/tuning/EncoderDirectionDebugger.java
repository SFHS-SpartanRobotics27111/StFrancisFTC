package org.firstinspires.ftc.teamcode.tuning;

import com.amarcolini.joos.command.CommandOpMode;
import com.amarcolini.joos.hardware.drive.DriveComponent;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.SampleRobot;
import org.firstinspires.ftc.teamcode.tuning.util.EncoderData;
import org.firstinspires.ftc.teamcode.tuning.util.TuningData;

import java.util.List;

/**
 * This will print out the positions of all localization encoders. Useful for making sure directions
 * and hardware ids are correct.
 */
@TeleOp(group = "Tuning")
public class EncoderDirectionDebugger extends CommandOpMode {
    @Register
    private SampleRobot robot;

    @Override
    public void preInit() {
        final List<EncoderData> encoders = new TuningData((DriveComponent) robot.drive).encoders;
        assert !encoders.isEmpty() : "This robot does not support this test.";

        schedule(true, () -> {
           for (EncoderData encoder : encoders) {
               telem.addData(encoder.name, encoder.encoder.getAsDouble());
           }
        });
    }
}