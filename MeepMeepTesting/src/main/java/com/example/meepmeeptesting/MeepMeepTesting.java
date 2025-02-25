
package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        Pose2d beginPose = new Pose2d(-46, 9, Math.PI / 2);

        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(beginPose)
                





                .strafeTo(new Vector2d(-46, 56))
                        .waitSeconds(2)
                .strafeTo(new Vector2d(-46, 31))
                        .waitSeconds(2)
                .splineToLinearHeading(new Pose2d(0, 45, -Math.PI /2), -Math.PI / 2)
                        .waitSeconds(2)
                .splineToLinearHeading(new Pose2d(-46, 31, Math.PI /2), -Math.PI /2)
                .waitSeconds(2)
                .splineToLinearHeading(new Pose2d(0, 45, -Math.PI /2), -Math.PI / 2)
                .waitSeconds(2)
                .strafeTo(new Vector2d(-39, 65))

                /*.strafeTo(new Vector2d(-46, 9))
                .strafeTo(new Vector2d(-55, 9))
                .strafeTo(new Vector2d(-55, 56))

               // .strafeTo(new Vector2d(-55, 9))

                 */
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}