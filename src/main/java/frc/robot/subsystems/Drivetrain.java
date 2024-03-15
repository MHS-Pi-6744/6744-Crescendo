
package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.DrivetrainConstants;


public class Drivetrain extends SubsystemBase {

  // The drive motors
  private final CANSparkMax leftMotor1 = new CANSparkMax(DrivetrainConstants.kLeftMotorCANID, MotorType.kBrushless);
  private final CANSparkMax leftMotor2 = new CANSparkMax(DrivetrainConstants.kLeftMotor2CANID, MotorType.kBrushless);
  private final CANSparkMax rightMotor1 = new CANSparkMax(DrivetrainConstants.kRightMotorCANID, MotorType.kBrushless);
  private final CANSparkMax rightMotor2 = new CANSparkMax(DrivetrainConstants.kRightMotor2CANID, MotorType.kBrushless);

  public double k_autoSpeed = -0.3;
  public double k_roto = 2.5;
  public double k_moto = 0.5;
  


  // The robot's drive
  private final DifferentialDrive m_drive = new DifferentialDrive(leftMotor1,rightMotor1);
  
  private final RelativeEncoder m_leftEncoder;
  private final RelativeEncoder m_rightEncoder;

  // Create Gyroscope
  //private final ADXRS450_Gyro m_gyro = new ADXRS450_Gyro();
  
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

  // not used? - RM
  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(fwd, rot);
  }

  //Encoder Methods
 
  public void resetEncoders() {
    m_leftEncoder.setPosition(0);
    m_rightEncoder.setPosition(0);
  }

  public double getAverageEncoderDistance() {
    return (m_leftEncoder.getPosition() + m_rightEncoder.getPosition()) / 2.0;
  }
  public double getLeftEncoderDistance() {
    return (m_leftEncoder.getPosition());
  }
  public double getRightEncoderDistance() {
    return (m_rightEncoder.getPosition());
  }


  //This gets the average of the total distance of the encoders
  //Ex: Rencoder=3 and Lencoder=-3 returns 3
  public double getAbsoluteAverageEncoderDistance() {
    return ((Math.abs(m_rightEncoder.getPosition()) + Math.abs(m_leftEncoder.getPosition()) / 2.0));
  }

  // Teleop default drive command
  public Command arcadeDriveCommand(DoubleSupplier fwd, DoubleSupplier rot) {
    // A split-stick arcade command, with forward/backward controlled by the left
    // hand, and turning controlled by the right.
    return run(() -> m_drive.arcadeDrive(fwd.getAsDouble(), rot.getAsDouble()))
        .withName("arcadeDrive");
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Left Drive P",m_leftEncoder.getPosition());
    SmartDashboard.putNumber("Right Drive P", m_rightEncoder.getPosition());
    SmartDashboard.putNumber("Left Drive V",m_leftEncoder.getVelocity());
    SmartDashboard.putNumber("Right Drive V", m_rightEncoder.getVelocity());
    SmartDashboard.putNumber("Moter Go", k_moto);
    SmartDashboard.putNumber("Rotat Go", k_roto);
    SmartDashboard.putNumber("Speed", k_autoSpeed);
    double m_roto = SmartDashboard.getNumber("Rotat Go", 2.5);
    if((m_roto != k_roto)) {k_roto = m_roto; }
    double m_moto = SmartDashboard.getNumber("Moter Go", 0.5);
    if((m_moto != k_moto)) {k_moto = m_moto; }
    double m_autoSpeed = SmartDashboard.getNumber("Speed", 0.3);
    if((m_autoSpeed != k_autoSpeed)) {k_autoSpeed = m_autoSpeed; }
    //SmartDashboard.putNumber("Gyro Angle", m_gyro.getAngle());
    //SmartDashboard.putNumber("Gyro Rate", m_gyro.getRate());

    m_drive.feed(); // Used to stop safety error messages?


  }

}
