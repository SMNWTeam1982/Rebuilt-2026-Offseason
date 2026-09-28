package frc.robot.subsystems.feeder;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANBus.FeederIDs;

public class FeederSubsystem extends SubsystemBase{
    private final SparkMax feederMotor = new SparkMax(FeederIDs.FEEDER_MOTOR, MotorType.kBrushless);
    public FeederSubsystem() {

    }


    
}
