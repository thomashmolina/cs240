import os
import sys
os.system('javac editor/*.java')
os.system('java editor/ImageEditor pictures/cs_logo.ppm outputs/cs_logo_blurred.ppm motionblur 10')
os.system('rm editor/*.class')

