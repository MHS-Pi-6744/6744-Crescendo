// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import frc.lib.PIDGains;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static class DrivetrainConstants {
    public static final int kLeftMotorCANID = 1;
    public static final int kLeftMotor2CANID = 2;
    public static final int kRightMotorCANID = 3;
    public static final int kRightMotor2CANID = 4;

    public static final boolean kLeftEncoderReversed = false;
    public static final boolean kRightEncoderrevesed = false;

// Physical robot parameters
    public static final int kEncoderCPR = 42;  // Drivetrain NEO motor encoder Counts per revolution
    public static final double kGearRatio = 8.48; // Drivetrain toughbox mini gear ratio
    public static final double kWheelDiameter = Units.inchesToMeters(6);
    
    
    // Encoder count conversion on the spark max for NEOs from rotations to SI units 
    public static final double kEncoderDistanceConversionFactor = 
    ((double) (Math.PI*kWheelDiameter)/(kGearRatio));
    public static final double kEncoderVelocityConversionFactor = 
    ((double) (Math.PI*kWheelDiameter)/(60*kGearRatio));
  }

public static class ArmConstants {     
    public static final int kLeftArmCanId = 7;
    public static final boolean kLeftArmInverted = false;
    public static final int kRightArmCanId = 8;
    public static final boolean kRightArmInverted = false;
    
    // controller settings for both arm motor controls
    public static final int kArmCurrentLimit = 40;
    public static final double kSoftLimitReverse = -20;
    public static final double kSoftLimitForward = 100;
    public static final double kMaxOutput = 1; 
    public static final double kMinOutput = -1;
    public static final double maxRPM = 5700;   // ??????????? units
    public static final double kPositionFactor = 2.0625;
    public static final double kVelocityFactor = 2.0625 / 60.0;
   
    // public static final PIDGains kArmPositionGains = new PIDGains(0.004, 0.0, 0);

    // PID coefficients for initialization
    public static final double kPinitial = 5e-5; 
    public static final double kIinitial = 1e-6;
    public static final double kDinitial = 0; 
    public static final double kIzinitial = 0; 
    public static final double kFFinitial = 0.000156; 
    

    // Smart Motion Coefficients for initialization
    public static final double maxVelinitial = 30; // deg/sec
    public static final double maxAccinitial = 60;  // deg/sec/sec

    // Target arm positions
    public static final double kHomePosition = 0;
    public static final double kScoringPosition = 90;
    
  }

  public static class IntakeConstants {
    public static final int Intake_CANID = 6;
    public static final double k_intakeSpeed = 0.7;
    public static final int Shooter_CANID = 5;
    public static final double k_shooterSpeed = 0.5;
    public static final double k_slowShooter = 0.1;
  }
  /*public static class ClimberConstants {
    public static final int Climber_CANID = 10;
    public static final double k_climbSpeed = 1.0;
  }*/
  



  
 // Operator input constants - RM
  public static class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kDriverController2Port = 1;
  }

  public static class AutoConstants {
    public static final double kDriveSpeed = .5;
    public static final double kTimeoutSeconds = 3;
  }
}
