
def getInnerText(line,start='>',end='<'):
    sPos = line.find(start)
    if sPos > 0:
        ePos = line.find(end, sPos)
        if (ePos > sPos) :
            return line[sPos+1:ePos]
    return ""


