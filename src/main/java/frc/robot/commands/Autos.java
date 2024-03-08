package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;

public class Autos extends SequentialCommandGroup{
    public Autos(Drivetrain drive){
        addCommands(
            new DriveDistance(0.5, 0.3, drive),
            new DriveRotation(0.5, 0.3, drive),
            new DriveDistance(0.5, 0.3, drive),
            new DriveDistance(0.5, -0.3, drive),
            new DriveRotation(0.5, -0.3, drive),
            new DriveDistance(0.5, -0.3, drive)
        );
    }
    
}
