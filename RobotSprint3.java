//Import needed pacakges
import rxtxrobot.*;
import java.util.Scanner;

// Initialize RobotSpring2 class
public class RobotSprint3
{

  private static final int DISTANCE = 45;

  public static void main(String[] args) {

      //Creates a new instance of the robot class
      RXTXRobot robot = new ArduinoUno();
      int frontPing = 100;
      int rightPing = 0;
      int leftPing = 0;
      int leftGap = 100;


      //Set port and connect the robot
      robot.setPort("/dev/tty.usbmodem73");
      robot.connect();

      //Motor 1 boom
      robot.attachMotor(RXTXRobot.MOTOR3, 11);//should be on pin 7
      //Motor2 right side
      robot.attachMotor(RXTXRobot.MOTOR1, 6);
      //Motor3 left side
      robot.attachMotor(RXTXRobot.MOTOR2, 5);

      //Attach servos
      robot.attachServo(RXTXRobot.SERVO1, 9); //right servo
      robot.attachServo(RXTXRobot.SERVO2, 8); //left servo

      leftPing = leftSensor(robot);

      pingForward(robot, frontPing);
      turnRight(robot, 1725);
      pingForward(robot, frontPing);

      do{
          frontPing = frontSensor(robot);
      }
      while(frontPing <= DISTANCE);
      robot.sleep(500);
      climbRamp(robot);
      //boom
      turnLeft(robot, 1600);
      raiseBoom(robot);
      getTemperature(robot);
      robot.sleep(1500);
      dropBoom(robot);
      moveForward(robot, 3500);

      do{

        leftPing = leftSensor(robot);
        moveForward(robot, 1300);

      } while( leftPing < 60 );

      turnLeft(robot, 1725);
      pingForward(robot, frontPing);
      turnLeft(robot, 1725);
      pingForward(robot, frontPing);
      turnRight(robot, 1725);
      pingForward(robot, frontPing);
      turnRight(robot, 1725);
      robot.close();



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


//Method for raising and dropping the boom

    public static void raiseBoom(RXTXRobot r){

        //Test which command elevates or drops it
        r.runMotor(RXTXRobot.MOTOR3, -255, 3800); //speed -255

    }

    public static void dropBoom(RXTXRobot r){

        //Test which command elevates or drops it
        r.runMotor(RXTXRobot.MOTOR3, 255, 3500); //speed -255

    }


    public static void stop(RXTXRobot r){

        r.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);

    }
    public static void turnRight(RXTXRobot r, int time){

        r.runMotor(RXTXRobot.MOTOR1, 210, RXTXRobot.MOTOR2, -240, time); //150, 175

    }
    public static void turnLeft(RXTXRobot r, int time){

        r.runMotor(RXTXRobot.MOTOR1, -240, RXTXRobot.MOTOR2, 210, time); //150, 175

    }
    public static void pingForward(RXTXRobot r, int ping){

        //int frontSensor = 100;
        r.runMotor(RXTXRobot.MOTOR1, 180, RXTXRobot.MOTOR2, 220, 0); //150, 175
        while (ping > DISTANCE ){

            //r.runMotor(RXTXRobot.MOTOR1, 230, RXTXRobot.MOTOR2, 240, 2000); //150, 175
            ping = frontSensor(r);//r.getPing(4);
            //r.sleep(200);
            System.out.println("Distance:" + ping);
            //System.out.println("Left Disctace:" + leftPing);

        }
        r.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0); //150, 175


    }

//!!!!!!!!!!!!!!! Warning !!!!!!!!!!!!!!!!!
//Every .getPing() declarations needs to be checked with the actual connection on the ArduinoUno
//method that gets the reading for the front ping sensor
    public static int frontSensor(RXTXRobot r){

      int distance = 0;
      int readings = 0;
      for (int x=0; x < 3; ++x){
          //Read the ping sensor value, which is connected to pin 12
          //r.refreshDigitalPins();
          //System.out.println("Response: " + r.getPing(10) + " cm");//Right sensor 10, ffront 4
          readings += r.getPing(4);

          r.sleep(100);
      }

        distance = readings / 3;
        System.out.println(distance);
        return distance;

    }

//method that gets the reading for the right ping sensor
    public static int rightSensor(RXTXRobot r){

      int distance = 0;
      int readings = 0;
      for (int x=0; x < 3; ++x){
          //Read the ping sensor value, which is connected to pin 12
          //r.refreshDigitalPins();
          //System.out.println("Response: " + r.getPing(10) + " cm");//Right sensor 10, ffront 4
          readings += r.getPing(10);
          r.sleep(100);
      }

        distance = readings / 3;
        return distance;

    }

//method that gets the reading for the left ping sensor
    public static int leftSensor(RXTXRobot r){

      int distance = 0;
      int readings = 0;
     for (int x=0; x < 3; ++x){
          //Read the ping sensor value, which is connected to pin 12
          //r.refreshDigitalPins();
          //System.out.println("Response: " + r.getPing(10) + " cm");//Right sensor 10, ffront 4
          readings += r.getPing(7);
          r.sleep(100);
      }

        distance = readings / 3;
        return distance;

    }
    public static void climbRamp(RXTXRobot r){

        r.runMotor(RXTXRobot.MOTOR1, 300, RXTXRobot.MOTOR2, 340, 5500); //150, 175

    }
    public static void moveForward(RXTXRobot r, int time){

        r.runMotor(RXTXRobot.MOTOR1, 210, RXTXRobot.MOTOR2, 250, time); //150, 175

    }


}
