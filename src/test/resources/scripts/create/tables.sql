CREATE SCHEMA IF NOT EXISTS university;

SET SCHEMA university;

DROP TABLE IF EXISTS university.classrooms CASCADE;

CREATE TABLE university.classrooms
(
    room_id SERIAL NOT NULL,
    room_name character varying(255) NOT NULL,
    CONSTRAINT classrooms_pkey PRIMARY KEY (room_id)
);

DROP TABLE IF EXISTS university.courses CASCADE;

CREATE TABLE university.courses
(
    course_id SERIAL NOT NULL,
    course_name character varying(255) NOT NULL,
    course_description character varying(255),
    CONSTRAINT courses_pkey PRIMARY KEY (course_id),
    CONSTRAINT courses_course_name_key UNIQUE (course_name)
);

DROP TABLE IF EXISTS university.groups CASCADE;

CREATE TABLE university.groups
(
    group_id SERIAL NOT NULL,
    group_name character varying(255) NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id),
    CONSTRAINT groups_group_name_key UNIQUE (group_name)
);

DROP TABLE IF EXISTS university.lecturers CASCADE;

CREATE TABLE university.lecturers
(
    lecturer_id SERIAL NOT NULL,
    lecturer_name character varying(255) NOT NULL,
    lecturer_lastname character varying(255) NOT NULL,
    CONSTRAINT lecturers_pkey PRIMARY KEY (lecturer_id)
);

DROP TABLE IF EXISTS university.lecturers_courses CASCADE;

CREATE TABLE university.lecturers_courses
(
    lecturer_id integer NOT NULL,
    course_id integer NOT NULL,
    CONSTRAINT lecturers_courses_course_id_fkey FOREIGN KEY (course_id)
        REFERENCES university.courses (course_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT lecturers_courses_lecturer_id_fkey FOREIGN KEY (lecturer_id)
        REFERENCES university.lecturers (lecturer_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

DROP TABLE IF EXISTS university.lecturers_groups CASCADE;

CREATE TABLE university.lecturers_groups
(
    lecturer_id integer NOT NULL,
    group_id integer NOT NULL,
    CONSTRAINT lecturers_groups_group_id_fkey FOREIGN KEY (group_id)
        REFERENCES university.groups (group_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT lecturers_groups_lecturer_id_fkey FOREIGN KEY (lecturer_id)
        REFERENCES university.lecturers (lecturer_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

DROP TABLE IF EXISTS university.lessons CASCADE;

CREATE TABLE university.lessons
(
    lesson_id SERIAL NOT NULL,
    course_id integer NOT NULL,
    room_id integer NOT NULL,
    lesson_start timestamp without time zone NOT NULL,
    lesson_end timestamp without time zone NOT NULL,
    CONSTRAINT lessons_pkey PRIMARY KEY (lesson_id)
);

DROP TABLE IF EXISTS university.lessons_groups CASCADE;

CREATE TABLE university.lessons_groups
(
    lesson_id integer NOT NULL,
    group_id integer NOT NULL,
    CONSTRAINT lessons_groups_group_id_fkey FOREIGN KEY (group_id)
        REFERENCES university.groups (group_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT lessons_groups_lesson_id_fkey FOREIGN KEY (lesson_id)
        REFERENCES university.lessons (lesson_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

DROP TABLE IF EXISTS university.students CASCADE;

CREATE TABLE university.students
(
    student_id SERIAL NOT NULL,
    student_name character varying(255) NOT NULL,
    student_lastname character varying(255) NOT NULL,
    group_id integer,
    CONSTRAINT students_pkey PRIMARY KEY (student_id)
);

DROP TABLE IF EXISTS university.students_courses CASCADE;

CREATE TABLE university.students_courses
(
    student_id integer NOT NULL,
    course_id integer NOT NULL,
    CONSTRAINT students_courses_course_id_fkey FOREIGN KEY (course_id)
        REFERENCES university.courses (course_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT students_courses_student_id_fkey FOREIGN KEY (student_id)
        REFERENCES university.students (student_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

DROP TABLE IF EXISTS university.students_groups CASCADE;

CREATE TABLE university.students_groups
(
    student_id integer NOT NULL,
    group_id integer NOT NULL,
    CONSTRAINT students_groups_group_id_fkey FOREIGN KEY (group_id)
        REFERENCES university.groups (group_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT students_groups_student_id_fkey FOREIGN KEY (student_id)
        REFERENCES university.students (student_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);