import sys
import fileinput

count = 0

for line in fileinput.input():
    line = line.strip()
    if (("Failed" in line or "CRASH" in line) and "Failed:" not in line):
        count+=1



if __name__ == "__main__":
    print(count)
    if (count > 0):
        sys.exit(count)