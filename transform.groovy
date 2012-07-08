@Grab(group='net.sf.json-lib', module='json-lib', version='2.4', classifier='jdk15')

def map = [:]

def file = new File("./csv/dept.csv").eachLine { line ->
	def terms = line.split(',')
	if(terms.size() != 2) return

	def from = map.get(terms[0])

	if(!from) {
	  from = [name: terms[0], size: 0, imports: []]
	  map.put(terms[0], from)
	}

	from.imports << terms[1]


	if(!map.get(terms[1])) {
		map.put(terms[1], [name: terms[1], size: 0, imports: []])	
	}
}

new File('links.json').write(net.sf.json.JSONArray.fromObject(map.values() as List).toString())
