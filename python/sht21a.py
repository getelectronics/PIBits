import smbus
import time

class SHT21:
    """Class to read temperature and humidity from SHT21.
    Ressources: 
      http://www.sensirion.com/fileadmin/user_upload/customers/sensirion/Dokumente/Humidity/Sensirion_Humidity_SHT21_Datasheet_V3.pdf
      https://github.com/jaques/sht21_python/blob/master/sht21.py
      Martin Steppuhn's code from http://www.emsystech.de/raspi-sht21"""

    #control constants
    _SOFTRESET = 0xFE
    _I2C_ADDRESS = 0x40
    _TRIGGER_TEMPERATURE_NO_HOLD = 0xF3
    _TRIGGER_HUMIDITY_NO_HOLD = 0xF5

    def __init__(self, bus):
        """According to the datasheet the soft reset takes less than 15 ms."""
        self.bus = bus
        self.bus.write_byte(self._I2C_ADDRESS, self._SOFTRESET)
        time.sleep(0.015)

    def read_temperature(self):    
        """Reads the temperature from the sensor.  Not that this call blocks
	for 250ms to allow the sensor to return the data"""
        data = []
        self.bus.write_byte(self._I2C_ADDRESS, self._TRIGGER_TEMPERATURE_NO_HOLD)
        time.sleep(0.250)
        data.append(self.bus.read_byte(self._I2C_ADDRESS))
        data.append(self.bus.read_byte(self._I2C_ADDRESS))
        return self._get_temperature_from_buffer(data)
        

    def read_humidity(self):    
        """Reads the humidity from the sensor.  Not that this call blocks 
	for 250ms to allow the sensor to return the data"""
        data = []
        self.bus.write_byte(self._I2C_ADDRESS, self._TRIGGER_HUMIDITY_NO_HOLD)
        time.sleep(0.250)
        data.append(self.bus.read_byte(self._I2C_ADDRESS))
        data.append(self.bus.read_byte(self._I2C_ADDRESS))
        return self._get_humidity_from_buffer(data)    

    def _get_temperature_from_buffer(self, data):
        """This function reads the first two bytes of data and 
        returns the temperature in C by using the following function:
        T = =46.82 + (172.72 * (ST/2^16))
        where ST is the value from the sensor
        """
        unadjusted = (data[0] << 8) + data[1]
        unadjusted *= 175.72
        unadjusted /= 1 << 16 # divide by 2^16
        unadjusted -= 46.85
        return unadjusted
    
    def _get_humidity_from_buffer(self, data):
        """This function reads the first two bytes of data and returns 
        the relative humidity in percent by using the following function:
        RH = -6 + (125 * (SRH / 2 ^16))
        where SRH is the value read from the sensor
        """
        unadjusted = (data[0] << 8) + data[1]
        unadjusted *= 125
        unadjusted /= 1 << 16 # divide by 2^16
        unadjusted -= 6
        return unadjusted
        
                

    def close(self):
        """Closes the i2c connection"""
        self.bus.close()


    def __enter__(self):
        """used to enable python's with statement support"""
        return self
        

    def __exit__(self, type, value, traceback):
        """with support"""
        self.close()


if __name__ == "__main__":
    try:
        bus = smbus.SMBus(1)
        with SHT21(bus) as sht21:
            print "Temperature: %s"%sht21.read_temperature()
            print "Humidity: %s"%sht21.read_humidity()
    except IOError, e:
        print e
        print 'Error creating connection to i2c.'