
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;
//import edu.wpi.first.wpilibj.Encoder;
import com.revrobotics.CANSparkMax;
import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


public class Drivetrain extends SubsystemBase {

  // The drive motors
  private final CANSparkMax leftMotor1 = new CANSparkMax(DrivetrainConstants.kLeftMotorCANID, MotorType.kBrushless);
  private final CANSparkMax leftMotor2 = new CANSparkMax(DrivetrainConstants.kLeftMotor2CANID, MotorType.kBrushless);
  private final CANSparkMax rightMotor1 = new CANSparkMax(DrivetrainConstants.kRightMotorCANID, MotorType.kBrushless);
  private final CANSparkMax rightMotor2 = new CANSparkMax(DrivetrainConstants.kRightMotor2CANID, MotorType.kBrushless);

  // The robot's drive
  private final DifferentialDrive m_drive = new DifferentialDrive(leftMotor1,rightMotor1);


  /** Creates a new subsystem. */
  public Drivetrain() {
    
    //This is the proper way to group CAN motors. See Spark-max example code - RM
    leftMotor2.follow(leftMotor1);
    rightMotor2.follow(rightMotor1);

    
    //Flip values so robot moves forward when stick-forward/LEDs-green */
    rightMotor1.setInverted(true);
    leftMotor1.setInverted(false);
  }
/* 
  public Command arcadeDrive(double FWD, double Rotate){
    return run(()->{
      m_drive.arcadeDrive(FWD, Rotate);
    });
  }
*/
  public Command arcadeDriveCommand(DoubleSupplier fwd, DoubleSupplier rot) {
    // A split-stick arcade command, with forward/backward controlled by the left
    // hand, and turning controlled by the right.
    return run(() -> m_drive.arcadeDrive(fwd.getAsDouble(), rot.getAsDouble()))
        .withName("arcadeDrive");
  }

/*
  public Command driveDistanceCommand(double DistanceM, double Speed){
    return runOnce(() -> {
      m_RightEncoder.reset();
      m_LeftEncoder.reset();
    })
    .andThen(run(() -> m_drive.arcadeDrive(Speed, 0))
    .until(() -> Math.max(m_LeftEncoder.getDistance(), m_RightEncoder.getDistance()) >= DistanceM))
    .finallyDo(interuppted -> m_drive.stopMotor());
     
  }
  */
  public Command driveRotateCommand(double degrees, double Speed){
    return runOnce(() ->{
        m_drive.arcadeDrive(Speed, degrees);
    })
    .finallyDo(interuppted -> m_drive.stopMotor());

  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
