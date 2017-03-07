//Import rxtxrobot package
import rxtxrobot.*;
//import java.io.*;

public class probeTest
{

  public static RXTXRobot robot;
  //Your main method, where your program starts
  public static void main(String[] args) {
      //Connect to the arduino
      robot = new ArduinoUno();
      int[] readings = new int[10];
      robot.setPort("/dev/tty.usbmodem28");
      robot.connect();
      int sum = 0;

      //Get the average thermistor reading
      //PrintWriter writer = new PrintWriter("readings.txt");
    //  int thermistorReading = 0;
      for(int i = 0; i < 10; i++){

        //thermistorReading = getThermistorReading();
        readings[i] = getThermistorReading();
        sum += readings[i];

        //writer.println("Trial number: "+ i);
      //  writer.println("The probe read the value: " + readings[i]);
      //  writer.println("In volts: " + (thermistorReading * (5.0/1023.0)));
        System.out.println("Trial number: "+ i);
        System.out.println("The probe read the value: " + readings[i]);
        System.out.println("In volts: " + (readings[i] * (5.0/1023.0)));
      }
      System.out.println(sum/10);
      System.out.println("");
      //writer.close();
      //Print the results

      robot.close();
  }


  public static int getThermistorReading() {
    int sum = 0;

    int readingCount = 10;
    //Read the analog pin values ten times, adding to sum each time
    for (int i = 0; i < readingCount; i++) {
       //Refresh the analog pins so we get new readings
        robot.refreshAnalogPins();
        int reading = robot.getAnalogPin(0).getValue();
        sum += reading;
}
    //Return the average reading
    return sum / readingCount;
}

}
