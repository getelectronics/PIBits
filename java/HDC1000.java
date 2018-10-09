// Distributed with a free-will license.
// Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
// HDC1000
// This code is designed to work with the HDC1000_I2CS I2C Mini Module available from ControlEverything.com.
// https://www.controleverything.com/content/Temperature?sku=HDC1000_I2CS#tabs-0-product_tabset-2

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

public class HDC1000
{
		public static void main(String args[]) throws Exception
	{
		// Create I2C bus
		I2CBus Bus = I2CFactory.getInstance(I2CBus.BUS_1);
		// Get I2C device, HDC1000 I2C address is 0x40(64)
		I2CDevice device = Bus.getDevice(0x40);
		Thread.sleep(500);

		// Select configuration register
		// Temperature, humidity enabled, resolultion = 14-bits, heater on
		device.write(0x02, (byte)0x30);
		Thread.sleep(100);

		// Send temp measurement command
		device.write((byte)0x00);
		Thread.sleep(500);

		// Read 2 bytes of data
		// temp msb, temp lsb
		byte[] data = new byte[2];
		device.read(data, 0, 2);
		
		// Convert the data
		int temp = ((data[0] & 0xFF) * 256 + (data[1] & 0xFF));
		double cTemp = (temp / 65536.0) * 165.0 - 40;
		double fTemp = cTemp * 1.8 + 32;

		// Send humidity measurement command
		device.write((byte)0x01);
		Thread.sleep(500);

		// Read 2 bytes of data
		// humidity msb, humidity lsb
		device.read(data, 0, 2);
		
		// Convert the data
		int hum = ((data[0] & 0xFF) * 256 + (data[1] & 0xFF));
		double humidity = (hum / 65536.0) * 100.0;

		// Output data to screen
		System.out.printf("Relative Humidity : %.2f %% RH %n", humidity);
		System.out.printf("Temperature in Celsius : %.2f C %n", cTemp);
		System.out.printf("Temperature in Farhenheit : %.2f F %n", fTemp);
	}
}