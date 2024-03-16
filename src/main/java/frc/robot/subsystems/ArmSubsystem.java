// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;



import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.cameraserver.CameraServer;




/*  -----Need to set up second motor as a follower
 * 
 */

public class ArmSubsystem extends SubsystemBase {

  
  // Declare variables

  public DigitalInput islimitSwitch = new DigitalInput(0);

  public boolean limitSwitchTF;






  


    
 

  /** Creates and initializes a new ArmSubsystem. */
  public ArmSubsystem() {

    
  
   


  }

  
  public void SetLimitSwitch(){
    if(islimitSwitch.get()){
      limitSwitchTF = true;
    } else {
      limitSwitchTF = false;
    }
   
  }



  

  




   
  

  @Override
  public void periodic() { // This method will be called once per scheduler run
    
    new InstantCommand(() -> SetLimitSwitch());

    SmartDashboard.putBoolean("Limit Switch1", limitSwitchTF);




    



    


    

    

    

    
    
   
  }
}
