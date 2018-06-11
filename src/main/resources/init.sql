DROP TABLE IF EXISTS shared;
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
    password TEXT ,
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
	('Test SCH for User1', null, 1),
	('Test SCH for User2', null, 2),
	('Test SCH for User3', null, 3),
	('Test SCH for User4', null, 4),
	('Test SCH for User5', null, 5),
	('Test SCH for User6', 'Something useful', 6)
;

/* ***** DAYS ***** */

CREATE TABLE days (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    name_id TEXT NOT NULL,
    schedule_id INTEGER NOT NULL,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE
);

INSERT INTO days (name, name_id, schedule_id) VALUES
	('Monday', 'mo', 1),
	('Tuesday', 'tu', 1),
	('Wednesday', 'we', 1),
	('Thursday', 'th', 1),
	('Friday', 'fr', 1),
	('Saturday', 'sa', 1),
	('Sunday', 'su', 1),

	('Monday', 'mo', 2),
    ('Tuesday', 'tu', 2),
    ('Wednesday', 'we', 2),
    ('Thursday', 'th', 2),
    ('Friday', 'fr', 2),
    ('Saturday', 'sa', 2),
    ('Sunday', 'su', 2),

    ('Monday', 'mo', 3),
    ('Tuesday', 'tu', 3),
    ('Wednesday', 'we', 3),
    ('Thursday', 'th', 3),
    ('Friday', 'fr', 3),
    ('Saturday', 'sa', 3),
    ('Sunday', 'su', 3),

    ('Monday', 'mo', 4),
    ('Tuesday', 'tu', 4),
    ('Wednesday', 'we', 4),
    ('Thursday', 'th', 4),
    ('Friday', 'fr', 4),
    ('Saturday', 'sa', 4),
    ('Sunday', 'su', 4),

    ('Monday', 'mo', 5),
    ('Tuesday', 'tu', 5),
    ('Wednesday', 'we', 5),
    ('Thursday', 'th', 5),
    ('Friday', 'fr', 5),
    ('Saturday', 'sa', 5),
    ('Sunday', 'su', 5),

    ('Monday', 'mo', 6),
    ('Tuesday', 'tu', 6),
    ('Wednesday', 'we', 6),
    ('Thursday', 'th', 6),
    ('Friday', 'fr', 6),
    ('Saturday', 'sa', 6),
    ('Sunday', 'su', 6)
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

	('ROBI 1', 5),
	('ROBI 2', 5),

	('Task A', 6),
	('Task B', 6),
	('Task C', 6)
;

/* ***** HOURS ***** */

CREATE TABLE hours (
    id SERIAL PRIMARY KEY,
    name INTEGER NOT NULL,
    task_id INTEGER NULL,
    day_id INTEGER NOT NULL,
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
    FOREIGN KEY (day_id) REFERENCES days(id) ON DELETE CASCADE
);

/* ***** TASK-DAY-SCHEDULE CONNECTION ***** */

CREATE TABLE task_day_sch (
    id SERIAL PRIMARY KEY,
    day_id INTEGER NOT NULL,
    task_id INTEGER NOT NULL,
    schedule_id INTEGER NOT NULL,
    FOREIGN KEY (day_id) REFERENCES days(id) ON DELETE CASCADE,
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE
);

/* ***** SHARED SCHEDULE ***** */

CREATE TABLE shared (
    schedule_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    PRIMARY KEY (schedule_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE
);
