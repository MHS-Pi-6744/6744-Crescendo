
package frc.robot.subsystems;

import frc.robot.Constants.IntakeConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;



//import frc.robot.Constants;

/**The following is an example of using the INLINE method of calling commands. A seperate command file is not required.
 * See frc.robot.subsystems; 
 * -RM */

public class IntakeSubsystem extends SubsystemBase {
    private final CANSparkMax m_intakeMotor = new CANSparkMax(IntakeConstants.Intake_CANID, MotorType.kBrushless);
    



    /** Returns a command that runs the intake */
    public Command pickupCommand() {
        return startEnd(
                // Start the intake motor
                () -> m_intakeMotor.set(IntakeConstants.k_intakeSpeed), 
                // Stop the motor when command ends
                () -> m_intakeMotor.stopMotor()); 
        }

/** Returns a command that runs the intake in the opposite direction */
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
 