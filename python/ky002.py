import RPi.GPIO as GPIO
import time
  
GPIO.setmode(GPIO.BCM)

sensor = 17
led = 27
GPIO.setup(sensor, GPIO.IN, pull_up_down = GPIO.PUD_UP)
GPIO.setup(led, GPIO.OUT)
  
  
#Function executed on signal detection
def active(null):
        GPIO.out(led,GPIO.HIGH)

#On detecting signal (falling edge), active function will be activated.
GPIO.add_event_detect(sensor, GPIO.FALLING, callback=active, bouncetime=100) 
  
# main program loop
try:
        while True:
                time.sleep(1)
  
# Scavenging work after the end of the program
except KeyboardInterrupt:
        GPIO.cleanup()