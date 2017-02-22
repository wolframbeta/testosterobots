import rxtxrobot.*; 
 
public class GetSensorData 
{    
    public static void main(String[] args) 
    {     
	    // All sensor data will be read from the analog pins 
		 
	    RXTXRobot r = new ArduinoNano(); //Create RXTXRobot object 
 
		r.setPort("COM5"); // Sets the port to COM5 
		 
		r.connect(); 
 
		r.refreshAnalogPins(); // Cache the Analog pin information 
 
		for (int x=0; x < 4; ++x) 
		{ 
			AnalogPin temp = r.getAnalogPin(x); 
			System.out.println("Sensor " + x + " has value: " + temp.getValue()); 
		} 
		r.close(); 
    } 
} 
