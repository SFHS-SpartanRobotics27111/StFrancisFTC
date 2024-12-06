package org.firstinspires.ftc.teamcode.tuning;

import com.amarcolini.joos.command.*;
import com.amarcolini.joos.dashboard.JoosConfig;
import com.amarcolini.joos.drive.AbstractSwerveDrive;
import com.amarcolini.joos.drive.Drive;
import com.amarcolini.joos.drive.SwerveModule;
import com.amarcolini.joos.geometry.Angle;
import com.amarcolini.joos.hardware.drive.DriveComponent;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.SampleRobot;
import org.firstinspires.ftc.teamcode.tuning.util.TuningData;

import java.util.function.Supplier;

@TeleOp
@JoosConfig
public class SwerveModuleTuningTest extends CommandOpMode {
    @Register
    private SampleRobot robot;
    public static Angle startAngle = Angle.deg(0);
    public static Angle endAngle = Angle.deg(60);
    public static double delay = 3.0;
    public static int moduleIndex = 0;
    private SwerveModule currentModule;
    private int lastIndex = moduleIndex;

    @Override
    public void preInit() {
        unregister(robot.drive);
        TuningData data = new TuningData((Drive) robot.drive);
        assert data.drive instanceof AbstractSwerveDrive : "Only swerve drives are allowed";
        final AbstractSwerveDrive swerve = (AbstractSwerveDrive) data.drive;
        lastIndex = moduleIndex;
        currentModule = swerve.getModules().get(moduleIndex);
        new SequentialCommand(
                Command.of(() -> currentModule.setModuleOrientation(startAngle)),
                new WaitCommand(delay),
                Command.of(() -> currentModule.setModuleOrientation(endAngle)),
                new WaitCommand(delay)
        ).repeatForever().schedule();

        schedule(true, () -> {
           if (moduleIndex != lastIndex) {
               currentModule.setModuleOrientation(currentModule.getModuleOrientation());
               currentModule.update();
               lastIndex = moduleIndex;
               currentModule = swerve.getModules().get(moduleIndex);
           } else currentModule.update();
        });
    }
}