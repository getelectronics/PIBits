// Distributed with a free-will license.
// Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
// MMA7455
// This code is designed to work with the MMA7455_I2CS I2C Mini Module available from ControlEverything.com.
// https://www.controleverything.com/content/Accelorometer?sku=MMA7455_I2CS#tabs-0-product_tabset-2

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

public class MMA7455
{
	public static void main(String args[]) throws Exception
	{
		// Create I2CBus
		I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
		// Get I2C device, MMA7455 I2C address is 0x1D(29)
		I2CDevice device = bus.getDevice(0x1D);

		// Select mode control register
		// Measurement mode, +/- 8g 
		device.write(0x16, (byte)0x01);
		Thread.sleep(500);

		// Read 6 bytes of data
		// xAccl lsb, xAccl msb, yAccl lsb, yAccl msb, zAccl lsb, zAccl msb
		byte[] data = new byte[6];
		device.read(0x00, data, 0, 6);

		// Convert the data to 10-bits
		int xAccl = ((data[1] & 0x03) * 256 + (data[0] & 0xFF));
		if(xAccl > 511)
		{
			xAccl -= 1024;
		}	

		int yAccl = ((data[3] & 0x03) * 256 + (data[2] & 0xFF));
		if(yAccl > 511)
		{
			yAccl -= 1024;
		}

		int zAccl = ((data[5] & 0x03) * 256 + (data[4] & 0xFF));
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