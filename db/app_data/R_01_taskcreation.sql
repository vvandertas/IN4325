INSERT INTO tasks(name, question)
VALUES('Task 1', 'I can grow body back in about two days if cut in half. Many scientists think I don''t undergo senescence. What am I?'),
  ('Task 2', 'Of the Romans "group of three" gods in the Archaic Triad, which one did not have a Greek counterpart?'),
  ('Task 3', 'As George surveyed the "waterless place", he unearthed some very important eggs of what animal?'),
  ('Task 4', 'If you were in the basin of the Somme River at summers end in 1918, what language would you have had to speak to understand coded British communications?');
INSERT INTO hints(task_id, hint)
VALUES(1, 'Find what is senescence'), (1, 'Find who does not undergo senescence'),(1,'Find who can also regenerate body and choose the one that satisfies both conditions'),
  (2,'Find the names of the gods from the Archaic triad'),(2, 'For each of the gods, find a Greek counterpart'),
  (3,'Find what is the "waterless place" mentioned in the question'), (3, 'Search for important eggs discovery in this waterless place'),
  (4,'Find the name of the battle mentioned in the question'), (4,'Search for which coded communications language was used in this battle');
INSERT INTO keywords(task_id, keyword) VALUES(1,'atlas'),(1,'mercury');