package org.firstinspires.ftc.teamcode;

import com.amarcolini.joos.geometry.Angle;
import com.amarcolini.joos.localization.AngleSensor;
import com.qualcomm.robotcore.hardware.AnalogInput;
import org.jetbrains.annotations.NotNull;

public class AxonAngleSensor extends AngleSensor {
    private final AnalogInput input;

    public AxonAngleSensor(
            AnalogInput input,
            Angle offset,
            boolean reversed
    ) {
        this.input = input;
        setReversed(reversed);
        setOffset(offset);
    }

    @NotNull
    @Override
    protected Angle getRawAngle() {
        return Angle.circle.times(input.getVoltage() / input.getMaxVoltage());
    }
}