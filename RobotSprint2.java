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
      RXTXRobot robot = new ArduinoUno();
      Scanner input = new Scanner(System.in);
      char cChoose;

      //Set port and connect the robot
      robot.setPort("/dev/tty.usbmodem33");
      robot.connect();

      //Attach every motor needed

      //Motor 1 Car antenna
      //robot.attachMotor(RXTXRobot.MOTOR1, 7);
      //Motor2 right side
      robot.attachMotor(RXTXRobot.MOTOR1, 5);
      //Motor3 left side
      robot.attachMotor(RXTXRobot.MOTOR2, 6);

      //Attach servos
      robot.attachServo(RXTXRobot.SERVO1, 9);

       //Cycle that iterates while a selection is diferent from 9
       do
      {
           cChoose = displayMenu(input);

           switch (cChoose){

             case '1':
                      moveRobot(input,robot);
                      break;
             case '2':
                      moveServo(input,robot);
                      break;
             case '3':
                      readDistances(robot);
                      break;
             case '4':
                      moveInfinitely(robot);
                      break;
             case '5':
                      //moveBoom(input, robot);
                      break;
             case '6':
                      getTemperature(robot);
                      break;
             case '7':
                      System.out.println(robot.getConductivity());
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
		  System.out.println("1) Move robot");
		  System.out.println("2) Move servo");
		  System.out.println("3) Read distances");
		  System.out.println("4) Move indefinitely");
      System.out.println("5) Move boom");
      System.out.println("6) Get temperature");
      System.out.println("7) Get conductivity");
      System.out.println("9) Exit");
		  System.out.print("Enter option:\n ");
		  cChoice = input.next().charAt(0);

		  return cChoice;

    }

    //Moving the robot forward or backwards 3 meters
    public static void moveRobot(Scanner scn, RXTXRobot r){

      char cOption;
      System.out.println("Move forward or backwards? \n F/B:");
      cOption = scn.next().charAt(0);

      if(cOption == 'F' || cOption == 'f'){

          System.out.println("Moving forward...");
          r.runMotor(RXTXRobot.MOTOR1, 145, RXTXRobot.MOTOR2, 150, 23000); //150, 175

      }

      else if (cOption == 'B' || cOption == 'b'){

          System.out.println("Moving backwards... ");
          r.runMotor(RXTXRobot.MOTOR1, -150, RXTXRobot.MOTOR2, -145, 23000); //150, 175

      }

      else {

          System.out.print("Incorrect option, exiting Function");
          return;

      }

    }

    //Method for moving servo motors
    public static void moveServo(Scanner scn, RXTXRobot r){

      int iOption = 0;

      System.out.println("Enter desired angle:");
      iOption = scn.nextInt();

      System.out.printf("Moving servo %d degrees...", iOption);
      r.moveServo(RXTXRobot.SERVO1, iOption);

    }

    //Method for reading ping sensor distances
    public static void readDistances(RXTXRobot r){

      for (int x=0; x < 5; ++x)
        {
          //Read the ping sensor value, which is connected to pin 12
          //r.refreshDigitalPins();
          System.out.println("Response: " + r.getPing(4) + " cm");
          r.sleep(3000);
        }

    }

    //Move the robot forward until the bump sensor is hitted
    public static void moveInfinitely(RXTXRobot r){

        boolean pressed = false;
        int bump = 0;

        r.refreshAnalogPins();
        r.runMotor(RXTXRobot.MOTOR1, 150, RXTXRobot.MOTOR2, 160, 0); //150, 175

        while ( !pressed ){
          r.refreshAnalogPins();
          bump = r.getAnalogPin(2).getValue();
          System.out.println(bump);
          r.sleep(10);
          if(bump < 500)
              pressed = true;

          }
          r.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 5);

    }

    //Method for raising or dropping the boom, not required for sprint 2
    public static void moveBoom(Scanner scn, RXTXRobot r){

      char cOption;
      System.out.println("Is the boom up?\n Y/N:");
      cOption = scn.next().charAt(0);

      if(cOption == 'Y' || cOption == 'y'){

          System.out.println("Dropping boom...");
          //Test which command elevates or drops it
            r.runMotor(RXTXRobot.MOTOR3, -180, 3500); //speed -255
          //  robot.runMotor(RXTXRobot.MOTOR1, 255, 3300);


      }

      else if (cOption == 'N' || cOption == 'n'){

          System.out.println("Elevating boom... ");
          //Test which command elevates or drops it
          //  robot.runMotor(RXTXRobot.MOTOR1, -255, 3500);
            r.runMotor(RXTXRobot.MOTOR3, 255, 3300);

      }

      else {

          System.out.print("Incorrect option, exiting Function");
          return;

      }

    }

    //Method for reading temperature, it returns a Celsius reading
    public static void getTemperature(RXTXRobot r){

      int[] readings = new int[5];
      int sum = 0;
      double intercept = 711.84;
      double slope = -7.12;
      double temperature = 0.0;
      int average = 0;


      for(int i = 0; i < 5 ; i++){

        readings[i] = getThermistorReading(r);
        sum += readings[i];

      }

      average = sum / 5;
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

}
