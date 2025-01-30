
package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 14.811136386145428)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-12, 61, Math.PI / 2))


                .strafeTo(new Vector2d(-12, 48))
                .strafeTo(new Vector2d(-35, 48))
                //claw.openClaw()
                //.splineTo(new Vector2d(0, 36), -Math.PI / 2)
                .strafeTo(new Vector2d(-38, 14))
                .strafeTo(new Vector2d(-46, 6))
                .strafeTo(new Vector2d(-46, 56))
                .strafeTo(new Vector2d(-46, 6))
                .strafeTo(new Vector2d(-55, 6))
                .strafeTo(new Vector2d(-55, 56))
                .strafeTo(new Vector2d(-55, 6))
                .strafeTo(new Vector2d(-61, 6))
                .strafeTo(new Vector2d(-61, 56))
                .strafeTo(new Vector2d(-61, 45))
                .strafeTo(new Vector2d(-54, 45))
                .splineTo(new Vector2d(-55, 46 ), Math.PI)
                .strafeTo(new Vector2d(-55, 55))



                /*.strafeTo(new Vector2d(-47, 55))
                .strafeTo(new Vector2d(-47, 1))
                .strafeTo(new Vector2d(-57, 1))
                .strafeTo(new Vector2d(-57, 55))
                .strafeTo(new Vector2d(-57, 1))
                .strafeTo(new Vector2d(-64, 1))
                .strafeTo(new Vector2d(-64, 55))
                .strafeTo(new Vector2d(-64, 45))
                .strafeTo(new Vector2d(-64, 55))
                //blocks END
                //.strafeTo(new Vector2d(43, 55))
               // //.splineTo(new Vector2d(-47, 47), -Math.PI)
                //.splineTo(new Vector2d(-34, 12), -Math.PI)
                //.splineTo(new Vector2d(-47, 16), Math.PI)*/
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}