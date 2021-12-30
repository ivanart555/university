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

DROP TABLE IF EXISTS university.group_course CASCADE;

CREATE TABLE university.group_course
(
    group_id integer NOT NULL,
    course_id integer NOT NULL,
    CONSTRAINT group_course_course_id_fkey FOREIGN KEY (course_id)
        REFERENCES university.courses (course_id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT group_course_group_id_fkey FOREIGN KEY (group_id)
        REFERENCES university.groups (group_id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

DROP TABLE IF EXISTS university.lecturers CASCADE;

CREATE TABLE university.lecturers
(
    lecturer_id SERIAL NOT NULL,
    lecturer_name character varying(255) NOT NULL,
    lecturer_lastname character varying(255) NOT NULL,
    active boolean NOT NULL,
    course_id integer,
    CONSTRAINT lecturers_pkey PRIMARY KEY (lecturer_id),
    CONSTRAINT lecturers_course_id_key UNIQUE (course_id)
);


DROP TABLE IF EXISTS university.lessons CASCADE;

CREATE TABLE university.lessons
(
    lesson_id SERIAL NOT NULL,
    course_course_id integer,
    classroom_room_id integer,
    lesson_start timestamp without time zone,
    lesson_end timestamp without time zone,
    lecturer_lecturer_id integer,
    group_group_id integer,
    CONSTRAINT lessons_pkey PRIMARY KEY (lesson_id)
);

DROP TABLE IF EXISTS university.students CASCADE;

CREATE TABLE university.students
(
    student_id SERIAL NOT NULL,
    student_name character varying(255) NOT NULL,
    student_lastname character varying(255) NOT NULL,
    active boolean NOT NULL,
    group_group_id integer,
    CONSTRAINT students_pkey PRIMARY KEY (student_id)
);

