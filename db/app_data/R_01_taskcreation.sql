INSERT INTO tasks(name, question, flags)
VALUES('Task 1', 'I can grow body back in about two days if cut in half. Many scientists think I don''t undergo senescence. What am I?', '-"scientists think I do not undergo senescense" -"scientists believe I do not undergo senescense" -"many scientists believe I do not" -"google a day" NOT answers.com'),
  ('Task 2', 'Of the Romans "group of three" gods in the Archaic Triad, which one did not have a Greek counterpart?', '-"google a day" -"Of the Romans group of three gods in the Archaic Triad, which one did not have a Greek counterpart?" - '),
  ('Task 3', 'As George surveyed the "waterless place", he unearthed some very important eggs of what animal?', '-"google a day" -"As George surveyed the waterless place, he unearthed some very important eggs of what animal?"'),
  ('Task 4', 'If you were in the basin of the Somme River at summers end in 1918, what language would you have had to speak to understand coded British communications?', '-"google a day" -"If you were in the basin of the Somme River at summers end in 1918, what language would you have had to speak to understand coded British communications?"');
INSERT INTO hints(task_id, hint_type ,hint)
VALUES(1, 'SPECIFIC','Find what is senescence'), (1, 'SPECIFIC','Find who does not undergo senescence'),(1, 'SPECIFIC', 'Find who can also regenerate body and choose the one that satisfies both conditions'),
  (2, 'SPECIFIC','Find the names of the gods from the Archaic triad'),(2,'SPECIFIC', 'For each of the gods, find a Greek counterpart'),
  (3,'SPECIFIC','Find what is the "waterless place" mentioned in the question'), (3, 'SPECIFIC','Search for important eggs discovery in this waterless place'),
  (4,'SPECIFIC','Find the name of the battle mentioned in the question'), (4,'SPECIFIC','Search for which coded communications language was used in this battle'),
  (1, 'ADVERSARIAL','Search for all animals that do not undergo senescence'),
  (2, 'ADVERSARIAL', 'Search for all Greek gods'),
  (3,'ADVERSARIAL','Search which animals lay eggs?'),
  (4,'ADVERSARIAL','Search for British coded language?');

INSERT INTO keywords(task_id, keyword) VALUES(1,'hydra|hyrad'),(2,'quirinus|quirinius|qurinus'),(3,'dinosaur|dinosuar'),(4,'cherokee|cheroke');
