
package frc.robot.subsystems;

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


public class ArmSubsystem extends SubsystemBase {
    private final WPI_TalonSRX m_armMotor = new WPI_TalonSRX(UpperIntakeConstants.kArmMotorCANID);
    private final CANSparkBase m_sparkMotor = new CANSparkMax(SparkmaxArmConstants.kSparkMaxCANID,MotorType.kBrushless);

    private final RelativeEncoder m_encoder = m_sparkMotor.getEncoder();

   




    


    
   
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

    /* 
    public Command sparkMaxDistance(double DistanceM, double Speed){
        return runOnce(() -> {
          .reset();
        })
        .andThen(run(() -> m_sparkMotor(Speed, 0))
        .until(() -> Math.max(m_Encoder.getDistance() >= DistanceM))
        .finallyDo(interuppted -> m_sparkMotor.stopMotor()));
      }
      */

    @Override
    public void periodic() {
      // This method will be called once per scheduler run
    }
}
