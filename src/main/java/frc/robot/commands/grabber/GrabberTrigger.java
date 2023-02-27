// FRC2106 Junkyard Dogs - Swerve Drive Base Code

package frc.robot.commands.grabber;
import frc.robot.subsystems.GrabberSubsystem;
import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class GrabberTrigger extends CommandBase {

  private GrabberSubsystem subsystem;
  private Supplier<Double> input;

  public GrabberTrigger(GrabberSubsystem subsystem, Supplier<Double> input) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.subsystem = subsystem;
    addRequirements(subsystem);
    this.input = input;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    subsystem.setAngleSetpoint(input.get() * 90 + 38);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
