import RPi.GPIO as GPIO
import time
from gpiozero import MCP3008

led = 7

GPIO.setmode(GPIO.BOARD)
GPIO.setup(led,GPIO.OUT)
GPIO.output(led,False)

try: 
	while True:
		adc = MCP3008(channel=0)
		voltage = 3.3 * adc.value
		print("Measured voltage is ",voltage);
		time.sleep(0.2)

except KeyboardInterrupt:
    GPIO.cleanup()