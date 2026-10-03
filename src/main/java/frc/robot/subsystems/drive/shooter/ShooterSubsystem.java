package frc.robot.subsystems.drive.shooter;

import static edu.wpi.first.units.Units.Amps;
	import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Pounds;
import static edu.wpi.first.units.Units.Seconds;

import org.littletonrobotics.junction.AutoLogOutput;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.CANBus.ShooterIDs;
import frc.robot.Constants.TunerConstants.ShooterTunables;
import yams.gearing.GearBox;
import yams.gearing.MechanismGearing;
import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
	import yams.mechanisms.config.FlyWheelConfig;
import yams.mechanisms.velocity.FlyWheel;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
import yams.motorcontrollers.local.SparkWrapper;


public class ShooterSubsystem extends SubsystemBase{
  private final SparkMax leadMotor = new SparkMax(ShooterIDs.LEAD_SHOOTER_MOTORID, MotorType.kBrushless);
 private final SparkMax followerMotor = new SparkMax(ShooterIDs.FOLLOWER_SHOOTER_MOTORID, MotorType.kBrushless); 
 private SmartMotorControllerConfig smcConfig = new SmartMotorControllerConfig(this)
  .withControlMode(ControlMode.CLOSED_LOOP)
  // Feedback Constants (PID Constants)
  .withClosedLoopController(0.001, 0, 0.00005)
  .withSimClosedLoopController(.001, 0, 0.00005)
  // Feedforward Constants
  .withFeedforward(new SimpleMotorFeedforward(0, 0.128, 0))
  .withSimFeedforward(new SimpleMotorFeedforward(0, .128, 0))
  // Telemetry name and verbosity level
  .withTelemetry("leftShooterMotor", TelemetryVerbosity.HIGH)
  // Gearing from the motor rotor to final shaft.
  .withGearing(new MechanismGearing(GearBox.fromReductionStages(3, 4)))
  // Motor properties to prevent over currenting.
  .withMotorInverted(false)
  .withIdleMode(MotorMode.COAST)
  .withStatorCurrentLimit(Amps.of(40))
  .withClosedLoopRampRate(Seconds.of(0.25))
  .withOpenLoopRampRate(Seconds.of(0.25))
  .withMomentOfInertia(Inches.of(4), Pounds.of(1))
  .withFollowers(Pair.of(followerMotor, true));

  private SmartMotorController SmartMotorController = new SparkWrapper(leadMotor, DCMotor.getNEO(1), smcConfig);

  private final FlyWheelConfig shooterConfig = new FlyWheelConfig()
  .withDiameter(Inches.of(4))
  // Telemetry name and verbosity for the arm.
  .withTelemetry("Flywheel", TelemetryVerbosity.HIGH);
  // Shooter Mechanism
  private FlyWheel shooter = new FlyWheel(shooterConfig, SmartMotorController);

 
public ShooterSubsystem(){} 
/**
   * Gets the current velocity of the shooter.
   *
   * @return Shooter velocity.
   */
  public AngularVelocity getRPM() {return
  shooter.getSpeed();}
  
  /**
   * Runs the shooter at the given velocity.
   *
   * @param speed Speed to set.
  /** */
  public void setVelocitySetpoint(AngularVelocity speed) 
    {shooter.setMechanismVelocitySetpoint(speed);}

  public Command run(AngularVelocity speed)
   {return shooter.run(speed);}
  
  public Command setDutyCycle(double dutyCycle) {
        return shooter.set(dutyCycle);
    }

  public Command stopCommand() {
        return shooter.set(0);
    }
 
@Override
  public void periodic() {
    // This method will be called once per scheduler run
    shooter.updateTelemetry();
  }
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    shooter.simIterate();
  }

}
