
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.DigitalInput;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkPIDController;

public class ArmSubsystem extends SubsystemBase {
    private final CANSparkMax m_armMotor1
     = new CANSparkMax(ArmConstants.kArmMotorCANID, MotorType.kBrushless);
    private final CANSparkMax m_armMotor2
     = new CANSparkMax(ArmConstants.kArmMotor2CANID, MotorType.kBrushless);
    private final RelativeEncoder m_armEncoder;
    private final SparkPIDController m_armPidController;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    
    static final double armSpeed = .25;

    
    /*
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
    */

    public ArmSubsystem() {

        m_armMotor1.restoreFactoryDefaults();
        m_armMotor2.restoreFactoryDefaults();

        m_armMotor2.follow(m_armMotor1);

        m_armMotor1.setInverted(false);
        m_armMotor2.setInverted(true);

        m_armMotor1.setIdleMode(IdleMode.kBrake);
        m_armMotor2.setIdleMode(IdleMode.kBrake);

        m_armMotor1.setSmartCurrentLimit(30,60);
        m_armMotor2.setSmartCurrentLimit(30,60);

        m_armPidController = m_armMotor1.getPIDController();

        m_armEncoder = m_armMotor1.getEncoder();

         // PID coefficients
        kP = 0.1; 
        kI = 1e-4;
        kD = 1; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;

        // set PID coefficients
        m_armPidController.setP(kP);
        m_armPidController.setI(kI);
        m_armPidController.setD(kD);
        m_armPidController.setIZone(kIz);
        m_armPidController.setFF(kFF);
        m_armPidController.setOutputRange(kMinOutput, kMaxOutput);

        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);
        SmartDashboard.putNumber("Set Rotations", 0);
    }
/* 
    private void resetEncoders() {
        m_armEncoder.setPosition(0);
    }
*/
    @Override
    public void periodic() {
    // This method will be called once per scheduler run
    
        // read PID coefficients from SmartDashboard
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 0);
        double min = SmartDashboard.getNumber("Min Output", 0);
        double rotations = SmartDashboard.getNumber("Set Rotations", 0);

        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if((p != kP)) { m_armPidController.setP(p); kP = p; }
        if((i != kI)) { m_armPidController.setI(i); kI = i; }
        if((d != kD)) { m_armPidController.setD(d); kD = d; }
        if((iz != kIz)) { m_armPidController.setIZone(iz); kIz = iz; }
        if((ff != kFF)) { m_armPidController.setFF(ff); kFF = ff; }
        if((max != kMaxOutput) || (min != kMinOutput)) { 
        m_armPidController.setOutputRange(min, max); 
        kMinOutput = min; kMaxOutput = max; 
        }

        m_armPidController.setReference(rotations, CANSparkMax.ControlType.kPosition);
        
        SmartDashboard.putNumber("SetPoint", rotations);
        SmartDashboard.putNumber("ProcessVariable", m_armEncoder.getPosition());
        }

    }
