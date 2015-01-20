
from sgmllib import SGMLParser
import urllib2

class FundsSpider(SGMLParser):
    funds = []
    _tableCount = 0
    _trEnable = False
    _tdEnable = False
    _trCount = 0

    def is_area_enable(self):
        return self._tableCount == 2 and self._trCount > 2

    def start_table(self, attrs):
        self._tableCount += 1
        self._trCount = 0

    def start_tr(self, attrs):
        self._trCount += 1
        self._tdCount = 0
        self._item = []
        self._trEnable = self.is_area_enable()

    def end_tr(self):
        if self._trEnable:
            self.funds.append(self._item)
            self._trEnable = False

    def start_td(self, attrs):
        self._tdCount += 1
        self._tdEnable = self.is_area_enable()

    def handle_data(self, text):
        if self._tdEnable:
            self._item.append(text)
            self._tdEnable = False

    def process(self, number):
        url='http://stockpage.10jqka.com.cn/'+number+'/funds/'
        content=urllib2.urlopen(url).read()
        self.feed(content)
        #with open(number+'.txt', 'w') as out:
            #for row in self.funds:
            #    out.write(','.join(row))
            #    out.write('\r\n')
        for row in self.funds:
            print ','.join(row)

fundsSpider = FundsSpider()
fundsSpider.process('000001')

