
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;
import edu.wpi.first.wpilibj.Encoder;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import java.util.function.DoubleSupplier;


public class Drivetrain extends SubsystemBase {

  // must create groups and add 2 motors, assign CANID in constants - RM
  private final CANSparkMax leftMotor1 = new CANSparkMax(DrivetrainConstants.kLeftMotorCANID, MotorType.kBrushless);
  private final CANSparkMax leftMotor2 = new CANSparkMax(DrivetrainConstants.kLeftMotor2CANID, MotorType.kBrushless);
  private final CANSparkMax rightMotor1 = new CANSparkMax(DrivetrainConstants.kRightMotorCANID, MotorType.kBrushless);
  private final CANSparkMax rightMotor2 = new CANSparkMax(DrivetrainConstants.kRightMotor2CANID, MotorType.kBrushless);

  private final DifferentialDrive m_myRobot = new DifferentialDrive(leftMotor1,rightMotor1);

  // Add encoders here - RM

  private final Encoder m_RightEncoder = new Encoder(
    DrivetrainConstants.kRightEncoderPort[0],
    DrivetrainConstants.kRightEncoderPort[1],
    DrivetrainConstants.kRightEncoderrevesed
  );

  private final Encoder m_LeftEncoder = new Encoder(
    DrivetrainConstants.kLeftEncoderPort[0],
    DrivetrainConstants.kLeftEncoderPort[1],
    DrivetrainConstants.kLeftEncoderReversed
  );




  /** Creates a new subsystem. */
  public Drivetrain() {
    
    //This is the proper way to group CAN motors. See Spark-max example code - RM
    leftMotor2.follow(leftMotor1);
    rightMotor2.follow(rightMotor1);
    
    rightMotor1.setInverted(true);

    m_LeftEncoder.setDistancePerPulse(DrivetrainConstants.kEncoderDistancePerPulse);
    m_RightEncoder.setDistancePerPulse(DrivetrainConstants.kEncoderDistancePerPulse);
  }

  public Command arcadeDriveCommand(DoubleSupplier fwd, DoubleSupplier rot) {
    // A split-stick arcade command, with forward/backward controlled by the left
    // hand, and turning controlled by the right.
    return run(() -> m_myRobot.arcadeDrive(fwd.getAsDouble(), rot.getAsDouble()))
        .withName("arcadeDrive");
  }

  public Command driveForwardCommand(double timeout, double speed){
    return runOnce(() -> m_myRobot.arcadeDrive(speed, 0))
    .finallyDo(interuppted -> m_myRobot.stopMotor());

  }
  /* 
  public Command driveDistanceCommand(double distanceMeters, double speed) {
    return runOnce(
            () -> {
              // Reset encoders at the start of the command
              m_leftEncoder.reset();
              m_rightEncoder.reset();
            })
        // Drive forward at specified speed
        .andThen(run(() -> m_drive.arcadeDrive(speed, 0)))
        // End command when we've traveled the specified distance
        .until(
            () ->
                Math.max(m_leftEncoder.getDistance(), m_rightEncoder.getDistance())
                    >= distanceMeters)
        // Stop the drive when the command ends
        .finallyDo(interrupted -> m_drive.stopMotor());
  }*/


  public Command driveDistanceCommand(Double DistanceM, Double SpeedM){
    return runOnce(() -> {
      m_RightEncoder.reset();
      m_LeftEncoder.reset();
    }).andThen(/*run(() -> m_drive.arcadeDrive(speed, 0)  Drive little bro*/)
    .until(() -> Math.max(m_LeftEncoder.getDistance(), m_RightEncoder.getDistance()) >= DistanceM);//.finallyDo(interuppted -> stopmoto);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
