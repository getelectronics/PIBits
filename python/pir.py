import time
import RPi.GPIO as io
io.setmode(io.BCM)
 
pir = 18
 
io.setup(pir, io.IN)         # activate input
 
while True:
	if io.input(pir):
		print("INTRUDER, INTRUDER, INTRUDER!")
	time.sleep(0.5)