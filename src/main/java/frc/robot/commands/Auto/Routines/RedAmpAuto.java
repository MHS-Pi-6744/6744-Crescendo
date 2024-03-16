package frc.robot.commands.Auto.Routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Auto.Base.Drivetrain.DriveDistance;
import frc.robot.commands.Auto.Base.Drivetrain.DriveRotation;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ShooterSubsystem;

public class RedAmpAuto extends SequentialCommandGroup{
    public RedAmpAuto(Drivetrain drive, ShooterSubsystem shooter){
        addCommands(
            // Drive far enough to go to amp
            new DriveDistance(1.93294, 0.5, drive),
            //Rotate to Amp
            new DriveRotation(90, -0.3, drive),
            //drive to the amp
            new DriveDistance(0.75, -0.5, drive),
            // Drop Note in
            // new shooterCommand(),
            // Move away
            new DriveDistance(0.5, 0.5, drive),
            // Rotate away
            new DriveRotation(90, 0.3, drive),
            // Drive away 
            new DriveDistance(1.5, 0.5, drive)
        );
    }
    
}
