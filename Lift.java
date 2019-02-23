package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift {

    static AnalogPotentiometer liftPot = new AnalogPotentiometer(RobotMap.LIFT_POT);
    static DigitalInput liftHighLimit = new DigitalInput(RobotMap.HIGH_LIFT_LIMIT);
    static DigitalInput liftLowLimit = new DigitalInput(RobotMap.LOWER_LIFT_LIMIT);

    static VictorSPX liftMotor1 = new VictorSPX(RobotMap.LIFT_MOTOR_1);
    static VictorSPX liftMotor2 = new VictorSPX(RobotMap.LIFT_MOTOR_2);

    static double bottomSetpoint;
    static double lowSetpoint;
    static double middleSetpoint;
    static double highSetpoint;
    
    static double tolerance = .005;
    static double position;

    static double nudge = 1;

    public static void liftControl() {
        position = liftPot.get();

        if (OI.LiftOverride.get()) {
            liftMotor1.set(ControlMode.PercentOutput, OI.liftJoystick.getY());
            liftMotor2.set(ControlMode.PercentOutput, OI.liftJoystick.getY());
        }
        else {
            if (OI.liftBottomHeight.get() && position > bottomSetpoint + tolerance) {
                liftMotor1.set(ControlMode.PercentOutput, setSpeed(bottomSetpoint));
                liftMotor2.set(ControlMode.PercentOutput, setSpeed(bottomSetpoint));
                nudge += .05;
            } 
            else if (OI.liftLowHeight.get() && (position < lowSetpoint - tolerance || position > lowSetpoint + tolerance)) {
                liftMotor1.set(ControlMode.PercentOutput, setSpeed(lowSetpoint));
                liftMotor2.set(ControlMode.PercentOutput, setSpeed(lowSetpoint));
                nudge += .05;
            }
            else if (OI.liftMiddleHeight.get() && (position < middleSetpoint - tolerance || position > middleSetpoint + tolerance)) {
                liftMotor1.set(ControlMode.PercentOutput, setSpeed(middleSetpoint));
                liftMotor2.set(ControlMode.PercentOutput, setSpeed(middleSetpoint));
                nudge += .05;
            }
            else if (OI.liftHighHeight.get() && position < highSetpoint - tolerance) {
                liftMotor1.set(ControlMode.PercentOutput, setSpeed(highSetpoint));
                liftMotor2.set(ControlMode.PercentOutput, setSpeed(highSetpoint));
                nudge += .05;
            }
            else {
                liftMotor1.set(ControlMode.PercentOutput, 0);
                liftMotor2.set(ControlMode.PercentOutput, 0);
                nudge = 1;
            }
        }
    }

    public static double setSpeed(double setpoint) {

        double error = setpoint - liftPot.get();
        double speed = error * nudge;

        if (speed > 0 && speed < .3) {
            speed = .3;
        }
        else if (speed < 0 && speed > -.3) {
            speed = -.3;
        }


        if (liftLowLimit.get() && speed < 0) {
            speed = 0;
        }
        else if (liftHighLimit.get() && speed > 0) {
            speed = 0;
        }
        SmartDashboard.putNumber("Current Setpoint", setpoint);
        return speed;
    }

    public static void updateSetpoints() {
        lowSetpoint = bottomSetpoint + .052;
        middleSetpoint = bottomSetpoint + .286;
        highSetpoint =  bottomSetpoint + .467;
    }
}