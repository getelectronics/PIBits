import max30100
mx30 = max30100.MAX30100()
mx30.read_sensor()

# The latest values are now available via .ir and .red
mx30.read_sensor()
mx30.ir, mx30.red

while True:
     print(mx30.buffer_red[-10:])
	 sleep(0.5)