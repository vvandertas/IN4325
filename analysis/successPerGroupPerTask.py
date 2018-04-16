import numpy as np
import matplotlib.pyplot as plt
from matplotlib.ticker import MaxNLocator
from collections import namedtuple

import database.database as db

# Successful Completions
# -------------------------

def drawSuccessPerGroupPerTask(data):
	"Print a bar chart of the number of participants who found answers per group per task."

	# The labels and colors
	groupNames = ("No tip", "Specific tip", "Generic tip", "Adversarial tip")
	taskNames = ("Task 1\n\"hydra\"", "Task 2\n\"quirinius\"", "Task 3\n\"dinosaur\"", "Task 4\n\"cherokee\"")
	colors = ('0.3', '0.5', '0.7', '0.9')

	# Predefined sizes
	n_groups = 4
	n_tasks = 4

	# Dimensions
	task_width = 0.7
	bar_width = task_width / (n_groups-1);

	index = np.arange(n_tasks)

	def barX (i) :
		"Returns the x position of the i'th bar."
		return index + bar_width * i - task_width / 2;


	fig, ax = plt.subplots()

	# Draw the bar series (all tasks) for each group
	for i in range(0, n_groups ):
		rect = ax.bar(	
				x = index + bar_width * i - task_width / 2,
				height = data[i],
				width = bar_width,
                color = colors[i],
            	label=groupNames[i]
            	)

	# Diagram configuration	
	ax.set_xlabel('')
	ax.set_ylabel('Number of successful completions')
	ax.set_title('')
	ax.set_xticks(index + bar_width / n_groups)
	ax.set_xticklabels(taskNames)
	ax.legend(loc=1)

	fig.tight_layout()

	# Display the plot
	plt.show()

# Render From Database
def renderFromDatabase () :
	
	# Get the results from the database
	succ_rate = open("database/success_per_group_per_task.sql", "r") 
	sql_sr = succ_rate.read()
	rows = db.selectFromDB(sql_sr)

	# Transform to correct format
	groups = ["NOHINT", "SPECIFICHINT", "GENERICHINT", "ADVERSARIALHINT"]
	tasks = [1,2,3,4] # 0: Overall

	# Group by task/group
	data = db.rowsToNestedData(rows, groups, tasks)

	# Plot data
	drawSuccessPerGroupPerTask(data)

# Run
renderFromDatabase()