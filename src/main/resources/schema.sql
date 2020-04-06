CREATE TABLE study_group (
    id int NOT NULL,
    name varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE student (
    id int NOT NULL,
    surname varchar(255),
    name varchar(255),
    second_name varchar(255),
    study_group_id int,
    PRIMARY KEY (id),
    FOREIGN KEY (study_group_id) REFERENCES study_group(id)
);

CREATE TABLE student_local (
    id int NOT NULL,
    surname varchar(255),
    name varchar(255),
    second_name varchar(255),
    study_group_id int,
    PRIMARY KEY (id),
    FOREIGN KEY (study_group_id) REFERENCES study_group(id)
);

CREATE TABLE subject (
    id int NOT NULL,
    name varchar(255),
    short_name varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE exam_type (
                         id int NOT NULL,
                         type varchar(255),
                         PRIMARY KEY (id)
);

CREATE TABLE study_plan (
                         id int NOT NULL,
                         subject_id int,
                         exam_type_id int,
                         PRIMARY KEY (id),
                         FOREIGN KEY (subject_id) REFERENCES subject(id),
                         FOREIGN KEY (exam_type_id) REFERENCES exam_type(id)
);

CREATE TABLE mark (
                           id int NOT NULL,
                           name varchar(255),
                           value varchar(255),
                           PRIMARY KEY (id)
);

CREATE TABLE journal (
                            id int NOT NULL,
                            student_id int,
                            study_plan_id int,
                            in_time int,
                            count int,
                            mark_id int,
                            PRIMARY KEY (id),
                            FOREIGN KEY (student_id) REFERENCES student(id),
                            FOREIGN KEY (study_plan_id) REFERENCES study_plan(id),
                            FOREIGN KEY (mark_id) REFERENCES mark(id)
);

INSERT INTO study_group (id, name)
VALUES
       (1, 'ИКБО-02-16'),
       (2, 'ИКБО-03-16');

INSERT INTO student_local (id, surname, name, second_name, study_group_id)
VALUES
(850514, 'Каженцев', 'Василий', 'Александрович', 2);

INSERT INTO subject (id, name, short_name)
VALUES
(1, 'Проектирование информационных систем', 'ПрИС'),
(2, 'Системы искусственного интеллекта', 'СИИ'),
(3, 'Программная инженерия', 'ПИ'),
(4, 'Национальная система информационной безопасности', 'НСИБ'),
(5, 'Системный анализ', 'СисАнал'),
(6, 'Распределенные базы данных', 'РБД'),
(7, 'Системное программное обеспечение', 'СПО');

INSERT INTO exam_type (id, type)
VALUES
(1, 'Экзамен'),
(2, 'Зачет'),
(3, 'Зачет с оценкой'),
(4, 'Курсовая');

INSERT INTO study_plan (id, subject_id, exam_type_id)
VALUES
(1, 1, 1),
(2, 1, 4),
(3, 2, 1),
(4, 3, 1),
(5, 4, 2),
(6, 5, 1),
(7, 6, 2),
(8, 7, 1);

INSERT INTO mark (id, name, value)
VALUES
(1, 'Отлично', 5),
(2, 'Хорошо', 4),
(3, 'Удовлетворительно', 3),
(4, 'Неудовлетворительно', 2),
(5, 'Зачет', 'з'),
(6, 'Незачет', 'н'),
(7, 'Неявка', '');