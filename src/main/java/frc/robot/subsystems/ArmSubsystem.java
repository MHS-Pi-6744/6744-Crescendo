
package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.UpperIntakeConstants;

//import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
    private final WPI_TalonSRX m_armMotor = new WPI_TalonSRX(UpperIntakeConstants.kArmMotorCANID);
    //private final DigitalInput topLimitSwitch = new DigitalInput(0);

    static final double armSpeed = .25;
    
    
    public Command retractArmCommand(){
        return startEnd(
            () -> m_armMotor.set(-armSpeed),  
            () -> m_armMotor.stopMotor());
    }

    public Command extendArmCommand(){
        return startEnd(
            () -> m_armMotor.set(armSpeed),
            () -> m_armMotor.stopMotor());
    }

    @Override
    public void periodic() {
      // This method will be called once per scheduler run
    }
}
