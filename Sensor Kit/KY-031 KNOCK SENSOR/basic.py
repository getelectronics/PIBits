import RPi. GPIO  as  GPIO
import time
   
GPIO.setmode (GPIO.BCM) 
   
# Here the input pin to which the Knock Sensor is connected
GPIO_PIN = 24
GPIO.setup (GPIO_PIN, GPIO.IN) 
   
print ("Knock Sensor test [press CTRL+C to exit the test]")
   
#  This output function is executed when a signal is detected
def outputFunction(null) :
    print("Knock Sensor triggered")
   
#  When a signal is detected (falling signal edge), the output function is triggered
GPIO.add_event_detect(GPIO_PIN, GPIO.FALLING, callback= outputFunction ,  bouncetime=100)  
   
# Main loop
try:
    while True:
        time.sleep (1) 
   
except KeyboardInterrupt:
    GPIO.cleanup ()