import RPi.GPIO as GPIO
import time
  
GPIO.setmode(GPIO.BCM)
  
# Here the input pin is declared, to which the buzzer is connected.
Buzzer_PIN = 24
GPIO.setup(Buzzer_PIN, GPIO.OUT, initial= GPIO.LOW)
  
print("Buzzer test [press CTRL+C to exit test]")
 
# main program loop
try:
    while True:
        print("Buzzer 4 seconds on")
        GPIO.output(Buzzer_PIN,GPIO.HIGH) #buzzer is switched on
        time.sleep(4)#wait for 4 seconds
        print("Buzzer 2 seconds off")
        GPIO.output(Buzzer_PIN,GPIO.LOW) #buzzer is switched off
        time.sleep(2)#wait two seconds
  
except KeyboardInterrupt:
    GPIO.cleanup()