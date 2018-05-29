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
	('Test SCH for User3', null, 3),
	('Test SCH for User1', null, 1),
	('Test SCH for User2', null, 2),
	('Test SCH for User4', null, 4),
	('Test SCH for User5',null, 5),
	('Test SCH for User6', 'Something useful', 6);



/* ***** DAYS ***** */

CREATE TABLE days (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    name_id TEXT NOT NULL,
    schedule_id INTEGER NOT NULL,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE
);

INSERT INTO days (name, name_id, schedule_id) VALUES
	('Day 1', 'mo', 1),
	('Day 2', 'tu', 1),
	('Day 3', 'we', 1),
	('Day 4', 'th', 1),
	('Day 5', 'fr', 1),
	('Day 6', 'sa', 1),
	('Day 7', 'su', 1),

	('Day 1', 'mo', 2),
    ('Day 2', 'tu', 2),
    ('Day 3', 'we', 2),
    ('Day 4', 'th', 2),
    ('Day 5', 'fr', 2),
    ('Day 6', 'sa', 2),
    ('Day 7', 'su', 2),

    ('Day 1', 'mo', 3),
    ('Day 2', 'tu', 3),
    ('Day 3', 'we', 3),
    ('Day 4', 'th', 3),
    ('Day 5', 'fr', 3),
    ('Day 6', 'sa', 3),
    ('Day 7', 'su', 3),

    ('Day 1', 'mo', 4),
    ('Day 2', 'tu', 4),
    ('Day 3', 'we', 4),
    ('Day 4', 'th', 4),
    ('Day 5', 'fr', 4),
    ('Day 6', 'sa', 4),
    ('Day 7', 'su', 4),

    ('Day 1', 'mo', 5),
    ('Day 2', 'tu', 5),
    ('Day 3', 'we', 5),
    ('Day 4', 'th', 5),
    ('Day 5', 'fr', 5),
    ('Day 6', 'sa', 5),
    ('Day 7', 'su', 5),

    ('Day 1', 'mo', 6),
    ('Day 2', 'tu', 6),
    ('Day 3', 'we', 6),
    ('Day 4', 'th', 6),
    ('Day 5', 'fr', 6),
    ('Day 6', 'sa', 6),
    ('Day 7', 'su', 6)
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


