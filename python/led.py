import RPi.GPIO as GPIO
import time

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD) ## Use board pin numbering
GPIO.setup(7, GPIO.OUT) ## Setup GPIO Pin 7 to OUT

state = True

# endless loop
while True:
 GPIO.output(7,True)## Turn on GPIO pin 7
 time.sleep(1) ## 1 second
 GPIO.output(7,False)## Turn off GPIO pin 7
 time.sleep(1)## 1 second
 
 
import RPi.GPIO as GPIO ## Import GPIO library

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD) ## Use board pin numbering
GPIO.setup(7, GPIO.OUT) ## Setup GPIO Pin 7 to OUT
GPIO.output(7,True) ## Turn on GPIO pin 7













