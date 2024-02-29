// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.DriveDistance;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;



/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 * 
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drive = new Drivetrain();
  private final IntakeSubsystem m_intake = new IntakeSubsystem();
  //private final ArmSubsystem m_armSubsystem = new ArmSubsystem();
  private final ShooterSubsystem m_shoot = new ShooterSubsystem();
  
  //private final RetractArmCommand retractArm = new RetractArmCommand();
  private final Command m_driveDistance = new DriveDistance(1, .3, m_drive);
  // negative speed moves backwards

  // The autonomous routines
  //private final Command m_dropAndGo = Autos.dropAndGoAuto(m_drive,m_intake);
  

  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();



  // The driver's controller
  CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
  CommandXboxController m_driverController2 = new CommandXboxController(OIConstants.kDriverController2Port);
 
public RobotContainer(){
  // Configure the button bindings using method below
  configureButtonBindings();

  //m_chooser.setDefaultOption("pickup", m_intake);
  

  // Add commands to the autonomous command chooser
  m_chooser.setDefaultOption("Drive Distance", m_driveDistance);
  m_chooser.addOption("Nothing", new WaitCommand(5));

  // Put the chooser on the dashboard
  Shuffleboard.getTab("Autonomous").add(m_chooser);
}
  
private void configureButtonBindings() {
    


    // Configure default commands
    // Set the default drive command to split-stick arcade drive
    m_drive.setDefaultCommand(
      m_drive.arcadeDriveCommand(
      () -> -m_driverController.getLeftY(), () -> -m_driverController.getRightX()));

    // Pickup a note with the right trigger
    m_driverController2.rightTrigger().whileTrue(m_intake.pickupCommand());
    m_driverController2.leftTrigger().whileTrue(m_intake.releaseCommand());
    //both shoot and pickup
    m_driverController2.rightBumper().whileTrue(new ParallelRaceGroup(m_intake.pickupCommand(), m_shoot.shooterCommand()));
    m_driverController2.leftBumper().whileTrue(new ParallelRaceGroup(m_intake.releaseCommand(), m_shoot.shooterReleaseCommand()));
   
}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return m_chooser.getSelected();
  
  }
}