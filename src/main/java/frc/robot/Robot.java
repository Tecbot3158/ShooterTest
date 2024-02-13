// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private VictorSPX roller;
  private XboxController controller;
  private CANSparkMax topMotor;
  private CANSparkMax bottomMotor;


/*
 * AMP Configutation
  * roller: 0.3
 * bottom speed: .23
 * top speed: .08
 * 
 * Speaker Configutation
  * roller: 0.4
 * bottom speed: .4
 * top speed: .5
 * 
 * TRAP
 *  pegado
 *  r
 *  bs
 *  ts
 */

  private ShuffleboardTab tab = Shuffleboard.getTab("Shooter Speeds"); 
   private GenericEntry rollerSpeed =
      tab.add("Roller Speed", 0.0)
         .getEntry();
   
  private GenericEntry bottomSpeed =
   tab.add("Bottom Speed", 0.0)
        .getEntry();

  private GenericEntry topSpeed =
   tab.add("Top Speed", 0.0)
        .getEntry();
  

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    roller = new VictorSPX(15);
    controller = new XboxController(0);
    topMotor = new CANSparkMax(11, MotorType.kBrushless);
    bottomMotor = new CANSparkMax(51, MotorType.kBrushless);
    
  }



  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
      
      double rs = rollerSpeed.getDouble(0.0);
      double ts = topSpeed.getDouble(0.0);
      double bs = bottomSpeed.getDouble(0.0);

      if(controller.getAButton() ){
        roller.set(VictorSPXControlMode.PercentOutput, rs);
      }

      if(controller.getBButton() ){
        topMotor.set(ts);
        bottomMotor.set(bs);
      }

      if( controller.getXButton() ){
        roller.set(VictorSPXControlMode.PercentOutput, 0);
        topMotor.set(0);
        bottomMotor.set(0);
      }
  }

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
