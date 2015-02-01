import urllib2
import util
import datetime
import threading

def getFunds(stockNo):
    stockNo = stockNo[0:6]
    url = 'http://stockpage.10jqka.com.cn/'+stockNo+'/funds/';
    funds = [];
    tmp = []
    try:
        content = urllib2.urlopen(url, timeout=5)
        i = 0
        for line in content:
            i += 1
            if i>500 and line.find('border_l_none') != -1:
                content.readline()
                content.readline()
                content.readline()
                main5 = int(util.getInnerText(content.readline(), '>', '.'))
                #content.readline()
                #content.readline()
                #content.readline()
                #main = int(util.getInnerText(content.readline(), '>', '.'))
                #funds.append((main5,main))
                if main5 != 0:
                    funds.append(main5)
                if len(funds) > 10: break
    except:
        print "error in getFunds(%s)" %(stockNo)
    #print 'handle ' + stockNo
    return funds

def checkFunds(f):
    if len(f) >= 5:
        d1 = f[0]>0 and f[1]<0 and f[2]<0 and f[3]<0
        d2 = f[0]>0 and f[1]>0 and f[2]<0 and f[3]<0 and f[4]<0
        return d1 or d2
    return False

class Worker(threading.Thread):
    def __init__(self, stockNo):
        threading.Thread.__init__(self)
        self.stockNo = stockNo

    def run(self):
        list = []
        for number in self.stockNo:
            number = number.strip('\n')
            if len(number) <5:
                continue
            if checkFunds(getFunds(number)):
                list.append(number)
        print list

def main():
    tCount = 10
    lists = []
    for i in range(tCount): lists.append([])
    fObj = open('stockNo.txt', 'r')
    i = 0;
    for number in fObj:
        lists[i].append(number.strip('\n'))
        i += 1;
        if i%tCount == 0: i=0
    fObj.close()
    for item in lists:
        wk = Worker(item)
        wk.start()

main()

#print getFunds('000333')
#print getFunds('601021');
#print getFunds('000338');
#print getFunds('000869');
#print getFunds('600111');



