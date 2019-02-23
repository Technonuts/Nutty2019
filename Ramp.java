package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Ramp {

    static DoubleSolenoid rampLock = new DoubleSolenoid(RobotMap.RAMP_LOCK_UP, RobotMap.RAMP_LOCK_DOWN);
    
    static VictorSPX rampMotor = new VictorSPX(RobotMap.RAMP_MOTOR);
    static TalonSRX rampWinchMotor = new TalonSRX(RobotMap.RAMP_WINCH_MOTOR);

    static DigitalInput rampLowLimit = new DigitalInput(RobotMap.RAMP_LOW_LIMIT);
    static DigitalInput rampHighLimit = new DigitalInput(RobotMap.RAMP_HIGH_LIMIT);
    static DigitalInput rampWinchLimit = new DigitalInput(RobotMap.RAMP_WINCH_LIMIT);

    static double timeStamp;

    public static void rampControl() {
        if (OI.rampRelease.get()) {
            rampLock.set(Value.kReverse);
            
            if (timeStamp - Timer.getMatchTime() > 3) {
                winchRamp();
                if (!rampLowLimit.get()) {
                    rampMotor.set(ControlMode.PercentOutput, .5);
                }
                else {
                    rampMotor.set(ControlMode.PercentOutput, 0);
                }
            }
        }
        else if (OI.rampLock.get()) {
            rampLock.set(Value.kForward);

            if(!rampHighLimit.get()) {
                rampMotor.set(ControlMode.PercentOutput, -.5 );
            }
            else {
                rampMotor.set(ControlMode.PercentOutput, 0);
            }
            
            timeStamp = Timer.getMatchTime();
        }
    } 

    public static void winchRamp() {
        if(!rampWinchLimit.get()) {
            rampWinchMotor.set(ControlMode.PercentOutput, -.25);
        }
        else {
            rampWinchMotor.set(ControlMode.PercentOutput, 0);
        }
    }
}