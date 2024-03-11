// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkBase.SoftLimitDirection;
import com.revrobotics.CANSparkLowLevel.MotorType;

import javax.swing.plaf.TreeUI;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.SparkRelativeEncoder;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.PIDGains;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;



/*  -----Need to set up second motor as a follower
 * 
 */

public class ArmSubsystem extends SubsystemBase {

  
  // Declare variables
  private CANSparkMax m_leftmotor;
  private CANSparkMax m_rightmotor;
  private RelativeEncoder m_leftencoder;
  private RelativeEncoder m_rightencoder;
  private SparkPIDController m_Leftcontroller;
  private SparkPIDController m_Rightcontroller;
  private double m_setpoint;
  private TrapezoidProfile m_profile;
  private Timer m_timer;
  private TrapezoidProfile.State m_startState;
  private TrapezoidProfile.State m_endState;
  private TrapezoidProfile.State m_targetState;



  

   // Create 2 SPARK MAX controllers   ----  Moved out of constructor to be consistent with WIPLib examples and Drivetrain

    
 

  /** Creates and initializes a new ArmSubsystem. */
  public ArmSubsystem() {
  
    m_leftmotor = new CANSparkMax(ArmConstants.kLeftArmCanId, MotorType.kBrushless);
    m_rightmotor = new CANSparkMax(ArmConstants.kRightArmCanId, MotorType.kBrushless);

    m_leftmotor.restoreFactoryDefaults();  
    m_rightmotor.restoreFactoryDefaults();
    
    // Set forward direction
    m_leftmotor.setInverted(true);
    m_rightmotor.setInverted(false);
 
    //Set the right motor to follow the left motor
    //m_rightmotor.follow(m_leftmotor);

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

    // set up the motor encoders including conversion factors to convert to radians and radians per
    // second for position and velocity ------  Seems like degrees would be easier units to work with?
    m_leftencoder = m_leftmotor.getEncoder(SparkRelativeEncoder.Type.kHallSensor, 42);
    m_leftencoder.setPositionConversionFactor(ArmConstants.kPositionFactor);
    m_leftencoder.setVelocityConversionFactor(ArmConstants.kVelocityFactor);
    m_leftencoder.setPosition(0);
    m_rightencoder = m_rightmotor.getEncoder(SparkRelativeEncoder.Type.kHallSensor, 42);
    m_rightencoder.setPositionConversionFactor(ArmConstants.kPositionFactor);
    m_rightencoder.setVelocityConversionFactor(ArmConstants.kVelocityFactor);
    m_rightencoder.setPosition(0);

    

    m_Leftcontroller = m_leftmotor.getPIDController();
    m_Rightcontroller = m_rightmotor.getPIDController();
    PIDGains.setSparkMaxGains(m_Leftcontroller, ArmConstants.kArmPositionGains);
    PIDGains.setSparkMaxGains(m_Rightcontroller, ArmConstants.kArmPositionGains);

    m_setpoint = 0;
    m_leftencoder.setPosition(0);
    m_rightencoder.setPosition(0);

    /* DOES NOT WORK 

    m_Leftcontroller.setOutputRange(0, 0.5);
    m_Rightcontroller.setOutputRange(0, 0.5);

    */

    m_leftmotor.burnFlash();
    m_rightmotor.burnFlash();

    m_setpoint = ArmConstants.kHomePosition;

    m_timer = new Timer();
    m_timer.start();

    updateMotionProfile();

  }

  /**
   * Sets the target position and updates the motion profile if the target position changed.
   *
   * @param _setpoint The new target position in wheel revolutions.
   */
  
  public void setTargetPosition(double _setpoint) {
    if (_setpoint != m_setpoint) {
      m_setpoint = _setpoint;
      updateMotionProfile();
    }
  }

/* 
  public Command CommandSetTargetPosition(){
    return this.runOnce(() -> setTargetPosition(m_setpoint));
  }

  public void setIntakePosition(double m_setpointIntake) {
    if (m_setpoint != m_setpointIntake) {
        m_setpoint = m_setpointIntake;
      updateMotionProfile();
    }
  }

  public Command CommandSetShootPosition(){
    return this.runOnce(() -> setIntakePosition(m_setpoint));
  }

*/



  /**
   * Update the motion profile variables based on the current setpoint and the pre-configured motion
   * constraints.
   */
  private void updateMotionProfile() {
    m_startState = new TrapezoidProfile.State(m_leftencoder.getPosition(), m_leftencoder.getVelocity());
    m_endState = new TrapezoidProfile.State(m_setpoint, 0.0);
    m_profile = new TrapezoidProfile(ArmConstants.kArmMotionConstraint);
    m_timer.reset();
  }

  /**
   * Drives the arm to a position using a trapezoidal motion profile. This function is usually
   * wrapped in a {@code RunCommand} which runs it repeatedly while the command is active.
   *
   * <p>This function updates the motor position control loop using a setpoint from the trapezoidal
   * motion profile. The target position is the last set position with {@code setTargetPosition}.
   */
  public void runAutomatic() {
    double elapsedTime = m_timer.get();
    if (m_profile.isFinished(elapsedTime)) {
      m_targetState = new TrapezoidProfile.State(m_setpoint, 0.0);
    } else {
      m_targetState = m_profile.calculate(elapsedTime, m_startState, m_endState);
    }
     

            

    m_Leftcontroller.setReference(
        m_targetState.position, CANSparkMax.ControlType.kPosition, 0, 0);
  
  
    m_Rightcontroller.setReference(
        m_targetState.position, CANSparkMax.ControlType.kPosition, 0, 0);
  }

  /**
   * Drives the arm using the provided power value (usually from a joystick). This also adds in the
   * feedforward value which can help counteract gravity.
   *
   * @param _power The motor power to apply.
   */
  public void runManual(double _power) {
    // reset and zero out a bunch of automatic mode stuff so exiting manual mode happens cleanly and
    // passively
    m_setpoint = m_leftencoder.getPosition();
    updateMotionProfile();

    // update the feedforward variable with the newly zero target velocity
    /* Turn off feed forward 
    m_feedforward =
        ArmConstants.kArmFeedforward.calculate(
            m_leftencoder.getPosition() + ArmConstants.kArmZeroCosineOffset, m_targetState.velocity);
            */

    // set the power of the motor
    m_leftmotor.set(_power);
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

    

    

    

    
    
   
  }
}
