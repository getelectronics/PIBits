// Distributed with a free-will license.
// Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
// BMA250
// This code is designed to work with the BMA250_I2CS I2C Mini Module available from ControlEverything.com.
// https://www.controleverything.com/content/Accelorometer?sku=BMA250_I2CS#tabs-0-product_tabset-2

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

public class BMA250
{
	public static void main(String args[]) throws Exception
	{
		// Create I2C bus
		I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
		// Get I2C device, BMA250 I2C address is 0x18(24)
		I2CDevice device = bus.getDevice(0x18);

		// Select range selection register
		// Set range +/- 2g
		device.write(0x0F, (byte)0x03);
		// Select bandwidth register
		// Bandwidth = 7.81 Hz
		device.write(0x10, (byte)0x08);
		Thread.sleep(500);

		// Read 6 bytes of data from address 0x02(02)
		// xAccl lsb, xAccl msb, yAccl lsb, yAccl msb, zAccl lsb, zAccl msb
		byte[] data = new byte[6];
		device.read(0x02, data, 0, 6);

		// Convert the data to 10-bits
		int xAccl = (((data[1] & 0xFF) * 256) + (data[0] & 0xC0)) / 64;
		if(xAccl > 511)
		{
			xAccl -= 1024;
		}

		int yAccl = (((data[3] & 0xFF) * 256) + (data[2] & 0xC0)) / 64;
		if(yAccl > 511)
		{
			yAccl -= 1024;
		}

		int zAccl = (((data[5] & 0xFF) * 256) + (data[4] & 0xC0)) / 64;
		if(zAccl > 511)
		{
			zAccl -= 1024;
		}

		// Output data to screen
		System.out.printf("Acceleration in X-Axis : %d %n", xAccl);
		System.out.printf("Acceleration in Y-Axis : %d %n", yAccl);
		System.out.printf("Acceleration in Z-Axis : %d %n", zAccl);
	}
}