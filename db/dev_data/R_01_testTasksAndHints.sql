INSERT INTO tasks(name, question, flags) VALUES('Task 1', 'This is a test question', '-"quest"')
  , ('Task 2', 'This is another test question', '-"google a day"');
INSERT INTO hints(task_id, hint_type, hint) VALUES(1, 'SPECIFIC', 'This is a very special first hint'), (1, 'SPECIFIC', 'Together with another hint for this task'),(2,'SPECIFIC','A lonesome hint for this task');
INSERT INTO keywords(task_id, keyword) VALUES(1,'atlas|altas'),(1,'mercury');