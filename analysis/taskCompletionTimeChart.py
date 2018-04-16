import numpy as np
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
import matplotlib.colors as mcolors
from matplotlib.ticker import MaxNLocator
from collections import namedtuple
import operator

import database.database as db

# Task Completion Time Chart
# --------------------------
# We calculate the diff from start to end per task per group per user (each attempt)
# We then section them into task, group

def rowsToNestedData(rows, groups, tasks):
	"Transforms an array of [group, tasks, time] to a nested array of group -> task -> time"

	# Create an empty data object
	numGroups = len(groups)
	numTasks = len(tasks)
	
	data = [[[] for t in range(numTasks)] for g in range(numGroups)] # [groups: [tasks: [time: ,]]]

	for r in range(len(rows)):
		g = groups.index(rows[r][0]) # Find the group and task this row belongs to
		t = tasks.index(rows[r][1])
		data[g][t].append(rows[r][2]) # Add the time

	return data

# [group][task] -> [times]
def drawTaskCompletionTimeChart(data):
	"Receives an n-tuple of m-tuples of arrays as a list of values for m groups on n tasks"

	groups = ("No tip", "Specific tip", "Generic tip", "Adversarial tip")
	tasks = ("Task 1\n\"hydra\"", "Task 2\n\"quirinius\"", "Task 3\n\"dinosaur\"", "Task 4\n\"cherokee\"", "Overall")

	# Alpha range
	min_alpha = 0.9
	max_alpha = 0.2

	nG = len(groups)
	nT = len(tasks)

	def calcAlpha (g) :
		"Returns the alpha value for the gth group."
		return min_alpha + g * (max_alpha - min_alpha) / (nG-1)

	# The range of alpha values, one for each group
	alphaRange = [calcAlpha(i) for i in range(nG)]

	# Dimensions
	task_width = 0.7
	bar_width = task_width / (nG-1)
	scale = 1.3 # move tasks farther apart

	index = np.arange(1, nT+1)

	def positionsI (g) :
		"Returns the array of positions for the ith group."
		return index + (bar_width * g - task_width / 2) / scale;

	fig, ax = plt.subplots()

	# For the legend
	patches = []

	# For each group
	for g in range(0, nG):

		color = mcolors.to_rgba('#000000', calcAlpha(g)) 
		# Add to the legend
		patches.append(mpatches.Patch(color=color, label=groups[g]))

		print("Group " + str(g))
		print(data[g])

		# Draw the boxplots for this group
		bp = ax.boxplot(
			data[g], # [arrTask1, arrTask2, ...]
			positions = positionsI(g),
			widths = bar_width * 2/3,
			sym="+",
			 patch_artist=True
			)

		for box in bp['boxes']:
		    # change outline color
		    # box.set( color=mcolors.to_rgb('#000000'), linewidth=2)
		    # change fill color
		    box.set( facecolor = color )

		## change color and linewidth of the medians
		for median in bp['medians']:
			median.set(color='#000000', linewidth=1 )

	# Diagram configuration	
	ax.set_xlabel('')
	ax.set_ylabel('Time per task, sec')
	ax.set_title('')
	# ax.set_xticks(index)
	ax.set_xticks(range(1, nT+1))
	ax.set_xticklabels(tasks)
	ax.legend(handles=patches)

	## Remove top axes and right axes ticks
	ax.get_xaxis().tick_bottom()
	ax.get_yaxis().tick_left()

	# fig.tight_layout()
	ax.set_xlim([0, nT+1])

	# Display the plot
	plt.show()


# Test this function
def drawFromDatabase() :

	# Get the results from the database
	succ_rate = open("database/completion_time.sql", "r") 
	sql_sr = succ_rate.read()
	rows = db.selectFromDB(sql_sr) # [group, task, time]

	# Add 'OVERALL'
	overall = [[row[0], 'OVERALL', row[2]] for row in rows]
	rowsAndOverall = rows + overall

	# Transform to correct format
	groups = ["NOHINT", "SPECIFICHINT", "GENERICHINT", "ADVERSARIALHINT"]
	tasks = [1,2,3,4, 'OVERALL']

	for row in rowsAndOverall:
		print(row)

	# Group by task/group
	data = rowsToNestedData(rowsAndOverall, groups, tasks)

	print(data)

	# Plot data
	drawTaskCompletionTimeChart(data)

drawFromDatabase()