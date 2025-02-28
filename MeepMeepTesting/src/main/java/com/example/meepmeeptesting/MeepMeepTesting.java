
package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
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

        TrajectoryActionBuilder toChamber = myBot.getDrive().actionBuilder(beginPose)
                .strafeToLinearHeading(new Vector2d(0, -42), Math.PI / 2);

        TrajectoryActionBuilder Backup = myBot.getDrive().actionBuilder(new Pose2d(0, -42, Math.PI / 2))
                .lineToY(-65);

        TrajectoryActionBuilder SplineToPush = myBot.getDrive().actionBuilder(new Pose2d(0, -65, Math.PI / 2))
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(46, -6, -Math.PI / 2), -Math.PI / 2);

        TrajectoryActionBuilder splineToScore2 = myBot.getDrive()
                .actionBuilder(new Pose2d(43, -51, -Math.PI / 2))
                .setTangent(1)
                .splineToLinearHeading(new Pose2d(0, -50, Math.PI / 2), Math.PI / 2)
                .strafeTo(new Vector2d(0, -42));

        Action splineToPickUpLast = myBot.getDrive()
                .actionBuilder(new Pose2d(0, -42, Math.PI / 2))
                .lineToY(-60)
                .splineToLinearHeading(new Pose2d(50, -25, -Math.PI / 2), -Math.PI / 2)
                .build();

        Action goHome = myBot.getDrive()
                .actionBuilder(new Pose2d(50, -25, -Math.PI / 2))
                .strafeTo(new Vector2d(50, -55))
                .turn(Math.PI)
                .build();

        myBot.runAction(
                new SequentialAction(
                splineToScore2.build(),
                splineToPickUpLast,
                goHome
                )
        );


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}