import psycopg2

def selectFromDB (query) :
	"Return all results from applying the selected query to the PSQL 'experiment' database"

	# Connect to db
	conn = psycopg2.connect("dbname=experiment user=experiment password=experiment")

	# Open a cursor to perform database operations
	cur = conn.cursor()

	# Query the database and obtain data as Python objects
	cur.execute(query)
	
	result = cur.fetchall()

	# Close communication with the database
	cur.close()
	conn.close()

	return result

def rowsToNestedData(rows, groups, tasks):
	"Transforms an array of [group, tasks, value] to a nested array of [group][task] -> value"

	numGroups = len(groups)
	numTasks = len(tasks)

	# Create an empty data object	
	data = [[0.0 for t in range(numTasks)] for g in range(numGroups)]

	# Fill with data from rows
	for r in range(len(rows)):
		g = groups.index(rows[r][0]) # Find the group and task this row belongs to
		t = tasks.index(rows[r][1])
		data[g][t] = rows[r][2] # Add the time

	return data