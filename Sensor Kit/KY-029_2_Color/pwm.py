import random, time 
import RPi.GPIO as GPIO
  
GPIO.setmode(GPIO.BCM) 
 
# Here we declare the output pin to which the LEDs are connected.
RED_LED = 24
GREEN_LED = 23
  
# Set pins to output mode
GPIO.setup(RED_LED, GPIO.OUT) 
GPIO.setup(GREEN_LED, GPIO.OUT)
  
Freq = 100 #Hz
  
# The respective colors are initialized.
RED = GPIO.PWM(RED_LED, Freq) 
GREEN = GPIO.PWM(GREEN_LED, Freq)
RED.start(0)  
GREEN.start(0)
  
def LED_Color(Red, Green, pause):
    RED.ChangeDutyCycle(Red)
    GREEN.ChangeDutyCycle(Green)
    time.sleep(pause)
 
    RED.ChangeDutyCycle(0)
    GREEN.ChangeDutyCycle(0)
   
print ("LED test [press CTRL+C to end test]")
  
# Main program loop:
try:
    while True:
        for x in range(0,2):
            for y in range(0,2):
                print (x,y)
                for i in range(0,101):
                    LED_Color((x*i),(y*i),.02)
  

except KeyboardInterrupt:
    GPIO.cleanup()