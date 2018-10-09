import smbus
import time

# Get I2C bus
bus = smbus.SMBus(1)

# BMA250 address, 0x18(24)
# Select range selection register, 0x0F(15)
#		0x03(03)	Set range = +/-2g
bus.write_byte_data(0x18, 0x0F, 0x03)
# BMA250 address, 0x18(24)
# Select bandwidth register, 0x10(16)
#		0x08(08)	Bandwidth = 7.81 Hz
bus.write_byte_data(0x18, 0x10, 0x08)

time.sleep(0.5)

# BMA250 address, 0x18(24)
# Read data back from 0x02(02), 6 bytes
# X-Axis LSB, X-Axis MSB, Y-Axis LSB, Y-Axis MSB, Z-Axis LSB, Z-Axis MSB
data = bus.read_i2c_block_data(0x18, 0x02, 6)

# Convert the data to 10 bits
xAccl = (data[1] * 256 + (data[0] & 0xC0)) / 64
if xAccl > 511 :
	xAccl -= 1024

yAccl = (data[3] * 256 + (data[2] & 0xC0)) / 64
if yAccl > 511 :
	yAccl -= 1024

zAccl = (data[5] * 256 + (data[4] & 0xC0)) / 64
if zAccl > 511 :
	zAccl -= 1024

# Output data to screen
print "Acceleration in X-Axis : %d" % xAccl
print "Acceleration in Y-Axis : %d" % yAccl
print "Acceleration in Z-Axis : %d" % zAccl