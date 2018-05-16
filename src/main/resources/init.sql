DROP TABLE IF EXISTS task_day_sch;
DROP TABLE IF EXISTS hours;
DROP TABLE IF EXISTS days;
DROP TABLE IF EXISTS schedules;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL,
    first_name TEXT NULL,
    last_name TEXT NULL,
    password TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    rank TEXT NOT NULL,
	CONSTRAINT email_not_empty CHECK (email <> ''),
	CONSTRAINT password_not_empty CHECK (password <> '')
);

INSERT INTO users (name, first_name, last_name, password, email, rank) VALUES
	('Robi', 'Robert', 'Kohanyi', 'a', 'robert.kohanyi@codecool.com', 'Admin'),
	('Pako', 'Pal', 'Monoczki', 'a', 'pal.monoczki@codecool.com', 'Admin'),
	('Ben', null, null, 'a', 'o.g.bence@totalcar.hu', 'User'),
	('Tib', null, null, 'a', 'domokos.tibor.82@gmail.com', 'User'),
	('Krisz', null, null, 'a', 'kollarkr@gmail.com', 'User'),
	('Norb', 'Norbert','Hresko', 'a', 'norberthresko@gmail.com', 'User')
;


/* ***** SCHEDULE ***** */

CREATE TABLE schedules (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    content TEXT NULL,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO schedules (name, content, user_id) VALUES
	('Test SCH', null, 3),
	('Test SCH 2', 'Test content', 3),
	('Weekly shit', 'Something useful', 5),
	('Weekly shat', 'Something useful', 6)
;


/* ***** DAYS ***** */

CREATE TABLE days (
    id SERIAL PRIMARY KEY,
    name TEXT NULL,
    schedule_id INTEGER NOT NULL,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id)
);

INSERT INTO days (name, schedule_id) VALUES
	('Day 1', 1),
	('Day 2', 1),
	('Friday', 2),
	('Saturday', 2),
	('Sunday', 2),
	('Csütörtök', 3),
	('Péntek', 3)
;

/* ***** TASK ***** */

CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO tasks (name, user_id) VALUES
	('Task 1', 3),
	('Task 2', 3),
	('Task 3', 3),
	('Task 4', 3),
	('Task 5', 3),
	('Task A', 5),
	('Task B', 5),
	('Task C', 6),
	('Task D', 6),
	('Task E', 6)
;


/* ***** HOURS ***** */

CREATE TABLE hours (
    id SERIAL PRIMARY KEY,
    name INTEGER NOT NULL,
    task_id INTEGER NULL,
    day_id INTEGER NOT NULL,
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
    FOREIGN KEY (day_id) REFERENCES days(id)
);

INSERT INTO hours (name, task_id, day_id) VALUES
	(9, 1, 1),
	(10, 1, 1),
	(11, 1, 1),
	(13, 2, 1),
	(14, 2, 1),
	(10, 3, 2),
	(11, 3, 2),
	(12, 3, 2),
	(13, 4, 2),
	(14, 5, 2),
	(15, 5, 2),

	(9, 1, 3),
	(11, 2, 3),
	(15, 3, 3),
	(8, 4, 4),
	(9, 5, 5),

	(10, 6, 6),
	(11, 7, 6),
	(13, 8, 6),
	(9, 9, 7),
	(10, 10, 7)
;


/* ***** TASK-DAY-SCHEDULE CONNECTION ***** */

CREATE TABLE task_day_sch (
    id SERIAL PRIMARY KEY,
    hour_id INTEGER NOT NULL,
    task_id INTEGER NOT NULL,
    schedule_id INTEGER NOT NULL,
    FOREIGN KEY (hour_id) REFERENCES hours(id),
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id)
);

INSERT INTO task_day_sch (hour_id, task_id, schedule_id) VALUES
	(1,1,1),
	(2,1,1),
	(3,1,1),
	(4,2,1),
	(5,2,1),
	(6,3,1),
	(7,3,1),
	(8,3,1),
	(9,4,1),
	(10,5,1),
	(11,5,1),
	(12,1,2),
	(13,2,2),
	(14,3,2),
	(15,4,2),
	(16,5,2),
	(17,6,3),
	(18,7,3),
	(19,8,3),
	(20,9,3),
	(21,10,3);
