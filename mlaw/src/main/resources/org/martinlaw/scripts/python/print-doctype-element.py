# author Eric Njogu
# print the child elements of data - useful for combining several files
# sample invocations:
# dir C:\Users\mugo\doctype /B/S  | c:\python33\python print-doctype-element.py > c:\temp\all-doctypes.xml
# type doctype-list.txt | c:\Python33\python.exe print-doctype-element.py > c:\temp\all-doctypes2.xml
# echo C:\Users\mugo\git\mlaw\mlaw\src\main\resources\org\martinlaw\rules\rules.xml | c:\Python33\python.exe C:\Users\mugo\git\mlaw\mlaw\src\main\resources\org\martinlaw\scripts\python\print-doctype-element.py > c:\temp\test-et.xml
# credits: http://stackoverflow.com/a/1319417, http://stackoverflow.com/a/60254 http://stackoverflow.com/a/402704

import sys, os, xml.etree.ElementTree as ET, re

# create new xml tree
combinedRoot = ET.Element("data")
for file_path in sys.stdin:
    file_path = file_path.strip()
    if os.path.exists(file_path):
        file = open(file_path, 'r')
        currentRoot = ET.fromstring(file.read())
        for element in currentRoot:
            existingElement = combinedRoot.find(element.tag)
            if existingElement is None:
                combinedRoot.append(element)
            else:
                for child in element:
                    existingElement.append(child)
    else:
        sys.stderr.write("file '" + file_path + "' does not exist\n")

toStr = ET.tostring(combinedRoot).decode()
sys.stdout.write(re.sub("ns\d+[:=]{1,1}", "", toStr).replace("xmlns:", "xmlns=").replace("xmlns=xsi", "xmlns:xsi"))
