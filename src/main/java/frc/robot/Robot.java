// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
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

      double rollerSpeed = SmartDashboard.getNumber("Roller Speed", 0.0);
      double topSpeed = SmartDashboard.getNumber("Top Shooter Speed", 0.0);
      double bottomSpeed = SmartDashboard.getNumber("Bottom Shooter Speed", 0.0);


      if(controller.getAButton() ){
        roller.set(VictorSPXControlMode.PercentOutput, rollerSpeed);
      }

      if(controller.getBButton() ){
        topMotor.set(topSpeed);
        bottomMotor.set(bottomSpeed);
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
