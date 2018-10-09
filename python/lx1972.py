import time

from gpiozero import MCP3008
adc = MCP3008(channel=0, device=0)

while True:
	print(adc.value)
	time.sleep(0.5)