@Grab(group='net.sf.json-lib', module='json-lib', version='2.4', classifier='jdk15')

def map = [:]

def cleanRegex = []	
cleanRegex << '-master'
cleanRegex << '-pool'
cleanRegex << '-webserver'
cleanRegex << '-jwebserver'
cleanRegex << '-webcontainer'
cleanRegex << '-mla'
cleanRegex << '-mlb'
cleanRegex << '_mlb'
cleanRegex << '-mlm'
cleanRegex << '-mlu'
cleanRegex << '-mlv'
cleanRegex << '-mlx'
cleanRegex << '-resto'
cleanRegex << '-roca'
cleanRegex << '-mla_mlb_mlm'
cleanRegex << '-rest_nopaid'
cleanRegex << '-rest_paid'
cleanRegex << '_mlb_mlm'
cleanRegex << '-slave_titan'
cleanRegex << 'jobs\\d\\d?:jobs'
cleanRegex << 'jobs-jobs:jobs'
cleanRegex << 'api-api:api'
cleanRegex << '-gz\\.medium'
cleanRegex << '-categ_ml.:-categ'
cleanRegex << '-categ_resto:-categ'
cleanRegex << '-mco_rola'
cleanRegex << '-others'
cleanRegex << '-publicml.:-public'
cleanRegex << '-publicresto.:-public'

def filter = args.size() ? args[0] : null
def fileName = filter ? "./csv/${filter}.csv" : './csv/scope.csv'
println "Filename: " + fileName

def file = new File("./csv/raw.csv").eachLine { line ->
	def terms = line.split(',')
	if(terms.size() != 3 || !terms[0] || !terms[1]) return

	def key = (terms[0]+','+terms[1]).toLowerCase()

	cleanRegex.each {
		def t = it.split(':')
		key = key.replaceAll(t[0], t.size()>1?t[1]:'')
	}

	if( key.contains('test')   || 
		key.contains('sndbox') || 
		key.contains('secondary') || 
		key.contains('preprod') || 
		key.contains('alpha') || 
		key.contains('beta')) return

	if(filter && !terms[0].startsWith(filter) && !terms[1].startsWith(filter)) return


	def count = map.get(key)?:0
	
	map.put(key, count + (terms[2] as Integer))
}

def out = new File(fileName)
out.write('')

map.each{ k,v ->
	def link = k.split(',')
	if(link.size() < 2) return
	def line = "${link[0]},${link[1]},$v\n"

	print line	
	out <<  line
}