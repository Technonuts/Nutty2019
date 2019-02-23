package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalGlitchFilter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm {
    static TalonSRX armMotor = new TalonSRX(RobotMap.ARM_MOTOR);

    static DigitalInput armLeftLimit = new DigitalInput(RobotMap.ARM_LEFT_LIMIT);
    static DigitalInput armRightLimit = new DigitalInput(RobotMap.ARM_RIGHT_LIMIT);

    static AnalogPotentiometer armPot = new AnalogPotentiometer(RobotMap.ARM_POT);
    static double position;
    static double nudge = 1;

    static double stowedSetpoint = .65;
    static double travelSetpoint = .78;
    static double loadSetpoint = .99;
    static double tolerance = .025;

    public static void moveArm() {
        position = armPot.get();

        if (OI.armStowed.get() && position > stowedSetpoint + tolerance) {
            armMotor.set(ControlMode.PercentOutput, setSpeed(stowedSetpoint));
            nudge += .05;
        }
        else if (OI.armTravel.get() && (position < travelSetpoint - tolerance || position > travelSetpoint + tolerance)) {
            armMotor.set(ControlMode.PercentOutput, setSpeed(travelSetpoint));
            nudge += .05;
        }
        else if (OI.armLoad.get() && (position < loadSetpoint - tolerance || position > loadSetpoint + tolerance)) {
            armMotor.set(ControlMode.PercentOutput, setSpeed(loadSetpoint));
            nudge += .05;
        }
        else {
            armMotor.set(ControlMode.PercentOutput, 0);
            nudge = 1;
        }

        SmartDashboard.putBoolean("Arm Left", armLeftLimit.get());
        SmartDashboard.putBoolean("Arm Right", armRightLimit.get());
        SmartDashboard.putNumber("Arm Pot", armPot.get());
    }

    public static double setSpeed(double setpoint) {

        double error = armPot.get() - setpoint;
        double speed = error * nudge;

        SmartDashboard.putNumber("Current Setpoint", setpoint);
        return speed;
    }
}