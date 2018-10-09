// Distributed with a free-will license.
// Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
// ADXL345
// This code is designed to work with the ADXL345_I2CS I2C Mini Module available from ControlEverything.com.
// https://www.controleverything.com/content/Accelorometer?sku=ADXL345_I2CS#tabs-0-product_tabset-2

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

public class ADXL345
{
public static void main(String args[]) throws Exception
	{
		// Create I2C bus
		I2CBus Bus = I2CFactory.getInstance(I2CBus.BUS_1);
		// Get I2C device, device I2C address is 0x53(83)
		I2CDevice device = Bus.getDevice(0x53);

		// Select Bandwidth rate register
		// Normal mode, Output data rate = 100 Hz
		device.write(0x2C, (byte)0x0A);
		// Select Power control register
		// Auto-sleep disable
		device.write(0x2D, (byte)0x08);
		// Select Data format register
		// Self test disabled, 4-wire interface, Full resolution, range = +/-2g
		device.write(0x31, (byte)0x08);
		Thread.sleep(500);

		// Read 6 bytes of data
		// xAccl lsb, xAccl msb, yAccl lsb, yAccl msb, zAccl lsb, zAccl msb
		byte[] data = new byte[6];
		data[0] = (byte)device.read(0x32);
		data[1] = (byte)device.read(0x33);
		data[2] = (byte)device.read(0x34);
		data[3] = (byte)device.read(0x35);
		data[4] = (byte)device.read(0x36);
		data[5] = (byte)device.read(0x37);

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