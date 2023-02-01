// FRC2106 Junkyard Dogs - Swerve Drive Base Code

package frc.robot.auto.commands;
import java.util.function.Consumer;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ResetPose;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.util.Constants.DriveConstants;

// Runs a given pp-trajectory as a command 
public class TrajectoryWeaver extends SequentialCommandGroup{
    
    Consumer<ChassisSpeeds> chassisSpeeds;

    // Constructor that obtains required values
    public TrajectoryWeaver(SwerveSubsystem swerveSubsystem,PIDController xController,
    PIDController yController,  PIDController ppthetaController,
    PathPlannerTrajectory pptrajectory, Boolean isFirstPath){
      
        // Tell theta PID controller that its a circle
        ppthetaController.enableContinuousInput(-Math.PI, Math.PI);

        // Check if first path
        if(isFirstPath){
           // Reset robot odometry before movement 
          addCommands(new ResetPose(swerveSubsystem, pptrajectory.getInitialPose()));
        }

        addCommands(
            // Commands to run sequentially
            
            new SequentialCommandGroup(

              //                                              .......
              //       Yay ASKI art                          ...........
              //                                             <|><|><|>
              // Going insane with PPSwerveControllerCommand (0) o (0)
              // Fixed it... Java was being weird

              // Use Path Planner to move the swerve modules by letting it call setModuleStates
              new PPSwerveControllerCommand(pptrajectory, swerveSubsystem::getPose, DriveConstants.kDriveKinematics, xController, yController, ppthetaController, swerveSubsystem::setModuleStates, swerveSubsystem),
              
              // Tell driver station that command is running
             // new ReportWarning("Trajectory weaver: " + pptrajectory.toString(), true),
              // Stop all module movement
              new InstantCommand(() -> swerveSubsystem.stopModules())

            )
        ); 
        
        
        
        

    }

    public TrajectoryWeaver(SwerveSubsystem swerveSubsystem, PIDController xController, PIDController yController,
        PIDController ppThetaController, Trajectory trajectory, TrajectoryConfig trajectoryConfig, boolean b) {
    }
}