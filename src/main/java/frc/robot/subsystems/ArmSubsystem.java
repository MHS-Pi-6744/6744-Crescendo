
package frc.robot.subsystems;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SparkmaxArmConstants;
import frc.robot.Constants.UpperIntakeConstants;
import java.net.CacheRequest;

//import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkLowLevel;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class ArmSubsystem extends SubsystemBase {
    private final WPI_TalonSRX m_armMotor = new WPI_TalonSRX(UpperIntakeConstants.kArmMotorCANID);
    private final CANSparkBase m_sparkMotor = new CANSparkMax(SparkmaxArmConstants.kSparkMaxCANID,MotorType.kBrushless);
    public final RelativeEncoder m_encoder = m_sparkMotor.getEncoder();
    static final double armSpeed = .25;


  

    /* 
    class EncoderVal {
      private ShuffleboardTab tab = Shuffleboard.getTab("Encoder");
      private GenericEntry Encoder = tab.add("EncoderValue", m_encoder).getEntry();
    }
    */

    
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

    public void setPosition(){
      double sparkPosition = SmartDashboard.getNumber("EnterPosition", 0.0);
      m_sparkMotor.set(sparkPosition);
    }

    

      

    @Override
    public void periodic() {
      // This method will be called once per scheduler run

      SmartDashboard.putNumber("Encoder Value", m_encoder.getPosition());
      SmartDashboard.putNumber("Encoder Velocity", m_encoder.getVelocity());
      setPosition();
    }
}
