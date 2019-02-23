package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive {

    static CANSparkMax frontLeftMotor = new CANSparkMax(RobotMap.FRONT_LEFT_MOTOR, MotorType.kBrushless);
    static CANSparkMax centerLeftMotor = new CANSparkMax(RobotMap.CENTER_LEFT_MOTOR, MotorType.kBrushless);
    static CANSparkMax backLeftMotor = new CANSparkMax(RobotMap.BACK_LEFT_MOTOR, MotorType.kBrushless);
    static CANSparkMax frontRightMotor = new CANSparkMax(RobotMap.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
    static CANSparkMax centerRightMotor = new CANSparkMax(RobotMap.CENTER_RIGHT_MOTOR, MotorType.kBrushless);
    static CANSparkMax backRightMotor = new CANSparkMax(RobotMap.BACK_RIGHT_MOTOR, MotorType.kBrushless);

    static SpeedControllerGroup leftMotors = new SpeedControllerGroup(frontLeftMotor, centerLeftMotor, backLeftMotor);
    static SpeedControllerGroup rightMotors = new SpeedControllerGroup(frontRightMotor, centerRightMotor, backRightMotor);

    static DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

    public static void tankDrive (double leftSpeed, double rightSpeed) {
        //Values are multiplied by -1 to invert
        drive.tankDrive(leftSpeed*-1, rightSpeed*-1);
    }

    public static void arcadeDrive (double speed, double turn) {
        drive.arcadeDrive(speed, turn);
    }

    
}