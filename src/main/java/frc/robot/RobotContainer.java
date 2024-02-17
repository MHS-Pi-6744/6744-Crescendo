// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
///*import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.OIConstants;
//import frc.robot.commands.Autos;
//import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
//import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
//import edu.wpi.first.wpilibj2.command.button.JoystickButton;
//import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;



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


  //private final RaceIntake m_Race = new RaceIntake();
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
  //m_chooser.setDefaultOption("Drop and Go", m_dropAndGo);

  // Put the chooser on the dashboard
  Shuffleboard.getTab("Autonomous").add(m_chooser);

}
  
private void configureButtonBindings() {
    


    // Configure default commands
    // Set the default drive command to split-stick arcade drive
    m_drive.setDefaultCommand(
      m_drive.arcadeDriveCommand(
      () -> -m_driverController.getLeftY(), () -> -m_driverController.getRightX()));

    // Pickup a cube with the X button
    // m_driverController.x().whileTrue(m_intake.pickupCommand());
    m_driverController2.rightTrigger().whileTrue(m_intake.pickupCommand());
    // Shoot the cube with the Y button
    //m_driverController.y().whileTrue(m_intake.releaseCommand());
    m_driverController2.leftTrigger().whileTrue(m_intake.releaseCommand());
    //m_driverController.rightBumper().whileTrue(m_intake.shooterCommand()).and(whileTrue(m_intake.pickupCommand()));
    //m_driverController.leftBumper().whileTrue(m_shoot.shooterCommand());
    //both shoot and pickup
    m_driverController2.rightBumper().whileTrue(new ParallelRaceGroup(m_intake.pickupCommand(), m_shoot.shooterCommand()));
    m_driverController2.leftBumper().whileTrue(new ParallelRaceGroup(m_intake.releaseCommand(), m_shoot.shooterReleaseCommand()));



    // Run the arm motor in reverse for x seconds
   // m_driverController.b().onTrue(m_armSubsystem.retractArmCommand().withTimeout(3));
    // Run the arm motor for x seconds
   // m_driverController.a().onTrue(m_armSubsystem.extendArmCommand().withTimeout(3));
   
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