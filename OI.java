package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

    static final int LEFT_JOYSTICK = 0;
    static final int RIGHT_JOYSTICK = 1;
    static final int LIFT_JOYSTICK = 2;

    static Joystick rightJoystick = new Joystick(RIGHT_JOYSTICK);
    static Joystick liftJoystick = new Joystick(LIFT_JOYSTICK);
    static Joystick leftJoystick = new Joystick(LEFT_JOYSTICK);

    // lift stick buttons
    static final int LIFT_BOTTOM_HEIGHT = 3;
    static final int LIFT_LOW_HEIGHT = 4;
    static final int LIFT_MIDDLE_HEIGHT = 5;
    static final int LIFT_HIGH_HEIGHT = 6;
    static final int LIFT_OVERRIDE = 1;

    // right stick buttons
    static final int ARM_STOWED = 5;
    static final int ARM_TRAVEL = 6;
    static final int ARM_LOAD = 4;
    static final int VISION_LOCK = 2;
    static final int ARM_OVERRIDE = 1;

    // left stick buttons
    static final int RAMP_RELEASE = 1;
    static final int RAMP_RETRACT = 2;
    static final int RAMP_LOCK = 3;


    //Initialize Buttons
    static JoystickButton armStowed = new JoystickButton(rightJoystick, ARM_STOWED);
    static JoystickButton armTravel = new JoystickButton(rightJoystick, ARM_TRAVEL);
    static JoystickButton armLoad = new JoystickButton(rightJoystick, ARM_LOAD);
    static JoystickButton visionLock = new JoystickButton(rightJoystick, VISION_LOCK);
    static JoystickButton LiftOverride = new JoystickButton(liftJoystick, LIFT_OVERRIDE);
    static JoystickButton armOverride = new JoystickButton(rightJoystick, ARM_OVERRIDE);
    static JoystickButton liftBottomHeight = new JoystickButton(liftJoystick, LIFT_BOTTOM_HEIGHT);
    static JoystickButton liftLowHeight = new JoystickButton(liftJoystick, LIFT_LOW_HEIGHT);
    static JoystickButton liftMiddleHeight = new JoystickButton(liftJoystick, LIFT_MIDDLE_HEIGHT);
    static JoystickButton liftHighHeight = new JoystickButton(liftJoystick, LIFT_HIGH_HEIGHT);
    static JoystickButton rampRelease = new JoystickButton(leftJoystick, RAMP_RELEASE);
    static JoystickButton rampRetract = new JoystickButton(leftJoystick, RAMP_RETRACT);
    static JoystickButton rampLock = new JoystickButton(leftJoystick, RAMP_LOCK);
}