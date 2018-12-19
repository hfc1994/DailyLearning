package HeadFirst.DesignPattern.CommandPattern;

import HeadFirst.DesignPattern.CommandPattern.Commands.*;

/**
 * Created by user-hfc on 2018/4/17.
 */
public class RemoteLoader
{
    public static void main(String... args)
    {
        RemoteControl remoteControl = new RemoteControl();

        Light light = new Light();
        GarageDoor garageDoor = new GarageDoor();
        Stereo stereo = new Stereo();

        LightOnCommand lightOnCommand = new LightOnCommand(light);
        LightOffCommand lightOffCommand = new LightOffCommand(light);

        GarageDoorOpenCommand garageDoorOpenCommand = new GarageDoorOpenCommand(garageDoor);
        GarageDoorCloseCommand garageDoorCloseCommand = new GarageDoorCloseCommand(garageDoor);

        StereoOnWithCDCommand stereoOnWithCDCommand = new StereoOnWithCDCommand(stereo);
        StereoOffWithCDCommand stereoOffWithCDCommand = new StereoOffWithCDCommand(stereo);


        remoteControl.setCommand(0, lightOnCommand, lightOffCommand);
        remoteControl.setCommand(1, garageDoorOpenCommand, garageDoorCloseCommand);
        remoteControl.setCommand(2, stereoOnWithCDCommand, stereoOffWithCDCommand);

        System.out.println("---------remoteControl------------");

        remoteControl.onButtonWasPushed(0);
        remoteControl.offButtonWasPushed(0);
        remoteControl.onButtonWasPushed(1);
        remoteControl.offButtonWasPushed(1);
        remoteControl.onButtonWasPushed(2);
        remoteControl.offButtonWasPushed(2);

        remoteControl.undoButtonWasPushed();

        System.out.println(remoteControl.toString());
    }
}
