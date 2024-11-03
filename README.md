# St. Francis FTC Code

Team code for St. Francis

# Setting up

-   Make sure the Gradle Runtime (Settings > Build, Execution, Deployment >
    Build Tools > Gradle) is set to JetBrains Runtime 17.X.X. If it isn't, make
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
-   Connect computer to robot wifi
-   Make sure Android Studio device is set to the robot
-   Run and stop the program in Android Studio
-   Select and start the OpMode on the Driver Hub

# Robot Information

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
