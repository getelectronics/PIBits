import time

from gpiozero import MCP3008
adc = MCP3008(channel=0, device=0)

	
def ConvertVolts(data,places):
	volts = (data * 3.3) / float(1023)
	volts = round(volts,places)
	return volts
 

def ConvertTemp(data,places):
	#temp = ((data * 330)/float(1023))-50
	temp += data / (10.0 / 1000)
	temp = round(temp,places)
	return temp
 
 
while True:
 
  # Read the temperature sensor data
  temp_level = adc.value
  temp_volts = ConvertVolts(temp_level,2)
  temp       = ConvertTemp(temp_level,2)
 
  # Print out results
  print "--------------------------------------------"
  print("Temp : {} ({}V) {} deg C".format(temp_level,temp_volts,temp))
 
  # Wait before repeating loop
  time.sleep(1)
  
  
