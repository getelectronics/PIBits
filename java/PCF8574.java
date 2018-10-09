// Distributed with a free-will license.
// Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
// PCF8574
// This code is designed to work with the PCF8574_LBAR_I2CL I2C Mini Module available from ControlEverything.com.
// https://www.controleverything.com/products

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

public class PCF8574
{
    public static void main(String args[]) throws Exception
    {
        // Create I2C bus
        I2CBus Bus = I2CFactory.getInstance(I2CBus.BUS_1);
        // Get I2C device, PCF8574 I2C address is 0x20(32)
        I2CDevice device = Bus.getDevice(0x20);
        
        // Select all pins configured as inputs
        device.write((byte)0xFF);
        
        // Output to screen
        System.out.printf("All Pins State are HIGH %n");
        Thread.sleep(500);
        
        // Read 1 byte of data
        byte[] data = new byte[1];
        device.read(data, 0, 1);
        
        // Convert the data to 4-bits
        int data1 = (data[0] & 0xFF);
        
        for(int i=0; i<8; i++)
        {
            if((data1 & ((int)Math.pow(2, i))) == 0)
            {
                System.out.printf("I/O Pin %d State is LOW %n", i);
            }
            else
            {
                System.out.printf("I/O Pin %d State is HIGH %n", i);
                Thread.sleep(500);
            }
        }
    }
}