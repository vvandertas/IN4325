-- Filter users based on whether the have participated in all four tasks
SELECT 
 	users.id,
    users.participant_type,
    users.created_at,
    users.current_task_id,
    users.finished_at,
    users.questionnaire
FROM users
WHERE users.id IN (
	-- Filter those who did not go through the entire experiment 
	SELECT user_task_data.user_id
    FROM user_task_data
    GROUP BY user_task_data.user_id
    HAVING count(*) = 4
) AND users.id NOT IN (
	-- Filter those who completed tasks without searching, or spent more than 30 min on a task
	SELECT user_task_data.user_id
    FROM user_task_data
    WHERE (answer_found AND query_count = 0)
    OR EXTRACT(EPOCH FROM finished_at) - EXTRACT(EPOCH FROM created_at) > 1800
) 