// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkBase.SoftLimitDirection;
import com.revrobotics.CANSparkLowLevel.MotorType;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkRelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;


public class ArmSubsystem extends SubsystemBase {

  // Declare variables
  private CANSparkMax m_leftmotor;
  private CANSparkMax m_rightmotor;
  private RelativeEncoder m_leftencoder;
  private RelativeEncoder m_rightencoder;
  private SparkPIDController m_Leftcontroller;
  private SparkPIDController m_Rightcontroller;
  private double m_setpoint;
  private Timer m_timer;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;

  
  /* Creates and initializes a new ArmSubsystem. This is run on power up. 
        It's not run when mode is switched from disabled to enabled */
  public ArmSubsystem() {
  
    m_leftmotor = new CANSparkMax(ArmConstants.kLeftArmCanId, MotorType.kBrushless);
    m_rightmotor = new CANSparkMax(ArmConstants.kRightArmCanId, MotorType.kBrushless);

    m_leftmotor.restoreFactoryDefaults();  
    m_rightmotor.restoreFactoryDefaults();
    
    // Set up the motors
    m_leftmotor.setInverted(true);
    m_rightmotor.setInverted(false);
 
    m_leftmotor.setSmartCurrentLimit(ArmConstants.kArmCurrentLimit);
    m_rightmotor.setSmartCurrentLimit(ArmConstants.kArmCurrentLimit);

    m_leftmotor.setIdleMode(IdleMode.kBrake);
    m_rightmotor.setIdleMode(IdleMode.kBrake);

    m_leftmotor.enableSoftLimit(SoftLimitDirection.kForward, true);
    m_leftmotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
    m_rightmotor.enableSoftLimit(SoftLimitDirection.kForward, true);
    m_rightmotor.enableSoftLimit(SoftLimitDirection.kReverse, true);

    m_leftmotor.setSoftLimit(SoftLimitDirection.kForward, (float) ArmConstants.kSoftLimitForward);
    m_leftmotor.setSoftLimit(SoftLimitDirection.kReverse, (float) ArmConstants.kSoftLimitReverse);
    m_rightmotor.setSoftLimit(SoftLimitDirection.kForward, (float) ArmConstants.kSoftLimitForward);
    m_rightmotor.setSoftLimit(SoftLimitDirection.kReverse, (float) ArmConstants.kSoftLimitReverse);

    // set up the motor encoders - sets position to 0 (the arm resting on the brace at intake position)
    m_leftencoder = m_leftmotor.getEncoder(SparkRelativeEncoder.Type.kHallSensor, 42);
    m_leftencoder.setPositionConversionFactor(ArmConstants.kPositionFactor);
    m_leftencoder.setVelocityConversionFactor(ArmConstants.kVelocityFactor);
    m_leftencoder.setPosition(0);
    m_rightencoder = m_rightmotor.getEncoder(SparkRelativeEncoder.Type.kHallSensor, 42);
    m_rightencoder.setPositionConversionFactor(ArmConstants.kPositionFactor);
    m_rightencoder.setVelocityConversionFactor(ArmConstants.kVelocityFactor);
    m_rightencoder.setPosition(0);

    // set up the motor controllers
    m_Leftcontroller = m_leftmotor.getPIDController();
    m_Rightcontroller = m_rightmotor.getPIDController();
  

    // Initialize setpoint to the left encoder position
    m_setpoint = m_leftencoder.getPosition();

    /* 
    m_leftmotor.burnFlash();
    m_rightmotor.burnFlash();
    */
   
    m_timer = new Timer();
    m_timer.start();

  }

  // Set the target position - setpoint should be in degrees
  
  public void setTargetPosition(double _setpoint) {
    if (_setpoint != m_setpoint) {
      m_setpoint = _setpoint;
    }
  }


  public void setArmCoastMode(){
    m_leftmotor.setIdleMode(IdleMode.kCoast);
    m_rightmotor.setIdleMode(IdleMode.kCoast);
  }

  public void setArmBrakeMode(){
    m_leftmotor.setIdleMode(IdleMode.kBrake);
    m_rightmotor.setIdleMode(IdleMode.kBrake);
  }

  public void setArmStart(){
    m_leftencoder.setPosition(90);
    m_rightencoder.setPosition(90);
  }


  @Override
  public void periodic() { // This method will be called once per scheduler run
    SmartDashboard.putNumber("Left Arm Position", m_leftencoder.getPosition());
    SmartDashboard.putNumber("Right Arm Position", m_rightencoder.getPosition());
    SmartDashboard.putNumber("Arm SetPoint", m_setpoint);
    
    SmartDashboard.putNumber("Left Arm Velocity", m_leftencoder.getVelocity());
    SmartDashboard.putNumber("Right Arm Velocity", m_rightencoder.getVelocity());

    SmartDashboard.getNumber(" Get Intake Position", Constants.ArmConstants.kScoringPosition);
    SmartDashboard.getNumber(" Get Home Position", Constants.ArmConstants.kHomePosition);
   
// display PID coefficients on SmartDashboard
    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);
    SmartDashboard.putNumber("Max Output", kMaxOutput);
    SmartDashboard.putNumber("Min Output", kMinOutput);

    // display Smart Motion coefficients
    SmartDashboard.putNumber("Max Velocity", maxVel);
    SmartDashboard.putNumber("Min Velocity", minVel);
    SmartDashboard.putNumber("Max Acceleration", maxAcc);
    SmartDashboard.putNumber("Allowed Closed Loop Error", allowedErr);
    SmartDashboard.putNumber("Set Position", 0);
    SmartDashboard.putNumber("Set Velocity", 0);

    // button to toggle between velocity and smart motion modes
    SmartDashboard.putBoolean("Mode", true);
    
    // Smart Motion display:
    // display PID coefficients on SmartDashboard
    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);
    SmartDashboard.putNumber("Max Output", kMaxOutput);
    SmartDashboard.putNumber("Min Output", kMinOutput);

    // display Smart Motion coefficients
    SmartDashboard.putNumber("Max Velocity", maxVel);
    SmartDashboard.putNumber("Min Velocity", minVel);
    SmartDashboard.putNumber("Max Acceleration", maxAcc);
    SmartDashboard.putNumber("Allowed Closed Loop Error", allowedErr);
    SmartDashboard.putNumber("Set Position", 0);
    SmartDashboard.putNumber("Set Velocity", 0);

    // button to toggle between velocity and smart motion modes
    SmartDashboard.putBoolean("Mode", true);

  }
}
