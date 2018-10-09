// Distributed with a free-will license.
// Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
// L3G4200D
// This code is designed to work with the L3G4200D_I2CS I2C Mini Module available from ControlEverything.com.
// https://www.controleverything.com/content/Gyro?sku=L3G4200D_I2CS#tabs-0-product_tabset-2

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

public class L3G4200D
{
	public static void main(String args[]) throws Exception
	{
		// Create I2C bus
		I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
		// Get I2C device, L3G4200 I2C address is 0x68(104)
		I2CDevice device = bus.getDevice(0x68);
		
		// Enable X, Y, Z-Axis and disable Power down mode
		device.write(0x20, (byte)0x0F);
		// Full scale range, 2000 dps
		device.write(0x23, (byte)0x30);
		Thread.sleep(300);
		
		// Read 6 bytes of data from address 0x28(40)
		// X lsb, X msb, Y lsb, Y msb, Z lsb, Z msb
		byte[] data = new byte[6];
		data[0] = (byte)device.read(0x28);
		data[1] = (byte)device.read(0x29);
		data[2] = (byte)device.read(0x2A);
		data[3] = (byte)device.read(0x2B);
		data[4] = (byte)device.read(0x2C);
		data[5] = (byte)device.read(0x2D);
		
		// Convert the values
		int xGyro = ((data[1] & 0xFF) * 256) + (data[0] & 0xFF);
		if (xGyro > 32767)
		{
			xGyro -= 65536;
		}
		
		int yGyro = ((data[3] & 0xFF) * 256) + (data[2] & 0xFF);
		if (yGyro > 32767)
		{
			yGyro -= 65536;
		}
		
		int zGyro = ((data[5] & 0xFF) * 256) + (data[4] & 0xFF);
		if (zGyro > 32767)
		{
			zGyro -= 65536;
		}
		
		// Output data to screen
		System.out.printf("Rotation in X-Axis : %d %n", xGyro);
		System.out.printf("Rotation in Y-Axis : %d %n", yGyro);
		System.out.printf("Rotation in Z-Axis : %d %n", zGyro);
	}
}