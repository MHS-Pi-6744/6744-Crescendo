// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {


  public static class UpperIntakeConstants {
    public static final int kArmMotorCANID = 7;
    public static final int kRightGateMotorCANID = 8;
    public static final int kLeftGateMotorCANID = 9;
  }
  
  public static class IntakeConstants {
    public static final int Intake_CANID = 6;
    public static final double k_intakeSpeed = 1;
    }
  
  public static class DrivetrainConstants {
    public static final int kLeftMotorCANID = 1;
    public static final int kLeftMotor2CANID = 2;
    public static final int kRightMotorCANID = 3;
    public static final int kRightMotor2CANID = 4;

    public static final boolean kLeftEncoderReversed = false;
    public static final boolean kRightEncoderrevesed = true;

// Physical robot parameters
    public static final int kEncoderCPR = 42;  // NEO motor encoder Counts per revolution
    public static final double kGearRatio = 7.31; // Toughbox mini gear ratio
    public static final double kWheelDiameter = Units.inchesToMeters(6);
    
    // Encoder count conversion on the spark max for NEOs from rotations to SI units 
    public static final double kEncoderDistanceConversionFactor = 
    ((double) (Math.PI*kWheelDiameter)/(kGearRatio));
    public static final double kEncoderVelocityConversionFactor = 
    ((double) (Math.PI*kWheelDiameter)/(60*kGearRatio));
  }
  
  //Operator input constants - RM
  public static class OIConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class AutoConstants {
    public static final double kDriveSpeed = .5;
    public static final double kTimeoutSeconds = 3;
  }

}
