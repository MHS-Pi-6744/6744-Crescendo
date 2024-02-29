package frc.robot.subsystems;


import frc.robot.Constants.IntakeConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class ShooterSubsystem extends SubsystemBase {
    private final CANSparkMax m_shooterMotor = new CANSparkMax(IntakeConstants.Shooter_CANID, MotorType.kBrushed);

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
