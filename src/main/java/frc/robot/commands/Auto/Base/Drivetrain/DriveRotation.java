package frc.robot.commands.Auto.Base.Drivetrain;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class DriveRotation extends Command {
    private final Drivetrain m_drive;
    private final double m_rotation;
    private final double m_speed;

    public DriveRotation(double angle, double speed, Drivetrain drive) {
        m_rotation = angle;
        m_speed = speed;
        m_drive = drive;
        addRequirements(m_drive);
    }

    @Override
    public void initialize() {
      m_drive.resetEncoders();
      m_drive.arcadeDrive(0, 0);
      m_drive.zeroGyro();
    }
  
    @Override
    public void execute() {
      m_drive.arcadeDrive(0, m_speed);
    }
  
    @Override
    public void end(boolean interrupted) {
      m_drive.arcadeDrive(0, 0);
    }
  
    @Override
    public boolean isFinished() {
      return (Math.abs(m_drive.gyroGetAngle()) >= m_rotation);  
    }

}
