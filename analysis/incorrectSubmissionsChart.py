import numpy as np
import matplotlib.pyplot as plt
from matplotlib.ticker import MaxNLocator
from collections import namedtuple

import database.database as db

# Incorrect Submissions
# ---------------------
# Each player has a number of incorrect submissions before completing the task
# This chart shows the mean and standard deviation of this number from all participants per group per task
# QUERY? 

def drawIncorrectSubmissionsChart(means, variances):
	"Print a bar chart of average success rate for four groups on four tasks and an overall score."

	# The labels and colors
	groupNames = ("No tip", "Specific tip", "Generic tip", "Adversarial tip")
	taskNames = ("Task 1\n\"hydra\"", "Task 2\n\"quirinius\"", "Task 3\n\"dinosaur\"", "Task 4\n\"cherokee\"", "Overall")
	colors = ('0.3', '0.5', '0.7', '0.9')

	# Predefined sizes
	n_groups = 4
	n_tasks = 5

	# Dimensions
	task_width = 0.7
	bar_width = task_width / n_groups;

	index = np.arange(n_tasks)

	def barX (i) :
		"Returns the x position of the i'th bar."
		return index + bar_width * i - task_width / 2;


	fig, ax = plt.subplots()

	# Draw the bar series (all tasks) for each group
	for i in range(0, n_groups ):
		print("--------------------------")
		print(index + bar_width * i - task_width / 2)
		print(means[i])
		print(bar_width)
		print(colors[i])
		print(variances[i])
		print(groupNames[i])
		rect = ax.bar(	
				x = index + bar_width * i - task_width / 2,
				height = means[i],
				width = bar_width,
                color = colors[i],
                yerr = variances[i], 
            	label=groupNames[i]
            	)
		print("------------------END")

	# Diagram configuration	
	ax.set_xlabel('')
	ax.set_ylabel('Number of incorrect submissions')
	ax.set_title('')
	ax.set_xticks(index + bar_width / n_groups)
	ax.set_xticklabels(taskNames)
	ax.legend()

	ax.set_ylim([0, 31])

	fig.tight_layout()

	# Display the plot
	plt.show()


# Render From Database
def renderFromDatabase () :
	
	# Get the results from the database
	res = open("database/incorrect_submissions.sql", "r") 
	sql_sr = res.read()
	rows = db.selectFromDB(sql_sr)

	# Transform to correct format
	groups = ["NOHINT", "SPECIFICHINT", "GENERICHINT", "ADVERSARIALHINT"]
	tasks = [1,2,3,4,0] # 0: Overall

	# Map to separate mean/variance lists [group, task, mean/var]
	means = [[row[0], row[1], row[3]] for row in rows]
	variances = [[row[0], row[1], row[2]] for row in rows]


	# Group by task/group
	meansGrouped = db.rowsToNestedData(means, groups, tasks)
	variancesGrouped = db.rowsToNestedData(variances, groups, tasks)

	# Plot data
	drawIncorrectSubmissionsChart(meansGrouped, variancesGrouped)

# Run
renderFromDatabase()