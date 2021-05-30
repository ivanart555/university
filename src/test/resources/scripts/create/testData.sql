INSERT INTO university.students(
	student_name, student_lastname, active)
	VALUES ('Peter', 'Jackson', true), ('Alex', 'Smith', true), ('Ann', 'White', true);
	
INSERT INTO university.courses(
	course_name)
	VALUES ('math'), ('biology'), ('history'), ('english');
	
INSERT INTO university.groups(
	group_id, group_name)
	VALUES (1, 'AB-01'), (2, 'AB-03'), (3, 'AC-05');
	
INSERT INTO university.lecturers(
	lecturer_id, lecturer_name, lecturer_lastname, active)
	VALUES (1, 'Alex', 'White', true), (2, 'Peter', 'Gray', true), (3, 'Elena', 'Smith', true);
	
INSERT INTO university.students_courses(
	student_id, course_id)
	VALUES (1, 1), (1, 2), (1, 3), (2, 1), (2, 2), (2, 3);