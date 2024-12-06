package org.firstinspires.ftc.teamcode.tuning.util;

import com.amarcolini.joos.geometry.Angle;

import java.util.ArrayList;
import java.util.function.DoubleSupplier;

public class EncoderData {
    public final String name;
    public final ArrayList<Double> data = new ArrayList<>();
    public final Angle phi;
    public final DoubleSupplier encoder;

    public EncoderData(String name, Angle phi, DoubleSupplier encoder) {
        this.name = name;
        this.encoder = encoder;
        this.phi = phi;
    }
}