package frc.robot.subsystems.feeder;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANBus.FeederIDs;
import frc.robot.Constants.TunerConstants.FeederTunables;

public class FeederSubsystem extends SubsystemBase{
    private final SparkMax feederMotor = new SparkMax(FeederIDs.FEEDER_MOTOR, MotorType.kBrushless);
    public FeederSubsystem() {}

    public Command startFeeder(){
        return runOnce(() -> {
            feederMotor.set(FeederTunables.ACTIVE_FEEDER_SPEED);
        });
    }
    public Command idleFeeder(){
        return runOnce(() -> {
            feederMotor.set(FeederTunables.IDLE_FEEDER_SPEED);   
        });
    }
}