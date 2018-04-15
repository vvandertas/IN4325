SELECT
	participant_type, task_id, COUNT(*)
FROM user_task_data AS utd
JOIN filtered_users AS users
ON utd.user_id = users.id
WHERE answer_found
GROUP BY participant_type, task_id
ORDER BY participant_type, task_id

