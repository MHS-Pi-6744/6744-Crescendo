
package frc.robot.subsystems;

import frc.robot.Constants.IntakeConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import com.revrobotics.SparkRelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;


/**The following is an example of using the INLINE method of calling commands. A seperate command file is not required.
 * See frc.robot.subsystems; 
 * -RM */

public class IntakeSubsystem extends SubsystemBase {
        
    private final CANSparkMax m_intakeMotor = new CANSparkMax(IntakeConstants.Intake_CANID, MotorType.kBrushless);
    
    private final RelativeEncoder m_intakeEncoder;

public IntakeSubsystem() { 

    m_intakeEncoder = m_intakeMotor.getEncoder(SparkRelativeEncoder.Type.kHallSensor, 42);

}
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
                () -> m_intakeMotor.set(0)); 
        
        }


    public Command releaseCommand() {
        return startEnd(
                // Start the intake motor in reverse. se
                () -> m_intakeMotor.set(-IntakeConstants.k_intakeSpeed), 
                // Stop the motor when command ends
                () -> m_intakeMotor.set(0));
        }
        


 @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_intakeMotor.getOutputCurrent();
    ;

    SmartDashboard.putNumber("Intake Motor Output", m_intakeMotor.getAppliedOutput());
    SmartDashboard.putNumber("Intake Motor Current", m_intakeMotor.getOutputCurrent());

    SmartDashboard.putNumber("Intake Motor P", m_intakeEncoder.getPosition());
    SmartDashboard.putNumber("Intake Motor V", m_intakeEncoder.getVelocity());
  }
}
 