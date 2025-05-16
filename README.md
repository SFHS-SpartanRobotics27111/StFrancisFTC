# St. Francis FTC Code

Team code for St. Francis

# Setting up

-   Make sure the Gradle Runtime (Settings > Build, Execution, Deployment >
    Build Tools > Gradle) is set to JetBrains Runtime 21.X.X. If it isn't, make
    sure to download that JDK from the same dropdown.
-   Do not upgrade Gradle or the Android Gradle Plugin if Android Studio prompts
    you to.

# Git Best Practices

-   Fetch and pull before you push to avoid merge conflicts.
-   All features should be developed in their own branches and merged into main
    once they are done. The main branch should always be buildable and in a
    working order
-   Double check the files changed before you commit, especially any
    configuration files, as Android Studio can change them without telling you.

# Running

-   Set up the robot config on the Driver Hub
-   Open Rev Hardware Control App
-   Connect computer to robot wifi
-   Make sure Android Studio device is set to the robot
-   Run and stop the program in Android Studio
-   Select and start the OpMode on the Driver Hub

# Robot Information

[#!Section Outdated]
This robot has a two-motor differential-steered (sometimes called tank or skid steer) drivetrain
with a left and right drive motor.

The drive wheels are 96mm diameter traction (Rhino) or omni wheels.
They are driven by 2x 5203-2402-0019 312RPM Yellow Jacket Planetary Gearmotors.

This robot's main scoring mechanism includes an arm powered by a motor, a "wrist" driven
by a servo, and an intake driven by a continuous rotation servo.

The arm is powered by a 5203-2402-0051 (50.9:1 Yellow Jacket Planetary Gearmotor) with an
external 5:1 reduction. This creates a total ~254.47:1 reduction.

The wrist is powered by a goBILDA Torque Servo (2000-0025-0002).

The intake wheels are powered by a goBILDA Speed Servo (2000-0025-0003) in Continuous Rotation mode.


# Pinpoint Odometry 
We are using a Gobilda Pinpoint Odometry Computer with 2 Gobilda odometry pods and the roadrunner library 
Documentation can be found under the 1.0 section at https://rr.brott.dev/docs/v1-0/tuning/


# Meep Meep Visualizer 
The Meep Mepp Visualizzer files are already in the project, but the following setup steps are required 

 
1.   Create a run configuration for Android Studio.
2.   First, click on the drop down menu on the top bar of Android Studio, where it says "TeamCode" with a little Android logo next to it.
3.   Click Edit Configurations
4.   Click on the "+" symbol in the top left of the window, and when it prompts you, select "Application".
5.   Change the name to your liking (ex. meepmeep-run)
6.   Where it says "module not specified", click to open the dropdown, then select JBR-21.
7.   Where it says "cp " click it to open the dropdown, and then select StFrancisFTC.MeepMeepTesting.main
8.   Where it says "Main Class", click the little "file" icon to the right of the text and then select the name of the main class for your MeepMeepTesting module.
9.   From here, in the bottom right of the window, press "Apply" then "Ok".
10.   It will now automatically switch to that Run/Debug Configuration profile.
    

If at any point you would like to build code onto your Control Hub or Phone, then click the Run/Debug configuration profile at the top to open the dropdown menu and select TeamCode. Perform the same steps to switch back to MeepMeepRun.
