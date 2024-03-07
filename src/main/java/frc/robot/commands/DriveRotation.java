package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.Command;

public class DriveRotation extends Command {
    private final Drivetrain m_drive;
    private final double m_rotation;
    private final double m_speed;

    public DriveRotation(double rotations, double speed, Drivetrain drive) {
        m_rotation = rotations;
        m_speed = speed;
        m_drive = drive;
        addRequirements(m_drive);
    }

    @Override
    public void initialize() {
      m_drive.resetEncoders();
      m_drive.arcadeDrive(0, 0);
    }
  
    @Override
    public void execute() {
      m_drive.arcadeDrive(m_speed, 0);
    }
  
    @Override
    public void end(boolean interrupted) {
      m_drive.arcadeDrive(0, 0);
    }
  
    @Override
    public boolean isFinished() {
      return Math.abs(m_drive.getAbsoluteAverageEncoderDistance()) >= m_rotation;
    }

}