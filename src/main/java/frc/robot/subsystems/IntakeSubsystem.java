
package frc.robot.subsystems;

import frc.robot.Constants.IntakeConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;


/**The following is an example of using the INLINE method of calling commands. A seperate command file is not required.
 * See frc.robot.subsystems; 
 * -RM */

public class IntakeSubsystem extends SubsystemBase {
        
    private final CANSparkMax m_intakeMotor = new CANSparkMax(IntakeConstants.Intake_CANID, MotorType.kBrushless);
    
/*public IntakeSubsystem() {

    m_intakeMotor.restoreFactoryDefaults();

    m_intakeMotor.setIdleMode(IdleMode.kBrake);

    //m_intakeMotor.setSmartCurrentLimit(30, 30);

    m_intakeMotor.burnFlash();
}*/



    public Command pickupCommand() {
        return startEnd(
                // Start the intake motor
                () -> m_intakeMotor.set(IntakeConstants.k_intakeSpeed), 
                // Stop the motor when command ends
                () -> m_intakeMotor.stopMotor()); 
        }


    public Command releaseCommand() {
        return startEnd(
                // Start the intake motor in reverse. se
                () -> m_intakeMotor.set(-IntakeConstants.k_intakeSpeed), 
                // Stop the motor when command ends
                () -> m_intakeMotor.stopMotor());
        }
        


 @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
 