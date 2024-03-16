package frc.robot.subsystems;

import frc.robot.Constants.ClimberConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class ClimberSubsystem extends SubsystemBase {
        
    private final CANSparkMax m_climbMotor = new CANSparkMax(ClimberConstants.Climber_CANID, MotorType.kBrushless);

    
    public Command climbCommand() {
        return startEnd(
                // Start the intake motor
                () -> m_climbMotor.set(ClimberConstants.k_climbSpeed), 
                // Stop the motor when command ends
                () -> m_climbMotor.set(0)); 
        
        }

}
