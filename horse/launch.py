
import FundsSpider
import fundsReal

#fundsSpider = FundsSpider.FundsSpider()
#fundsSpider.process('000001')

stockNo = open('stockNo.txt')
for number in stockNo:
    number = number.strip('\n')
    sum = fundsReal.fundsSumReal(number)
    if sum > 1000:
        print '%s, %f'%(number,sum)
stockNo.close()