# sample invocation:
# C:\Users\mugo\git\mlaw\mlaw\src\test\python>type c:\Temp\accordion-raw.html | c:\Python33\python.exe remove-blank-lines.py > c:\Temp\accordion-clean.html

import sys, os, re

for line in sys.stdin:
    if re.match("^$", line) is None:
        sys.stdout.write(line)
