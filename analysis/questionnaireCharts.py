import numpy as np
import matplotlib.pyplot as plt
from matplotlib.ticker import MaxNLocator
from collections import namedtuple
import operator

import database.database as db

# Questionnaire 
# -------------
# Participants each answered a few questions on a 5 point likert scale
# We count the hits on each likert point and divide them by the total number of people in the group
# SELECT group, task, score, COUNT(*) AS hits FROM answers GROUP BY group, task, score

def drawStackedBarChart(data, groups, labels, title):
	"Receives an n-tuple of m-tuples with values for m groups on n labels"

	# Alpha range
	min_alpha = 0.2
	max_alpha = 0.9

	m = len(groups)
	n = len(labels)

	def calcAlpha (i) :
		"Returns the alpha value for the ith group."
		return min_alpha + i * (max_alpha - min_alpha) / (n-1)

	# Dimensions
	bar_width = 0.7

	index = np.arange(m)

	fig, ax = plt.subplots()

	handles = []

	# Draw the bar series (all tasks) for each label
	acc = tuple(0 for i in range(m))
	for i in range(0, n):
		rect = ax.bar(	
				x = index,
				height = data[i],
				bottom = acc,
				width = bar_width,
                color = str(calcAlpha(i)),
                alpha = 1,
            	label = labels[i]
            	)
		handles.append(rect)
		acc = tuple(map(operator.add, acc, data[i]))

	# Diagram configuration	
	ax.set_xlabel('')
	ax.set_ylabel(title)
	ax.set_title('')
	ax.set_xticks(index)
	ax.set_xticklabels(groups)
	ax.legend(handles=handles[::-1], loc=1) # Reverse order of legend

	fig.tight_layout()

	# Display the plot
	plt.show()


def drawLikeabilityChart () :

	# Get the results from the database
	succ_rate = open("database/likeability.sql", "r") 
	sql_sr = succ_rate.read()
	rows = db.selectFromDB(sql_sr) # [participant_type, score, count]

	groups = ["NOHINT", "SPECIFICHINT", "GENERICHINT", "ADVERSARIALHINT"]
	scores = ['1','2','3','4','5']

	# Flip the columns to [score, participant_type, count]
	groupedCount = db.rowsToNestedData([[row[1], row[0], row[2]] for row in rows], scores, groups)

	groupsLabels = ["No tip", "Specific tip", "Generic tip", "Adversarial tip"]
	scoreLabels = ["Disliked a lot", "Disliked a little", "Neutral", "Liked a little", "Liked a lot"]
	drawStackedBarChart(groupedCount, groupsLabels, scoreLabels, "")

def drawUsefulnessChart () :

	# Get the results from the database
	succ_rate = open("database/usefulness.sql", "r") 
	sql_sr = succ_rate.read()
	rows = db.selectFromDB(sql_sr) # [participant_type, score, count]

	groups = ["SPECIFICHINT", "GENERICHINT", "ADVERSARIALHINT"]
	scores = ['1','2','3','4','5']

	# Flip the columns to [score, participant_type, count]
	groupedCount = db.rowsToNestedData([[row[1], row[0], row[2]] for row in rows], scores, groups)

	groupsLabels = ["Specific tip", "Generic tip", "Adversarial tip"]
	scoreLabels = ["Detrimental", "Distracting", "Not really", "Useful", "Extremely Useful"]
	drawStackedBarChart(groupedCount, groupsLabels, scoreLabels, "")



def drawDifficultyChart () :

	# Get the results from the database
	succ_rate = open("database/difficulty.sql", "r") 
	sql_sr = succ_rate.read()
	rows = db.selectFromDB(sql_sr) # [participant_type, score, count]

	groups = ["NOHINT", "SPECIFICHINT", "GENERICHINT", "ADVERSARIALHINT"]
	scores = ['1','2','3','4','5']

	# Flip the columns to [score, participant_type, count]
	groupedCount = db.rowsToNestedData([[row[1], row[0], row[2]] for row in rows], scores, groups)

	groupsLabels = ["No tip", "Specific tip", "Generic tip", "Adversarial tip"]
	scoreLabels = ["Very difficult", "Difficult", "So and so", "Easy", "Extremely Easy"]
	drawStackedBarChart(groupedCount, groupsLabels, scoreLabels, "")

drawLikeabilityChart()
drawUsefulnessChart()
drawDifficultyChart()