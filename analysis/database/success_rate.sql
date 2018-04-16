-- Combine task and group information and modify boolean answer_found to numeric
WITH success_per_user AS (
SELECT participant_type, task_id, CASE WHEN answer_found THEN 1 ELSE 0 END AS success
FROM user_task_data AS utd
JOIN filtered_users AS users
ON utd.user_id = users.id
)

-- Select the variance and mean for each task/group
(
	SELECT participant_type, task_id, COALESCE(variance(success),0.0) AS s_variance, avg(success) AS s_average
	FROM success_per_user
	GROUP BY task_id, participant_type
	ORDER BY task_id, participant_type
)
UNION
(
SELECT participant_type, 0, COALESCE(variance(success),0.0) AS s_variance, avg(success) AS s_average
FROM success_per_user
GROUP BY participant_type
ORDER BY participant_type
)