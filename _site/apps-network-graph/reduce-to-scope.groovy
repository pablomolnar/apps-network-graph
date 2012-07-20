@Grab(group='net.sf.json-lib', module='json-lib', version='2.4', classifier='jdk15')

def map = [:]

def file = new File("./csv/raw.csv").eachLine { line ->
	def terms = line.split(',')
	if(terms.size() != 3 || !terms[0] || !terms[1]) return

	def key = terms[0]+','+terms[1]
	def count = map.get(key)?:0
	
	map.put(key, count + (terms[2] as Integer))
}

def out = new File('./csv/scope.csv')
out.write('')

map.each{ k,v ->
	println k
	def link = k.split(',')
	out << "${link[0]},${link[1]},$v\n" 

}