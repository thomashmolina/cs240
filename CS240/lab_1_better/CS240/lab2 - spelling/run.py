import os
import sys
word = sys.argv[1]
os.system('javac spell/*.java')
os.system('java spell/Driver spell/words.txt ' + word)
os.system('rm spell/*.class')

