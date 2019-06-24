import os
import sys
dictionary_path = "hangman/dictionary.txt"
word_length = 6
guesses = 26

os.system('javac hangman/*.java')
os.system('java hangman/Main ' + dictionary_path + " " + str(word_length) + " " + str(guesses))
os.system('rm hangman/*.class')
