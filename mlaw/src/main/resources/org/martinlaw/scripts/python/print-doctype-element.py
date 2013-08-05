# author Eric Njogu
# print the doc type xml elements - useful for combining several files
# sample invocations:
# dir C:\Users\mugo\doctype /B/S  | c:\python33\python print-doctype-element.py > c:\temp\all-doctypes.xml
# type doctype-list.txt | c:\Python33\python.exe print-doctype-element.py > c:\temp\all-doctypes2.xml
# credits: http://stackoverflow.com/a/1319417, http://stackoverflow.com/a/60254
import sys, os, xml.etree.ElementTree as ET

for file_path in sys.stdin:
    file_path = file_path.strip()
    if os.path.exists(file_path):
        file = open(file_path, 'r')
        root = ET.fromstring(file.read())
        for element in root.findall("{0}documentTypes/{0}documentType".format("{ns:workflow/DocumentType}")):
            sys.stdout.write(ET.tostring(element).decode())
    else:
        sys.stderr.write("file '" + file_path + "' does not exist\n") 


    
