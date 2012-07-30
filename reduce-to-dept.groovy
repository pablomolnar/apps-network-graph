@Grab(group='net.sf.json-lib', module='json-lib', version='2.4', classifier='jdk15')

def map = [:]

def file = new File("./csv/raw.csv").eachLine { line ->
	def terms = line.split(',')
	if(terms.size() != 3 || !terms[0] || !terms[1]) return

	def from = terms[0].split('-')
	def to = terms[1].split('-')

	if(from.size() == 0 || to.size() == 0) return

	def A
	if(from.size() >= 1){
		A = from[0]
	}

	def B
	if(to.size() >= 1){
		B = to[0]
	}

	def key = "$A,$B"
	def count = map.get(key)?:0
	
	map.put(key, count + (terms[2] as Integer))
}

def out = new File('./csv/dept.csv')
out.write('')

map.each{ k,v ->
	println k
	def link = k.split(',')
	out << "${link[0]},${link[1]},$v\n"
}