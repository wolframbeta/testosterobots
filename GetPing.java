import rxtxrobot.*;

public class GetPing
{
	//final private static int PING_PIN = 9;

	public static void main(String[] args)
	{
		RXTXRobot r = new ArduinoNano(); // Create RXTXRobot object
		r.setPort("/dev/tty.usbmodem21"); // Set the port to COM3
		r.connect();



    r.refreshAnalogPins();

    for(int i = 0; i < 5 ; i++){
        AnalogPin aBump = r.getAnalogPin(2);
        System.out.println("Analog pin value " + aBump.getValue() );

    }
    //System.out.println("Initial free Digital pins");
  //  System.out.println(r.checkValidPorts());
  //  System.out.println(r.getAvailableDigitalPins());

		/*for (int x=0; x < 3; x++)
		{
			//Read the ping sensor value, which is connected to pin 12
      //r.refreshDigitalPins();
			System.out.println("Response: " + r.getPing(4) + " cm");
			r.sleep(2000);
		}*/
		r.close();
	}
}
