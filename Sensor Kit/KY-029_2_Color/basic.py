import RPi.GPIO as GPIO
import time
   
GPIO.setmode(GPIO.BCM)
   
# Here the output pin is declared, to which the LEDs are connected.
RED_LED = 24
GREEN_LED = 23
GPIO.setup(RED_LED, GPIO.OUT, initial= GPIO.LOW)
GPIO.setup(GREEN_LED, GPIO.OUT, initial= GPIO.LOW)
   
print ("LED test [press CTRL+C to end test]")
  
# main program loop
try:
    while True:
        print("LED RED 2 seconds on")
        GPIO.output(RED_LED,GPIO.HIGH) #LED is turned on
        GPIO.output(GREEN_LED,GPIO.LOW) #LED is switched off
        time.sleep(2) # wait for 2 seconds
        print("LED GREEN 2 seconds on") 
        GPIO.output(RED_LED,GPIO.LOW) #LED is switched off
        GPIO.output(GREEN_LED,GPIO.HIGH) #LED is switched on
        time.sleep(2) #wait for two seconds
   
# clean up after the program has been terminated
except KeyboardInterrupt:
    GPIO.cleanup()