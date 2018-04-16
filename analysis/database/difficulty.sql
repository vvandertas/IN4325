
WITH hits_per_score_per_type AS (
-- Transform to [group][score] -> portion
SELECT 
	participant_type,
	questionnaire::hstore -> 'difficulty' AS score,
	COUNT(*) AS num_votes
FROM filtered_users
GROUP BY participant_type, questionnaire::hstore -> 'difficulty'

), group_size AS (
SELECT participant_type, COUNT(*) AS group_size
FROM filtered_users
GROUP BY participant_type
)

SELECT hits.participant_type, score, CAST(num_votes AS FLOAT)/CAST(group_size AS float) AS ratio
FROM hits_per_score_per_type AS hits
JOIN group_size
ON hits.participant_type = group_size.participant_type