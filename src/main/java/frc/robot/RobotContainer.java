// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.DriveDistance;
import frc.robot.subsystems.ArmSubsystem;
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
  private final ArmSubsystem m_arm = new ArmSubsystem();
  private final IntakeSubsystem m_intake = new IntakeSubsystem();
  private final ShooterSubsystem m_shoot = new ShooterSubsystem();

  
  private final Command m_driveDistance = new DriveDistance(1, .3, m_drive);
  // negative speed moves backwards

  
  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();



  // The driver's controller
  CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
  CommandXboxController m_driverController2 = new CommandXboxController(OIConstants.kDriverController2Port);

  
 /* Constructor - instantiated in Robot
  *   initializes by configuring button bindings 
   */
public RobotContainer(){
  // Configure the button bindings using method below
  configureButtonBindings();

  // Put the chooser on the dashboard
  Shuffleboard.getTab("Autonomous").add(m_chooser);
  
  // Add commands to the autonomous command chooser
  m_chooser.setDefaultOption("Drive Distance", m_driveDistance);
  m_chooser.addOption("Nothing", new WaitCommand(5));



  /*  MOVED THIS TO BEGINNING OF configureButtonNindings() ???????????????????????????????????
  // set the arm subsystem to run the "runAutomatic" function continuously when no other command is running
  m_arm.setDefaultCommand(new RunCommand(() -> m_arm.runAutomatic(), m_arm));
  */
}
  


  /*configureButtonBindings sets default commands for all the subsystems and sets up commands associated
      with controller inputs. 2 controllers are used:
         m_driverController for drivetrain 
         m_driverController2 for intake/shooter and arm*/

 

private void configureButtonBindings() {

  // Configure default commands
    // Set the default drive command to split-stick arcade drive
      m_drive.setDefaultCommand(
          m_drive.arcadeDriveCommand(
                () -> -m_driverController.getLeftY(), () -> -m_driverController.getRightX())); 

    // set the arm subsystem to run the "runAutomatic" function continuously when no other command is running
      m_arm.setDefaultCommand(new RunCommand(() -> m_arm.runAutomatic(), m_arm));


    
    // Pickup a note with controller 2 right trigger
    m_driverController2.rightTrigger().whileTrue(m_intake.pickupCommand());

    // Release note with controller2 left trigger
    m_driverController2.leftTrigger().whileTrue(m_intake.releaseCommand());

    //Shoot note with controller 2 bumpers both shoot and pickup
    m_driverController2.rightBumper().whileTrue(new ParallelRaceGroup(m_intake.pickupCommand(), m_shoot.shooterCommand()));
    m_driverController2.leftBumper().whileTrue(new ParallelRaceGroup(m_intake.releaseCommand(), m_shoot.shooterReleaseCommand()));
    
    // Move arm to home position with controller 2 Y button
    m_driverController2.y().whileTrue(new InstantCommand(() -> m_arm.setTargetPosition(Constants.ArmConstants.kHomePosition)));

    // Move arm to scoring position with controller 2 A button
    m_driverController2.a().whileTrue(new InstantCommand(() -> m_arm.setTargetPosition(Constants.ArmConstants.kScoringPosition)));


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