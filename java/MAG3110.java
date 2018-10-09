// Distributed with a free-will license.
// Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
// MAG3110
// This code is designed to work with the MAG3110_I2CS I2C Mini Module available from ControlEverything.com.
// https://www.controleverything.com/content/Compass?sku=MAG3110_I2CS#tabs-0-product_tabset-2

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

public class MAG3110
{
	public static void main(String args[]) throws Exception
	{
		// Create I2C bus
		I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
		// Get I2C device, MAG3110 I2C address is 0x0E(14)
		I2CDevice device = bus.getDevice(0x0E);
		
		// Send Start Command , Active mode
		device.write(0x10,(byte)0x01);
		Thread.sleep(200);
		
		// Read 6 bytes of data from address 0x01(1) , msb first
		byte[] data = new byte[6];
		device.read(0x01,data,0,6);
		
		// Convert data
		int xMag = (((data[0] & 0xFF) * 256 ) + (data[1] & 0xFF));
		if(xMag > 32767)
		{
			xMag -= 65536;
		}
		
		int yMag = (((data[2] & 0xFF) * 256 ) + (data[3] & 0xFF));
		if(yMag > 32767)
		{
			yMag -= 65536;
		}
		
		int zMag = (((data[4] & 0xFF) * 256 ) + (data[5] & 0xFF));
		if(zMag > 32767)
		{
			zMag -= 65536;
		}
		
		// Output data to screen
		System.out.printf("Magnetic field in X Axis :  %d %n" ,  xMag);
		System.out.printf("Magnetic field in Y Axis :  %d %n" ,  yMag);
		System.out.printf("Magnetic field in Z Axis :  %d %n" ,  zMag);
	}
}