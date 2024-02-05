
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.UpperIntakeConstants;

/**The following is an example of using the INLINE method of calling commands. A seperate command file is not required.
 * See frc.robot.subsystems; 
 * -RM */

public class UpperArmIntake extends SubsystemBase {
    private final WPI_TalonSRX m_UpperintakeMotorL = new WPI_TalonSRX(UpperIntakeConstants.kRightGateMotorCANID);
    private final  WPI_TalonSRX m_UpperintakeMotorR = new WPI_TalonSRX(UpperIntakeConstants.kLeftGateMotorCANID);


    public UpperArmIntake() {
    
   
        m_UpperintakeMotorL.follow(m_UpperintakeMotorR);

        m_UpperintakeMotorR.setInverted(true);
    
      }

 

   
    







  
/** Returns a command that runs the intake */
    public Command UpperpickupCommand() {
        return startEnd(
                // Start the intake motor
                () -> m_UpperintakeMotorR.set(IntakeConstants.k_intakeSpeed), 
                
                // Stop the motor when command ends
                () -> m_UpperintakeMotorR.stopMotor()); 
        }

/** Returns a command that runs the intake in the opposite direction */
    public Command UpperreleaseCommand() {
        return startEnd(
                // Start the intake motor in reverse. se
                () -> m_UpperintakeMotorR.set(-IntakeConstants.k_intakeSpeed), 
                // Stop the motor when command ends
                () -> m_UpperintakeMotorR.stopMotor());
        }


  //public void periodic() {
    // This method will be called once per scheduler run
  
}

