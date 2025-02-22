
package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 14.811136386145428)
                .build();
        //myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-12, 61, Math.PI / 2)) // blue observation start
       //myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(35, 61, Math.PI / 2)) // blue net zone start
        //myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(12, -61, -Math.PI / 2)) // red observation start
      //  myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-35, -61, Math.PI / 2)) // red net start
                //myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-35, -61, Math.PI)) // remove this eventually(debug start)
                myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0, 48, Math.PI / 2))
                        .splineTo(new Vector2d(36, 48), 0)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}