package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants.IntakeConstants;


public class ShooterSubsystem extends SubsystemBase {
    private final CANSparkMax m_shooterMotor = new CANSparkMax(IntakeConstants.Shooter_CANID, MotorType.kBrushless);

/*public ShooterSubsystem() {

    m_shooterMotor.restoreFactoryDefaults();

    m_shooterMotor.setIdleMode(IdleMode.kBrake);

    //m_shooterMotor.setSmartCurrentLimit(30, 30);

    m_shooterMotor.burnFlash();
}*/

    public Command shooterCommand() {
        return startEnd(
                () -> m_shooterMotor.set(IntakeConstants.k_intakeSpeed), 
                () -> m_shooterMotor.stopMotor());
    }   
    public Command shooterReleaseCommand() {
        return startEnd(
                () -> m_shooterMotor.set(-IntakeConstants.k_intakeSpeed), 
                () -> m_shooterMotor.stopMotor());
    }    
    
    
} 
