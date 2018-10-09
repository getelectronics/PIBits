// Distributed with a free-will license.
// Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
// LSM303DLHC
// This code is designed to work with the LSM303DLHC_I2CS I2C Mini Module available from ControlEverything.com.
// https://www.controleverything.com/products

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

public class LSM303DLHC
{
	public static void main(String args[]) throws Exception
	{
		// Create I2CBus
		I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
		// Get I2C device, LSM303DLHC ACCELERO I2C address is 0x19(25)
		I2CDevice device_accl = bus.getDevice(0x19);

		// Select control register1
		// X, Y and Z-axis enable, power on mode, o/p data rate 10 Hz
		device_accl.write(0x20 ,(byte)0x27);
		// Select control register4
		// Full scale +/- 2g, continuous update
		device_accl.write(0x23 ,(byte)0x00);
		Thread.sleep(500);

		// Read 6 bytes of data
		// xAccl lsb, xAccl msb, yAccl lsb, yAccl msb, zAccl lsb, zAccl msb
		byte[] data = new byte[6];
		data[0] = (byte)device_accl.read(0x28);
		data[1] = (byte)device_accl.read(0x29);
		data[2] = (byte)device_accl.read(0x2A);
		data[3] = (byte)device_accl.read(0x2B);
		data[4] = (byte)device_accl.read(0x2C);
		data[5] = (byte)device_accl.read(0x2D);

		// Convert the data
		int xAccl = ((data[1] & 0xFF) * 256 + (data[0] & 0xFF)) ;
		if(xAccl > 32767)
		{
			xAccl -= 65536;
		}	

		int yAccl = ((data[3] & 0xFF) * 256 + (data[2] & 0xFF)) ;
		if(yAccl > 32767)
		{
			yAccl -= 65536;
		}

		int zAccl = ((data[5] & 0xFF) * 256 + (data[4] & 0xFF)) ;
		if(zAccl > 32767)
		{
			zAccl -= 65536;
		}

		// Get I2C device, LSM303DLHC MAGNETO I2C address is 0x1E(30)
		I2CDevice device_mag = bus.getDevice(0x1E);

		// Select MR register
		// Continuous conversion
		device_mag.write(0x02, (byte)0x00);
		// Select CRA register
		// Data output rate = 15Hz
		device_mag.write(0x00, (byte)0x10);
		// Select CRB register
		// Set gain = +/- 1.3g
		device_mag.write(0x01, (byte)0x20);
		Thread.sleep(500);

		// Read 6 bytes of data
		// xMag msb, xMag lsb, zMag msb, zMag lsb, yMag msb, yMag lsb
		data[0] = (byte)device_mag.read(0x03);
		data[1] = (byte)device_mag.read(0x04);
		data[2] = (byte)device_mag.read(0x05);
		data[3] = (byte)device_mag.read(0x06);
		data[4] = (byte)device_mag.read(0x07);
		data[5] = (byte)device_mag.read(0x08);

		// Convert the data
		int xMag = ((data[0] & 0xFF) * 256 + (data[1] & 0xFF));
		if(xMag > 32767)
		{
			xMag -= 65536;
		}	

		int yMag = ((data[4] & 0xFF) * 256 + (data[5] & 0xFF)) ;
		if(yMag > 32767)
		{
			yMag -= 65536;
		}

		int zMag = ((data[2] & 0xFF) * 256 + (data[3] & 0xFF)) ;
		if(zMag > 32767)
		{
			zMag -= 65536;
		}

		// Output data to screen
		System.out.printf("Acceleration in X-Axis : %d %n", xAccl);
		System.out.printf("Acceleration in Y-Axis : %d %n", yAccl);
		System.out.printf("Acceleration in Z-Axis : %d %n", zAccl);
		System.out.printf("Magnetic field in X-Axis : %d %n", xMag);
		System.out.printf("Magnetic field in Y-Axis : %d %n", yMag);
		System.out.printf("Magnetic field in Z-Axis : %d %n", zMag);
	}
}