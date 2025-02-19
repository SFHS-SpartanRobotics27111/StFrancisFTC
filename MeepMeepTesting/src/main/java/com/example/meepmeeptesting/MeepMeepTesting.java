
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
        //myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-12, 61, Math.PI / 2)) // blue observation start
       //myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(35, 61, Math.PI / 2)) // blue net zone start
        //myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(12, -61, -Math.PI / 2)) // red observation start
      //  myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-35, -61, Math.PI / 2)) // red net start
                //myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-35, -61, Math.PI)) // remove this eventually(debug start)
                myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0, 70, -Math.PI / 2))

                        //blue samples

                       /* .strafeTo(new Vector2d(-12, 48))
                        .strafeTo(new Vector2d(-35, 48))
                        .strafeTo(new Vector2d(-38, 14))
                        .strafeTo(new Vector2d(-46, 9))
                        .strafeTo(new Vector2d(-46, 56))
                        .strafeTo(new Vector2d(-46, 9))
                        .strafeTo(new Vector2d(-55, 9))
                        .strafeTo(new Vector2d(-55, 56))
                        .strafeTo(new Vector2d(-55, 9))
                        .strafeTo(new Vector2d(-62, 9))
                        .strafeTo(new Vector2d(-62, 56))
                        .strafeTo(new Vector2d(-62, 45))
                        .strafeTo(new Vector2d(-54, 45))
                        .turn(Math.toRadians(180))
                        .strafeTo(new Vector2d(-55, 55))*/





                //net zone FIRST VERSION
              /* .strafeTo(new Vector2d(50, 61))
               .strafeTo(new Vector2d(35, 61))
               .strafeTo(new Vector2d(38, 15))
               .strafeTo(new Vector2d(46, 9))
               .strafeTo(new Vector2d(46, 56))
               .strafeTo(new Vector2d(46, 9))
               .strafeTo(new Vector2d(55, 9))
               .strafeTo(new Vector2d(55, 56))
               .strafeTo(new Vector2d(55, 9))
               .strafeTo(new Vector2d(62, 9))
               .strafeTo(new Vector2d(62, 56))
               .strafeTo(new Vector2d(62, 9))
               .strafeTo(new Vector2d(31, 9))
               .turn(Math.toRadians(90))
               .strafeTo(new Vector2d(17, 9))*/

        //red observation

                /*.strafeTo(new Vector2d(12, -48))
                .strafeTo(new Vector2d(35, -48))
                .strafeTo(new Vector2d(38, -14))
                .strafeTo(new Vector2d(46, -6))
                .strafeTo(new Vector2d(46, -56))
                .strafeTo(new Vector2d(46, -6))
                .strafeTo(new Vector2d(55, -6))
                .strafeTo(new Vector2d(55, -56))
                .strafeTo(new Vector2d(55, -6))
                .strafeTo(new Vector2d(61, -6))
                .strafeTo(new Vector2d(61, -56))
                .strafeTo(new Vector2d(61, -45))
                .strafeTo(new Vector2d(54, -45))
                .turn(Math.toRadians(180))
                .strafeTo(new Vector2d(55, -55))*/

                       //claw start
                        //.turn(Math.PI)
               //arm.moveArm(ARM_SCORE_SPECIMEN)
               //claw.openClaw

               .strafeTo(new Vector2d(0, 45))
                        //arm.moveArm(ARM_SCORE_SPECIMEN)
                        //claw.openClaw










                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}