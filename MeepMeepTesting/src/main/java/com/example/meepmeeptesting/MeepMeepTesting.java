
package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        Pose2d beginPose = new Pose2d(10, -65, -Math.PI);

        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(beginPose)
                .strafeToLinearHeading(new Vector2d(0, -42), Math.PI / 2)
                //.strafeTo(new Vector2d(0,-65))
                        .strafeTo(new Vector2d(31, -53))
                        .setTangent(1)
                .splineToLinearHeading(new Pose2d(45, -13, -Math.PI/2), -Math.PI / 2)

                .splineToLinearHeading(new Pose2d(43, -13, -Math.PI / 2), -Math.PI / 2)
                .lineToY(-56)
                .lineToY(-50)


                .splineToLinearHeading(new Pose2d(0, -42, Math.PI / 2), Math.PI / 2)
                        .setTangent(-1)
                .splineToLinearHeading(new Pose2d(48, -31, -Math.PI / 2), -Math.PI / 2)
                .strafeTo(new Vector2d(39, -65))


                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}