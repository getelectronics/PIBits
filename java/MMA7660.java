// Distributed with a free-will license.
// Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
// MMA7660FC
// This code is designed to work with the MMA7660FC_I2CS I2C Mini Module available from ControlEverything.com.
// https://www.controleverything.com/content/Accelorometer?sku=MMA7660FC_I2CS#tabs-0-product_tabset-2

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

public class MMA7660FC
{
	public static void main(String args[]) throws Exception
	{
		// Create I2CBus
		I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
		// Get I2C device, MMA7660FC I2C address is 0x4C(76)
		I2CDevice device = bus.getDevice(0x4C);

		// Select sample rate register
		// 1 Sample/second
		device.write(0x08, (byte)0x07);
		// Select mode register
		// Active mode
		device.write(0x07, (byte)0x01);
		Thread.sleep(300);

		// Read 3 bytes of data
		// xAccl, yAccl, zAccl
		byte[] data = new byte[3];
		device.read(0x00, data, 0, 3);

		// Convert the data to 6-bits
		int xAccl = data[0] & 0x3F;
		if(xAccl > 31)
		{
			xAccl -= 64;
		}	

		int yAccl = data[1] & 0x3F;;
		if(yAccl > 31)
		{
			yAccl -= 64;
		}

		int zAccl = data[2] & 0x3F;;
		if(zAccl > 31)
		{
			zAccl -= 64;
		}

		// Output data to screen
		System.out.printf("Acceleration in X-Axis : %d %n", xAccl);
		System.out.printf("Acceleration in Y-Axis : %d %n", yAccl);
		System.out.printf("Acceleration in Z-Axis : %d %n", zAccl);
	}
}