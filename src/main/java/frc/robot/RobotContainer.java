// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OIConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.OIConstants.ControllerDevice;
import frc.robot.Devices.Controller;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IMUSubsystem;
import frc.robot.subsystems.SmartDashboardSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  public static final IMUSubsystem imuSubsystem = new IMUSubsystem();

  public static final DriveSubsystem driveSubsystem = new DriveSubsystem();

  public static final SmartDashboardSubsystem smartDashboardSubsystem = new SmartDashboardSubsystem();

  public static Controller driveStick;

  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed

  
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    // Configure driver interface - binding joystick objects to port numbers
    configureDriverInterface();

    // Configure the trigger bindings
    configureBindings();
  }

   /**
   * Use this method to define your controllers depending on the
   * {@link DriveInterface}
   */
  private void configureDriverInterface() {
    driveStick = new Controller(ControllerDevice.DRIVESTICK);
    System.out.println("Driver interface configured");
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is
    // pressed,
    // cancelling on release.
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

}

/**
 * Swerve control inputs will be supplied as lambdas to the manual drive command.
 * This way it would be easy to be switch between different input devices such as joystick or Xbox controller.
 * In order to do a switch appropriate analog axis of the device would need to be specificed in one of the three
 * methods below.
 */

 /**
  * Logitech joystick returns -1 of Y axis when pushed all the way forward and +1 when pushed all the way backwards.
  * Since the robot is oriented on the X axis of the field, pushing joystick forward means robot to go forward
  * along the X axis.
  * @return
  */
private double getDriverXAxis() {
    return -driveStick.getLeftStickY();
}

private double getDriverYAxis() {
    return -driveStick.getLeftStickX();
}

private double getDriverOmegaAxis() {
    return -driveStick.getLeftStickOmega();
}

  /**
  * Make sure motors move robot forward with positive power and encoders increase with positive power
  * To enable put call to this method in configureBindings method
  */
  private void testCalibrateMotorsAndEncodersButtonBindings() {

    new JoystickButton(driveStick, 5)
        .whileTrue(new InstantCommand(() -> RobotContainer.driveSubsystem.testDriveMotorEncoderPhase(0)))
        .whileFalse(new InstantCommand(() -> RobotContainer.driveSubsystem.stopDriveMotor(0)));

    new JoystickButton(driveStick, 6)
        .whileTrue(new InstantCommand(() -> RobotContainer.driveSubsystem.testDriveMotorEncoderPhase(1)))
        .whileFalse(new InstantCommand(() -> RobotContainer.driveSubsystem.stopDriveMotor(1)));

    new JoystickButton(driveStick, 3)
        .whileTrue(new InstantCommand(() -> RobotContainer.driveSubsystem.testDriveMotorEncoderPhase(2)))
        .whileFalse(new InstantCommand(() -> RobotContainer.driveSubsystem.stopDriveMotor(2)));

    new JoystickButton(driveStick, 4)
        .whileTrue(new InstantCommand(() -> RobotContainer.driveSubsystem.testDriveMotorEncoderPhase(3)))
        .whileFalse(new InstantCommand(() -> RobotContainer.driveSubsystem.stopDriveMotor(3)));

    new JoystickButton(driveStick, 11)
        .whileTrue(new InstantCommand(() -> RobotContainer.driveSubsystem.testAngleMotorEncoderPhase(2)))
        .whileFalse(new InstantCommand(() -> RobotContainer.driveSubsystem.stopAngleMotor(2)));

    new JoystickButton(driveStick, 12)
        .whileTrue(new InstantCommand(() -> RobotContainer.driveSubsystem.testAngleMotorEncoderPhase(3)))
        .whileFalse(new InstantCommand(() -> RobotContainer.driveSubsystem.stopAngleMotor(3)));

    new JoystickButton(driveStick, 10)
        .whileTrue(new InstantCommand(() -> RobotContainer.driveSubsystem.testAngleMotorEncoderPhase(1)))
        .whileFalse(new InstantCommand(() -> RobotContainer.driveSubsystem.stopAngleMotor(1)));

    new JoystickButton(driveStick, 9)
        .whileTrue(new InstantCommand(() -> RobotContainer.driveSubsystem.testAngleMotorEncoderPhase(0)))
        .whileFalse(new InstantCommand(() -> RobotContainer.driveSubsystem.stopAngleMotor(0)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
