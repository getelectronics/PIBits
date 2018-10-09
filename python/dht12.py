import smbus

DEVICE     = 0x5C #device I2C address
bus = smbus.SMBus(1)

def readdata(addr=DEVICE):

  #read 5 bytes of data from the device address (0x05C) starting from an offset of zero
  data = bus.read_i2c_block_data(addr,0x00, 5)

  print "Humidity = " + str(data[0]) + "." + str(data[1]) + "%"
  print "Temperature : " + str(data[2]) + "." + str(data[3]) + "C"

  if (data[0] + data[1] + data[2] + data[3] ==  data[4]):
    print "checksum is correct"
  else:
    print "checksum is incorrect"

if __name__=="__main__":
    readdata()