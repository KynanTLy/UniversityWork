import csv

def CalcY(Y):
	newY = ((float(Y) - 11.358) / (11.7240 - 11.358)) * ((1000 - 0) + 0)
	return newY

def CalcX(X):
	newX = ((float(X)  - 48.06) / (48.249 - 48.06)) * ((1000 - 0) + 0)
	return newX

def CalcYDec(Y):
	yDec = 10/74539 
	newY = (((float(Y) - yDec) - 11.358) / (11.7240 - 11.358)) * ((1000 - 0) + 0)	
	return newY

def CalcXInc(X):
	xInc = 10/111191
	newX = (((float(X) + xInc) - 48.06) / (48.249 - 48.06)) * ((1000 - 0) + 0)	
	return newX

def main():
	POI_Input = open('poi.tsv', 'r')
	POI_Output = open('poiConvert.tsv', 'w')


	for line in csv.reader(POI_Input, dialect="excel-tab") :
		POI_Output.write(line[0] + '\t')
		#POI_Output.write(line[1] + '\t')
		
		xmin = CalcX(line[2])
		xmax = CalcXInc(line[2])
		ymin = CalcYDec(line[3])
		ymax = CalcY(line[3])

		POI_Output.write(str(xmin) + '\t')
		POI_Output.write(str(xmax) + '\t')
		POI_Output.write(str(ymin) + '\t')
		POI_Output.write(str(ymax) + '\n')

	POI_Input.close()
	POI_Output.close()
		
	
main()



