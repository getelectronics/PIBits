import RPi. GPIO  as  GPIO
import time
  
GPIO.setmode (GPIO.BCM) 
  
# The input pin
LED_PIN = 24
GPIO.setup(LED_PIN, GPIO.OUT, initial= GPIO.LOW)
  
print ("Flashing LED test [press CTRL+C to end the test]")
 
# Main program loop
try:
    while True:
        print("LED 4 seconds on")
        GPIO.output(LED_PIN,GPIO.HIGH) # LED is switched on
        time.sleep(4)  # Wait for 4 seconds
        print("LED 2 seconds off") 
        GPIO.output (LED_PIN,GPIO.LOW) # LED is switched off
        time.sleep (2)   # LED is switched off
 
# Tidying up after the program is finished
except KeyboardInterrupt:
    GPIO.cleanup ()