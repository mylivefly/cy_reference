from sgmllib import SGMLParser
import urllib2
import json

def fundsReal(number):
    url = 'http://stockpage.10jqka.com.cn/spService/'+number+'/Funds/realFunds'
    raw = urllib2.urlopen(url).read()
    raw = raw[0:raw.index(']')]+']}'
    obj = json.loads(raw)
    fund = {}
    for item in obj['flash']:
        fund[item['name']]=item['sr']
    return float(fund[u'大单流入'])-float(fund[u'大单流出']) \
        + float(fund[u'中单流入'])-float(fund[u'中单流出']) \
        + float(fund[u'小单流入'])-float(fund[u'小单流出'])

def fundsSumReal(number):
    url = 'http://stockpage.10jqka.com.cn/spService/'+number+'/Funds/realFunds'
    raw = urllib2.urlopen(url).read()
    title = json.loads(raw[raw.index('title')+7:-1])
    return float(title['je'])

print fundsSumReal('000333')
print fundsSumReal('000869')
print fundsSumReal('601618')
print fundsSumReal('002160')

#print fundsReal('000333')
#print fundsReal('000869')
#print fundsReal('601618')
#print fundsReal('002160')

