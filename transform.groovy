@Grab(group='net.sf.json-lib', module='json-lib', version='2.4', classifier='jdk15')
def map = [:]

if(args.size() == 0 || !args[0]) {
	println "Pass an argument!"
	System.exit(0)
}

def file = new File("./csv/${args[0]}.csv").eachLine { line ->
	def terms = line.split(',')
	def size = 0
	if(terms.size() == 3) terms[2] as Integer
	else if(terms.size() != 2) return

	def from = map.get(terms[0])

	if(!from) {
	  from = [name: terms[0], size: size, imports: []]
	  map.put(terms[0], from)
	}

	from.imports << terms[1]


	if(!map.get(terms[1])) {
		map.put(terms[1], [name: terms[1], size: 0, imports: []])	
	}
}

new File("./json/${args[0]}.json").write(net.sf.json.JSONArray.fromObject(map.values() as List).toString())
