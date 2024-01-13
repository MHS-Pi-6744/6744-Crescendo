// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import frc.robot.Constants.OIConstants;
import frc.robot.commands.Autos;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.UpperArmIntake;
import edu.wpi.first.wpilibj2.command.Command;
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
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_myRobot = new Drivetrain();
  private final IntakeSubsystem m_intake = new IntakeSubsystem();
  private final ArmSubsystem m_armSubsystem = new ArmSubsystem();
  private final UpperArmIntake m_UpperArmIntake = new UpperArmIntake();

  // The autonomous routines
  private final Command m_dropAndGo = Autos.dropAndGoAuto(m_myRobot,m_intake);

  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  // The driver's controller
  CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
 
public RobotContainer(){
  // Configure the button bindings
  configureButtonBindings();

  // Configure default commands
  // Set the default drive command to split-stick arcade drive
  m_myRobot.setDefaultCommand(
    m_myRobot.arcadeDriveCommand(
        () -> -m_driverController.getLeftY(), () -> -m_driverController.getRightX()));
  
  // Add commands to the autonomous command chooser
  m_chooser.setDefaultOption("Drop and Go", m_dropAndGo);

  // Put the chooser on the dashboard
  Shuffleboard.getTab("Autonomous").add(m_chooser);

}
  
private void configureButtonBindings() {
    

    // Pickup a cube with the X button
    // m_driverController.x().whileTrue(m_intake.pickupCommand());
    m_driverController.rightTrigger().whileTrue(m_intake.pickupCommand());
    // Shoot the cube with the Y button
    //m_driverController.y().whileTrue(m_intake.releaseCommand());
    m_driverController.leftTrigger().whileTrue(m_intake.releaseCommand());



    // Run the arm motor in reverse for x seconds
    m_driverController.leftBumper().onTrue(m_armSubsystem.retractArmCommand().withTimeout(3));
    // Run the arm motor for x seconds
    m_driverController.rightBumper().onTrue(m_armSubsystem.extendArmCommand().withTimeout(3));


    //Upper Intake 

     m_driverController.a().whileTrue(m_UpperArmIntake.UpperpickupCommand());
     m_driverController.x().whileTrue(m_UpperArmIntake.UpperreleaseCommand());
   
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