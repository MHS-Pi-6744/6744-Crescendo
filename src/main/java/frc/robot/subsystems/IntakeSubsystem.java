
package frc.robot.subsystems;

import frc.robot.Constants.IntakeConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


//import frc.robot.Constants;

/**The following is an example of using the INLINE method of calling commands. A seperate command file is not required.
 * See frc.robot.subsystems; 
 * -RM */

public class IntakeSubsystem extends SubsystemBase {
    private final WPI_TalonSRX m_intakeMotor = new WPI_TalonSRX(IntakeConstants.Intake_CANID);
    

  
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
 