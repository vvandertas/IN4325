INSERT INTO tasks(name, question) VALUES('Task 1', 'This is a test question'), ('Task 2', 'This is another test question');
INSERT INTO hints(task_id, hint) VALUES(1, 'This is a very special first hint'), (1, 'Together with another hint for this task'),(2,'A lonesome hint for this task');
INSERT INTO keywords(task_id, keyword) VALUES(1,'atlas'),(1,'mercury');