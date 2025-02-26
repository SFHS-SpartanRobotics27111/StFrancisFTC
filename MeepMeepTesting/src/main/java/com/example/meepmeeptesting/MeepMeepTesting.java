
package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Actions;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

public class MeepMeepTesting {
    public static void main(String[] args) {
        Pose2d beginPose = new Pose2d(10, -65, -Math.PI);

        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        TrajectoryActionBuilder toChamber = myBot.getDrive().actionBuilder(beginPose)
                .strafeToLinearHeading(new Vector2d(0, -42), Math.PI / 2);

        TrajectoryActionBuilder backup = myBot.getDrive().actionBuilder(new Pose2d(0, -42, Math.PI / 2))
                .lineToY(-55);

        TrajectoryActionBuilder splineToPush = myBot.getDrive().actionBuilder(new Pose2d(0, -65, Math.PI / 2))
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(48, -13, -Math.PI / 2), -Math.PI / 2)
                .strafeTo(new Vector2d(43, -13));

        TrajectoryActionBuilder pushCycle1 = myBot.getDrive().actionBuilder(new Pose2d(48, -13, -Math.PI / 2))
                .lineToY(-56)
                .lineToY(-49);

        TrajectoryActionBuilder splineToScore2and3 = myBot.getDrive().actionBuilder(new Pose2d(48, -49, -Math.PI / 2))
                .splineToLinearHeading(new Pose2d(0, -42, Math.PI / 2), Math.PI / 2);

        TrajectoryActionBuilder splineToPickUpLast = myBot.getDrive().actionBuilder(new Pose2d(0, -42, Math.PI / 2))
                .splineToLinearHeading(new Pose2d(48, -49, -Math.PI / 2), -Math.PI / 2)
                .lineToY(-55);

        TrajectoryActionBuilder goHome = myBot.getDrive().actionBuilder(new Pose2d(0, -55, Math.PI / 2))
                .strafeTo(new Vector2d(39, -60));


        myBot.runAction(
                new SequentialAction(
                       splineToPush.build()
                )
        );

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}