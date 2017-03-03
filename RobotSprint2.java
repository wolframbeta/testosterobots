/*
  Tasks Spring 2:
  1. Move forward as close to 3 meters in the straightest line possible
  2. Move servo to certain angle and back (ex. start point 90 degrees move to
  180 degrees and backwards).
  3. Have sensors read 2 distances: one greater than 30cm and
  one less than 30 cm.
  4. Have a motor run indefinitely until stopped by a bump sensor.
*/

//Import needed pacakges
import rxtxrobot.*;
import java.util.Scanner;

// Initialize RobotSpring2 class
public class RobotSprint2
{

  public static void main(String[] args) {

      //Creates a new instance of the robot class
      RXTXRobot robot = new ArduinoNano();
      Scanner input = new Scanner(System.in);
      char cChoose;

      //Set port and connect the robot
      robot.setPort("/dev/tty.usbmodem9");
      robot.connect();

      //Attach every motor needed

      //Motor1 boom
      robot.attachMotor(RXTXRobot.MOTOR1, 5);
      //Motor2 left side
      robot.attachMotor(RXTXRobot.MOTOR2, 6);
      //Motot3 right side
      robot.attachMotor(RXTXRobot.MOTOR3, 7);


       //Cycle that iterates while a selection is diferent from 9
       do
       {
           cChoose = displayMenu(input);

           switch (cChoose){

             case '1':
                    //moveForward(robot);
                      break;
             case '2':
                    //moveServo(robot);
                      break;
             case '3':
                    //readDistances(robot);
                      break;
             case '4':
                    //moveInfinitely(robot);
                      break;
             case '5':
                      moveBoom(input, robot);
                      break;
             case '6':
                      getTemperature(robot);
                      break;
             case '9':
                      robot.close();
                      break;
             default:
                      System.out.println("Please enter a valid menu option");
            }

  //It will return to the user if any other option is pressed
       } while (cChoose != '9');


   }

   //Main menu
   public static char displayMenu(Scanner input){

		  char cChoice;
		  System.out.print("\nWelcome to Sprint's 2 Robot Menu\n========================\nChoose from the following menu options:\n");
		  System.out.println("1) Move 3 meters forward");
		  System.out.println("2) Move servo");
		  System.out.println("3) Read distances");
		  System.out.println("4) Move indefinitely");
      System.out.println("5) Move boom");
      System.out.println("6) Get temperature");
      System.out.println("9) Exit");
		  System.out.print("Enter option:\n ");
		  cChoice = input.next().charAt(0);

		  return cChoice;

    }

    //Moving the robot forward 3 meters
    public static void moveForward(RXTXRobot r){

      /*
      Functions for moving the required motors until the robot is near 3 meters
      //Has the motor's name and pin number connected.
      //Attach as much as motors needed
      robot.attachMotor(RXTXRobot.MOTOR1, 5);
      //Function for moving THE MOTOR forward. First parameter is the name.
      //Second, speed(I think top speed is 500, should play with this).
      //Third, time motor will move in miniseconds, if 0 it will move indefinitely
      //until another command is reached

      robot.runMotor(RXTXRobot.MOTOR1, 255, 3300);


      */
    }

    //Function for moving servo motors
    public static void moveServo(){



    }

    public static void readDistances(){



    }

    public static void moveInfinitely(){


    }
    public static void moveBoom(Scanner scn, RXTXRobot r){

      char cOption;
      System.out.println("Is the boom up?\n Y/N:");
      cOption = scn.next().charAt(0);

      if(cOption == 'Y' || cOption == 'y'){

          System.out.println("Dropping boom...");
          //Test which command elevates or drops it
          //  robot.runMotor(RXTXRobot.MOTOR1, -255, 3500);
          //  robot.runMotor(RXTXRobot.MOTOR1, 255, 3300);


      }

      else if (cOption == 'N' || cOption == 'n'){

          System.out.println("Elevating boom... ");
          //Test which command elevates or drops it
          //  robot.runMotor(RXTXRobot.MOTOR1, -255, 3500);
          //  robot.runMotor(RXTXRobot.MOTOR1, 255, 3300);

      }

      else {

          System.out.print("Incorrect option, exiting Function");
          return;

      }

    }

    public static void getTemperature(RXTXRobot r){

      int[] readings = new int[5];
      int sum = 0;
      double intercept = 747.63;
      double slope = -7.42;
      double temperature = 0.0;


      for(int i = 0; i < readings.length ; i++){

        readings[i] = getThermistorReading(r);
        sum += readings[i];

      }

      temperature = (sum/readings.length - intercept ) / slope;
      System.out.printf("\nExterior temperature = %.2f C ", temperature);

    }

    public static int getThermistorReading(RXTXRobot r) {

      int sum = 0;
      int readingCount = 10;
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

}
    //robot.attachMotor(RXTXRobot.MOTOR1, 5);
    //  robot.runMotor(RXTXRobot.MOTOR1, -255, 3500);
    //  robot.runMotor(RXTXRobot.MOTOR1, 255, 3300);
      //robot.runMotor(RXTXRobot.MOTOR1, 0, 0);

// Move servo to certain angle and back

/*
      robot.moveServo(RXTXRobot.SERVO1, 180); //Desired angle

*/
//Have sensors read 2 distances: one greater than 30cm and one less than 30 cm.
/*RFID sensor
      RFID irSensor = robot.get

*/

//Run motor indefinitely until stopped by a bump sensor.
      /*
      AnalogPin bumpPin = robot.getAnalogPin(Int pin number));

      while (bumpPin.getValue == 0){

        robot.runMotor(RXTXRobot.MOTOR1, 255, 0);

      }
      robot.runMotor(RXTXRobot.MOTOR1, 0, 0);


      */
