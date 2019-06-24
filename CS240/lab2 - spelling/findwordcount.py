import sys
readfile = open('spell/words.txt', 'r')
count = 0
for line in readfile:
    line = line.strip()
    if line == 'or':
        count+=1
print(count)
