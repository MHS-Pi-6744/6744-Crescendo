
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Drivetrain extends SubsystemBase {

  // The drive motors
  private final CANSparkMax leftMotor1 = new CANSparkMax(DrivetrainConstants.kLeftMotorCANID, MotorType.kBrushless);
  private final CANSparkMax leftMotor2 = new CANSparkMax(DrivetrainConstants.kLeftMotor2CANID, MotorType.kBrushless);
  private final CANSparkMax rightMotor1 = new CANSparkMax(DrivetrainConstants.kRightMotorCANID, MotorType.kBrushless);
  private final CANSparkMax rightMotor2 = new CANSparkMax(DrivetrainConstants.kRightMotor2CANID, MotorType.kBrushless);

  // The robot's drive
  private final DifferentialDrive m_drive = new DifferentialDrive(leftMotor1,rightMotor1);

  private final RelativeEncoder m_leftEncoder;
  private final RelativeEncoder m_rightEncoder;

  /** Creates a new subsystem. */
  public Drivetrain() {

    // idle(); Create method

    leftMotor1.restoreFactoryDefaults();
    leftMotor2.restoreFactoryDefaults();
    rightMotor1.restoreFactoryDefaults();
    rightMotor2.restoreFactoryDefaults();
    
    //This is the proper way to group CAN motors. See Spark-max example code - RM
    leftMotor2.follow(leftMotor1);
    rightMotor2.follow(rightMotor1);
    
    //Flip values so robot moves forward when stick-forward/LEDs-green */
    rightMotor1.setInverted(true);
    leftMotor1.setInverted(false);

     // Set Idle mode for CANSparkMax (brake)
    leftMotor1.setIdleMode(IdleMode.kBrake);
    leftMotor2.setIdleMode(IdleMode.kBrake);
    rightMotor1.setIdleMode(IdleMode.kBrake);
    rightMotor2.setIdleMode(IdleMode.kBrake);
    
    // Set Smart Current Limit for CAN SparkMax
    leftMotor1.setSmartCurrentLimit(40, 60);
    leftMotor2.setSmartCurrentLimit(40, 60);
    rightMotor1.setSmartCurrentLimit(40, 60);
    rightMotor2.setSmartCurrentLimit(40, 60);

    // Setup NEO internal encoder to return SI units for odometry
    m_leftEncoder = leftMotor1.getEncoder();
    m_rightEncoder = rightMotor1.getEncoder();
    m_rightEncoder.setPositionConversionFactor(DrivetrainConstants.kEncoderDistanceConversionFactor);
    m_rightEncoder.setVelocityConversionFactor(DrivetrainConstants.kEncoderVelocityConversionFactor);
    m_leftEncoder.setPositionConversionFactor(DrivetrainConstants.kEncoderDistanceConversionFactor);
    m_leftEncoder.setVelocityConversionFactor(DrivetrainConstants.kEncoderVelocityConversionFactor);

    // Burn settings into Spark MAX flash
    leftMotor1.burnFlash();
    leftMotor2.burnFlash();
    rightMotor1.burnFlash();
    rightMotor2.burnFlash();

    // Set drive deadband and safety 
    m_drive.setDeadband(0.05);
    m_drive.setSafetyEnabled(true);

    //drive.feed(); 
  }

  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(fwd, rot);
  }

  //Encoder Methods
  /** Resets the drive encoders to currently read a position of 0. */
  public void resetEncoders() {
    m_leftEncoder.setPosition(0);
    m_rightEncoder.setPosition(0);
  }

  public double getAverageEncoderDistance() {
    return (m_leftEncoder.getPosition() + m_rightEncoder.getPosition()) / 2.0;
  }


  // Teleop default drive command
  public Command arcadeDriveCommand(DoubleSupplier fwd, DoubleSupplier rot) {
    // A split-stick arcade command, with forward/backward controlled by the left
    // hand, and turning controlled by the right.
    return run(() -> m_drive.arcadeDrive(fwd.getAsDouble(), rot.getAsDouble()))
        .withName("arcadeDrive");
  }

/* 
  public Command driveRotateCommand(double degrees, double Speed){
    return runOnce(() ->{
        m_drive.arcadeDrive(Speed, degrees);
    })
    .finallyDo(interuppted -> m_drive.stopMotor());
  }
*/

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Left Drive P",m_leftEncoder.getPosition());
    SmartDashboard.putNumber("Right Drive P", m_rightEncoder.getPosition());
    SmartDashboard.putNumber("Left Drive V",m_leftEncoder.getVelocity());
    SmartDashboard.putNumber("Right Drive V", m_rightEncoder.getVelocity());

    m_drive.feed(); // Used to stop safety error messages?
  }

}
