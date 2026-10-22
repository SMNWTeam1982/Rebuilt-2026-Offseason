package frc.robot.subsystems.drive.Intake;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANBus.IntakeIDs;
import frc.robot.Constants.TunerConstants;
import frc.robot.Constants.TunerConstants.IntakeTunables;;

public class IntakeSubsystem extends SubsystemBase {
  private final SparkMax leadIntakeMotor =
      new SparkMax(IntakeIDs.LEAD_INTAKE_MOTORID, MotorType.kBrushless);
  private final SparkMax followerIntakeMotor =
      new SparkMax(IntakeIDs.LEAD_INTAKE_MOTORID, MotorType.kBrushless);
  private final SparkMax IntakeMotor = new SparkMax(IntakeIDs.INTAKE_MOTORID, MotorType.kBrushless);

  public IntakeSubsystem() {
    leadIntakeMotor.configure(
        TunerConstants.IntakeTunables.INTAKE_LEAD_MOTOR_CONFIG,
        ResetMode.kResetSafeParameters,
        PersistMode.kPersistParameters);

    followerIntakeMotor.configure(
        TunerConstants.IntakeTunables.INTAKE_LEAD_MOTOR_CONFIG.follow(
            IntakeIDs.LEAD_INTAKE_MOTORID,
            true), // set the motor to follow the leader and invert its input
        ResetMode.kResetSafeParameters,
        PersistMode.kPersistParameters);
  }

  public Command feedIntakeIn() {
    return startEnd(
        () -> {
          IntakeMotor.set(IntakeTunables.INTAKE_FEED_IN_SPEED);
        },
        () -> {
          IntakeMotor.set(0);
        });
  }

  public Command stopIntake() {
    return runOnce(() -> IntakeMotor.set(0));
  }

  public Command feedIntakeOut() {
    return startEnd(
        () -> {
          IntakeMotor.set(IntakeTunables.INTAKE_FEED_OUT_SPEED);
        },
        () -> {
          IntakeMotor.set(0);
        });
  }

  public Command moveIntakeIn() {
    return startEnd(
        () -> {
          leadIntakeMotor.set(IntakeTunables.INTAKE_EXTEND_SPEED);
        },
        () -> {
          leadIntakeMotor.set(0);
        });
  }

  public Command moveIntakeOut() {
    return startEnd(
        () -> {
          leadIntakeMotor.set(IntakeTunables.INTAKE_RETRACT_SPEED);
        },
        () -> {
          leadIntakeMotor.set(0);
        });
  }
}
