-- Combine task and group information and subtract the correct answer from the attempts
WITH incorrect_submissions AS (
SELECT participant_type, task_id, attempts - (CASE WHEN answer_found THEN 1 ELSE 0 END) AS inc_subs
FROM user_task_data AS utd
JOIN filtered_users AS users
ON utd.user_id = users.id
)

-- Select the variance and mean for each task/group
(
	SELECT participant_type, task_id, COALESCE(variance(inc_subs),0.0) AS s_variance, avg(inc_subs) AS s_average
	FROM incorrect_submissions
	GROUP BY task_id, participant_type
	ORDER BY task_id, participant_type
)
UNION
(
SELECT participant_type, 0, COALESCE(variance(inc_subs),0.0) AS s_variance, avg(inc_subs) AS s_average
FROM incorrect_submissions
GROUP BY participant_type
ORDER BY participant_type
)