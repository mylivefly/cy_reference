
import urllib2

def getStockNo():
    with open('stockNo.txt', 'w') as out:
        url = 'http://www.bestopview.com/stocklist.html'
        for line in urllib2.urlopen(url):
            line = line.lstrip()
            if line.startswith('<li><a href="http://www.bestopview.com'):
                out.write(line[63:69])
                out.write('\n')

getStockNo()
