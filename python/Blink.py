# Written by Lorenzo Daidone
# graficayes@email.it
# 11/15/2015

#!/usr/bin/python

import time
import RPi.GPIO as GPIO

GPIO.setmode(GPIO.BCM)

GPIO.setup (18, GPIO.OUT)   # led connected to pin 18

while True:
    GPIO.output(18, True)   # turn on the led
    time.sleep(0.5)
    GPIO.output(18, False)  # turn off the led
    time.sleep(0.5)
