//Import needed pacakges
import rxtxrobot.*;
import java.util.Scanner;

// Initialize RobotSpring2 class
public class RobotSprint3
{

  private static final int DISTANCE = 25;

  public static void main(String[] args) {

      //Creates a new instance of the robot class
      RXTXRobot robot = new ArduinoUno();
      int frontPing = 100;
      int rightPing = 100;
      int leftPing = 100;
      int leftGap = 100;

      leftPing = leftGap;
      //Set port and connect the robot
      robot.setPort("/dev/tty.usbmodem36");
      robot.connect();

      //Motor 1 Car antenna
      //robot.attachMotor(RXTXRobot.MOTOR3, 5);//should be on pin 7
      //Motor2 right side
      //robot.attachMotor(RXTXRobot.MOTOR1, 5);
      //Motor3 left side
      //robot.attachMotor(RXTXRobot.MOTOR2, 6);

      //Attach servos
      //robot.attachServo(RXTXRobot.SERVO1, 9);
      //robot.attachServo(RXTXRobot.SERVO2, 10);

//Sprint 3 tasks step by step
      moveForward(robot, frontPing);
      turnDecision(robot, leftPing, rightPing);
      while (leftPing <= 10 && rightPing <= 10){
          moveForward(robot, frontPing);

      }
      if(frontPing && leftPing && rightPing ==)// wait until test to see the reading that we get on the top
      {
          raiseBoom();
          getTemperature();
          dropBoom();
      }
      leftPing = leftSensor(robot);

      while(leftPing == leftGap){
          leftPing = leftSensor(robot);
          moveForward(robot, frontPing);
      }
      turnDecision(robot, leftPing, rightPing);
      moveForward(robot, frontPing);
      turnDecision(robot, leftPing, rightPing);
      moveForward(robot, frontPing);
      turnDecision(robot, leftPing, rightPing);


    }
//!!!!!!!!!!!! Warning !!!!!!!!!!!!!!!
//actual ArduinoUno connections need to be checked before using this method
//Method for reading temperature, it returns a Celsius reading
    public static void getTemperature(RXTXRobot r){

      int[] readings = new int[3];
      int sum = 0;
      double intercept = 711.84;
      double slope = -7.12;
      double temperature = 0.0;
      int average = 0;


      for(int i = 0; i < 3 ; i++){

        readings[i] = getThermistorReading(r);
        sum += readings[i];

      }

      average = sum / 3;
      temperature = (average - intercept) / slope;
      System.out.printf("\nExterior temperature = %.2f C ", temperature);

    }

    //Calling the thermistor reading method for having an ADC code for temperature
    public static int getThermistorReading(RXTXRobot r) {

      int sum = 0;
      int readingCount = 5;
      //Read the analog pin values ten times, adding to sum each time
      for (int i = 0; i < readingCount; i++) {
         //Refresh the analog pins so we get new readings
          r.refreshAnalogPins();
          int reading = r.getAnalogPin(0).getValue();
          sum += reading;
      }
      //Return the average reading
      return sum / readingCount;
  }

//!!!!!!!!!!!! Warning !!!!!!!!!!!!!!!
// The next to method are random declarations of where the car antenna is connected,
//test is needed
//Method for raising and dropping the boom
    public static void raiseBoom(RXTXRobot r){

        //Test which command elevates or drops it
        r.runMotor(RXTXRobot.MOTOR3, -255, 3800); //speed -255

    }

    public static void dropBoom(RXTXRobot r){

        //Test which command elevates or drops it
        r.runMotor(RXTXRobot.MOTOR3, 255, 3500); //speed -255

    }

//method for deciding which side should the robot turn
//!!!!!!!!!!!! Warning !!!!!!!!!!!!!!!
//This code is made with random motor declarations, speeds, and times
//Test is needed to make this method work
    public static void turnDecision(RXTXRobot r, int leftPing, int rightPing){

        rightPing = rightSensor(r);
        leftPing = leftSensor(r);

        if (leftPing < rightPing)
          r.runMotor(RXTXRobot.MOTOR1, 200, 2000);


        else if(rightPing < leftPing)
          r.runMotor(RXTXRobot.MOTOR2, 200, 2000);

    }

    public static void moveForward(RXTXRobot r, int ping){

        int frontSensor = 100;
        while (ping > DISTANCE ){

            ping = frontSensor(r);
            robot.runMotor(RXTXRobot.MOTOR1, 216, RXTXRobot.MOTOR2, 240, 115); //150, 175

        }

    }

//!!!!!!!!!!!!!!! Warning !!!!!!!!!!!!!!!!!
//Every .getPing() declarations needs to be checked with the actual connection on the ArduinoUno
//method that gets the reading for the front ping sensor
    public static int frontSensor(RXTXRobot r){

      int distance = 0;
      int readings = 0;
      for (int x=0; x < 5; ++x){
          //Read the ping sensor value, which is connected to pin 12
          //r.refreshDigitalPins();
          //System.out.println("Response: " + r.getPing(10) + " cm");//Right sensor 10, ffront 4
          readings += r.getPing(10);
          r.sleep(500);
      }

        distance = readings / 5;
        return distance;

    }

//method that gets the reading for the right ping sensor
    public static int rightSensor(RXTXRobot r){

      int distance = 0;
      int readings = 0;
      for (int x=0; x < 5; ++x){
          //Read the ping sensor value, which is connected to pin 12
          //r.refreshDigitalPins();
          //System.out.println("Response: " + r.getPing(10) + " cm");//Right sensor 10, ffront 4
          readings += r.getPing(11);
          r.sleep(500);
      }

        distance = readings / 5;
        return distance;

    }

//method that gets the reading for the left ping sensor
    public static int leftSensor(RXTXRobot r){

      int distance = 0;
      int readings = 0;
      for (int x=0; x < 5; ++x){
          //Read the ping sensor value, which is connected to pin 12
          //r.refreshDigitalPins();
          //System.out.println("Response: " + r.getPing(10) + " cm");//Right sensor 10, ffront 4
          readings += r.getPing(12);
          r.sleep(500);
      }

        distance = readings / 5;
        return distance;

    }

}
