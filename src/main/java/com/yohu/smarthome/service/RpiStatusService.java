package com.yohu.smarthome.service;

import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;
import com.yohu.smarthome.bean.GpioStatusRsp;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huyong
 * @since 2018/11/29
 */
@Service
public class RpiStatusService {
    public List<GpioStatusRsp> getRpiStatus() {
        List<GpioStatusRsp> statusRspList = new ArrayList<>();
        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate code)
//        final Console console = new Console();

        // print program title/header
//        console.title("<-- The Pi4J Project -->", "GPIO Input (ALL PINS) Example");

        // allow for user to exit program using CTRL-C
//        console.promptForExit();

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // by default we will use gpio pin PULL-UP; however, if an argument
        // has been provided, then use the specified pull resistance
        PinPullResistance pull = CommandArgumentParser.getPinPullResistance(
                PinPullResistance.PULL_DOWN  // default pin pull resistance if no pull argument found
                // argument array to search in
        );

        List<GpioPinDigitalInput> provisionedPins = new ArrayList<>();

        // provision GPIO input pins
        for (Pin pin : RaspiPin.allPins()) {
            try {
                GpioPinDigitalInput provisionedPin = gpio.provisionDigitalInputPin(pin, pull);
                provisionedPin.setShutdownOptions(true); // unexport pin on program shutdown
                provisionedPins.add(provisionedPin);     // add provisioned pin to collection
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }

        // prompt user that we are ready
//        console.println(" ... Successfully provisioned all GPIO input pins");
//        console.emptyLine();
//        console.box("The GPIO input pins states will be displayed below.");
//        console.emptyLine();

        // display pin states for all pins
        for (GpioPinDigitalInput input : provisionedPins) {
//            console.println(" [" + input.toString() + "] digital state is: " + ConsoleColor.conditional(
//                    input.getState().isHigh(), // conditional expression
//                    ConsoleColor.GREEN,        // positive conditional color
//                    ConsoleColor.RED,          // negative conditional color
//                    input.getState()));

            GpioStatusRsp rsp = new GpioStatusRsp();

            rsp.setName(input.getName());
            rsp.setValue(input.getState().getName());
            statusRspList.add(rsp);
        }

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        gpio.shutdown();
        return statusRspList;
    }
}
