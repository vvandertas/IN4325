SELECT
	participant_type, task_id, EXTRACT(EPOCH FROM utd.finished_at) - EXTRACT(EPOCH FROM utd.created_at) AS ctime, utd.created_at, utd.finished_at
FROM user_task_data AS utd
JOIN filtered_users AS users
ON utd.user_id = users.id
WHERE answer_found