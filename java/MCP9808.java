// Distributed with a free-will license.
// Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
// MCP9808
// This code is designed to work with the MCP9808_I2CS I2C Mini Module available from ControlEverything.com.
// https://www.controleverything.com/content/Temperature?sku=MCP9808_I2CS#tabs-0-product_tabset-2

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

public class MCP9808
{
	public static void main(String args[]) throws Exception
	{
		// Create I2C bus
		I2CBus Bus = I2CFactory.getInstance(I2CBus.BUS_1);
		// Get I2C device, MCP9808 I2C address is 0x18(24)
		I2CDevice device = Bus.getDevice(0x18);
		Thread.sleep(300);

		// Select configuration register
		// Continuous conversion mode, Power-up default
		byte[] config = new byte[2];
		config[0] = 0x00;
		config[1] = 0x00;
		device.write(0x01, config, 0, 2);
		// Select resolution rgister
		// Resolution = +0.0625 / C
		device.write(0x08, (byte)0x03);
		Thread.sleep(300);

		// Read 2 bytes of data from address 0x05(05)
		// temp msb, temp lsb
		byte[] data = new byte[2];
		device.read(0x05, data, 0, 2);

		// Convert the data to 13-bits
		int temp = ((data[0] & 0x1F) * 256 + (data[1] & 0xFF));
		if(temp > 4095)
		{
			temp -= 8192;
		}
		double cTemp = temp * 0.0625;
		double fTemp = cTemp * 1.8 + 32;

		// Output data to screen
		System.out.printf("Temperature in Celsius is : %.2f C %n", cTemp);
		System.out.printf("Temperature in Fahrenheit is : %.2f F %n", fTemp);
	}
}