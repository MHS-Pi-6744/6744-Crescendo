package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkRelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants.IntakeConstants;


public class ShooterSubsystem extends SubsystemBase {
    private final CANSparkMax m_shooterMotor = new CANSparkMax(IntakeConstants.Shooter_CANID, MotorType.kBrushless);


    private final RelativeEncoder m_shootEncoder;

public ShooterSubsystem() { 

    m_shootEncoder = m_shooterMotor.getEncoder(SparkRelativeEncoder.Type.kHallSensor, 42);

}
/*public ShooterSubsystem() {

    m_shooterMotor.restoreFactoryDefaults();

    m_shooterMotor.setIdleMode(IdleMode.kBrake);

    m_shooterMotor.setSmartCurrentLimit(30, 30);

    m_shooterMotor.burnFlash();
}*/

    public Command shooterCommand() {
        return startEnd(
                () -> m_shooterMotor.set(IntakeConstants.k_shooterSpeed), 
                () -> m_shooterMotor.set(0));
    }   
    public Command slowShooterCommand() {
        return startEnd(
                () -> m_shooterMotor.set(IntakeConstants.k_slowShooter), 
                () -> m_shooterMotor.set(0));
    }   
    public Command shooterReleaseCommand() {
        return startEnd(
                () -> m_shooterMotor.set(-IntakeConstants.k_shooterSpeed), 
                () -> m_shooterMotor.set(0));
    }    

    public Command testCommand(){
        return startEnd(
            () -> m_shooterMotor.set(-IntakeConstants.k_shooterSpeed),
            () -> m_shooterMotor.set(0));
    }


    

 @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_shooterMotor.getOutputCurrent();
    ;

    SmartDashboard.putNumber("Shooter Motor Output", m_shooterMotor.getAppliedOutput());
    SmartDashboard.putNumber("Shooter Motor Current", m_shooterMotor.getOutputCurrent());

    SmartDashboard.putNumber("Shooter Motor P", m_shootEncoder.getPosition());
    SmartDashboard.putNumber("Shooter Motor V", m_shootEncoder.getVelocity());
  }
}

