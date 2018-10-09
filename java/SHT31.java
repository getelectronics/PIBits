// Distributed with a free-will license.
// Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
// SHT31
// This code is designed to work with the SHT31_I2CS I2C Mini Module available from ControlEverything.com.
// https://www.controleverything.com/content/Humidity?sku=SHT31_I2CS#tabs-0-product_tabset-2

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

public class SHT31
{
	public static void main(String args[]) throws Exception
	{
		// Create I2CBus
		I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
		// Get I2C device, SHT31 I2C address is 0x44(68)
		I2CDevice device = bus.getDevice(0x44);

		// Send high repeatability measurement command
		// Command msb, command lsb
		byte[] config = new byte[2];
		config[0] = (byte)0x2C; 
		config[1] = (byte)0x06;
		device.write(config, 0, 2);
		Thread.sleep(500);

		// Read 6 bytes of data
		// temp msb, temp lsb, temp CRC, humidity msb, humidity lsb, humidity CRC
		byte[] data = new byte[6];
		device.read(data, 0, 6);

		//Convert the data
		double cTemp = ((((data[0] & 0xFF) * 256) + (data[1] & 0xFF)) * 175.0) / 65535.0  - 45.0;
		double fTemp = ((((data[0] & 0xFF) * 256) + (data[1] & 0xFF)) * 315.0) / 65535.0 - 49.0;
		double humidity = ((((data[3] & 0xFF) * 256) + (data[4] & 0xFF)) * 100.0) / 65535.0;

		//Output data to screen
		System.out.printf("Temperature in Celsius : %.2f C %n", cTemp);
		System.out.printf("Temperature in Fahrenheit : %.2f F %n", fTemp);
		System.out.printf("Relative Humidity is : %.2f %%RH %n", humidity);
	}
}