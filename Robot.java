/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  Vision visionThread = new Vision();
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    Lift.bottomSetpoint = Lift.liftPot.get();
    Lift.updateSetpoints();

    Ramp.rampLock.set(Value.kForward);

    
    visionThread.start();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    //Tank Drive and driver station indicators
    
    SmartDashboard.putNumber("Front Left Motor", Drive.frontLeftMotor.get());
    SmartDashboard.putNumber("Center Left Motor", Drive.centerLeftMotor.get());
    SmartDashboard.putNumber("Back Left Motor", Drive.backLeftMotor.get());
    SmartDashboard.putNumber("Front Right Motor", Drive.frontRightMotor.get());
    SmartDashboard.putNumber("Center Right Motor", Drive.centerRightMotor.get());
    SmartDashboard.putNumber("Back Right Motor", Drive.backRightMotor.get());

    //Lift Motor Control  
    Lift.liftControl();

    //Ramp Control
    Ramp.rampControl();
    SmartDashboard.putBoolean("Ramp Winch", Ramp.rampWinchLimit.get());

    //Arm Control
    Arm.moveArm();
      
    if (OI.visionLock.get()) {
      visionThread.visionDrive();
    }
    else {
      Drive.tankDrive(OI.leftJoystick.getY(), OI.rightJoystick.getY());
    }
    
    
      
    

    SmartDashboard.putNumber("Bottom", Lift.bottomSetpoint);
    SmartDashboard.putNumber("Low", Lift.lowSetpoint);
    SmartDashboard.putNumber("Middle", Lift.middleSetpoint);
    SmartDashboard.putNumber("High", Lift.highSetpoint);
  

    SmartDashboard.putNumber("Lift Pot", Lift.liftPot.get());
    SmartDashboard.putNumber("Lift Position", Lift.position);
    SmartDashboard.putNumber("Nudge", Lift.nudge);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
