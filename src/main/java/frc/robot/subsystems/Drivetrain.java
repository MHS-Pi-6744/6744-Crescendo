
package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkLowLevel.MotorType;
//import edu.wpi.first.wpilibj.Encoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;


public class Drivetrain extends SubsystemBase {

  // Create Drive Motors
  private final CANSparkMax m_leftLead = new CANSparkMax(DrivetrainConstants.kLeftMotorCANID, MotorType.kBrushless);
  private final CANSparkMax m_leftFollow = new CANSparkMax(DrivetrainConstants.kLeftMotor2CANID, MotorType.kBrushless);
  private final CANSparkMax m_rightLead = new CANSparkMax(DrivetrainConstants.kRightMotorCANID, MotorType.kBrushless);
  private final CANSparkMax m_rightFollow = new CANSparkMax(DrivetrainConstants.kRightMotor2CANID, MotorType.kBrushless);
  // Create Encoders
  public final RelativeEncoder e_rightEncoder = m_rightLead.getEncoder();
  public final RelativeEncoder e_leftEncoder = m_leftLead.getEncoder();
  // Create Drive
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftLead,m_rightLead);


  // Creates a new subsystem.
  public Drivetrain() {
    
    // Makes the follow motors follow the lead motor
    m_leftFollow.follow(m_leftLead);
    m_rightFollow.follow(m_rightLead);
    
    // Inverts 1 Motor to make the robot drive forward
    m_rightLead.setInverted(true);
    m_leftLead.setInverted(false);
  }
/* 
  public Command arcadeDrive(double FWD, double Rotate){
    return run(()->{
      m_drive.arcadeDrive(FWD, Rotate);
    });
  }
*/
  public Command arcadeDriveCommand(DoubleSupplier fwd, DoubleSupplier rot) {
    // A split-stick arcade command, with forward/backward controlled by the left hand, and turning controlled by the right.
    return run(() -> m_drive.arcadeDrive(fwd.getAsDouble(), rot.getAsDouble()))
        .withName("arcadeDrive");
  }
  public Command driveRotateCommand(double degrees, double Speed){
    return runOnce(() ->{
        m_drive.arcadeDrive(Speed, degrees);
    })
    .finallyDo(interuppted -> m_drive.stopMotor());

  }

  public void setPosition(){
  double drivePosition = SmartDashboard.getNumber("EnterPosition", 0.0);
  m_leftLead.set(drivePosition);
  m_rightLead.set(drivePosition);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Left Encoder Value", e_leftEncoder.getPosition());
    SmartDashboard.putNumber("Left Encoder Velocity", e_leftEncoder.getVelocity());
    SmartDashboard.putNumber("Right Encoder Value", e_rightEncoder.getPosition());
    SmartDashboard.putNumber("Right Encoder Velocity", e_rightEncoder.getVelocity());
    setPosition();
  }

}
