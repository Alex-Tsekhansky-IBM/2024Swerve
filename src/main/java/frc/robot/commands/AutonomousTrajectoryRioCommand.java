// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPRamseteCommand;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.Constants.Swerve;

/**
 * Runs trajectory. The command will not update initial odometry of the robot.
 * That should be done by a separate command preceding this one.
 */
public class AutonomousTrajectoryRioCommand extends PPSwerveControllerCommand {
  /** Creates a new AutonomousTrajectoryRioCommand. */

  TrajectoryConfig config;

  PathPlannerTrajectory trajectoryPath;
  
  public AutonomousTrajectoryRioCommand(PathPlannerTrajectory trajectoryPath) {
    // Use addRequirements() here to declare subsystem dependencies.
    
    
    super(
      trajectoryPath,
      RobotContainer.driveSubsystem::getPose,
      Constants.Swerve.SWERVE_KINEMATICS,
      new PIDController(Constants.Swerve.DRIVE_MOTOR_KP,
                        Constants.Swerve.DRIVE_MOTOR_KI,
                        Constants.Swerve.DRIVE_MOTOR_KD),
      new PIDController(Constants.Swerve.DRIVE_MOTOR_KP,
                        Constants.Swerve.DRIVE_MOTOR_KI,
                        Constants.Swerve.DRIVE_MOTOR_KD),
      new PIDController(Constants.Swerve.ANGLE_MOTOR_KP,
                        Constants.Swerve.ANGLE_MOTOR_KI,
                        Constants.Swerve.ANGLE_MOTOR_KD),
      RobotContainer.driveSubsystem::setDesiredStates,
      false,
      RobotContainer.driveSubsystem
    );
    this.trajectoryPath = trajectoryPath;
  }

  // Run trajectory with known maximum velocity and acceleration
  /**
   * @param trajectoryName Filename containing trajectory without .path
   * @param maxVelocity    Maximum velocity m/s
   * @param maxAcceleration  Maximum acceleration m/s^2
   */
  public AutonomousTrajectoryRioCommand(String trajectoryName, double maxVelocity, double maxAcceleration){
    this(PathPlanner.loadPath(trajectoryName, new PathConstraints(maxVelocity, maxAcceleration)));
    System.out.println("initalized trajectory: "+ trajectoryName + "V:"+maxVelocity+" A:"+maxAcceleration);
  }

  // Run trajectory with default maximum velocity and acceleration
  /**
   * @param trajectoryName Filename containing trajectory without .path
   */
  public AutonomousTrajectoryRioCommand(String trajectoryName){
    this(PathPlanner.loadPath(trajectoryName, 
      new PathConstraints(Swerve.MAX_SPEED, Swerve.MAX_ACCELERATION)));
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    super.initialize();
    System.out.println("Auto trajectory initialized");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Update robot odometry

    //System.out.println("O");

    //TODO: Currently update odometry in DriveSubsystem, but it is really used here.
    //RobotContainer.driveSubsystem.updateTrajectoryOdometry();

    super.execute();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    System.out.println("*** End trajectory command. Interrupted:"+interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    //RobotContainer.driveSubsystem.updateOdometry();

    return super.isFinished();
  }
}
