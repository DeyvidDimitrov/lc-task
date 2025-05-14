CREATE TABLE IF NOT EXISTS teachers
(
    id   UUID         NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age  INT          NOT NULL
);

CREATE TABLE IF NOT EXISTS students
(
    id   UUID         NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age  INT          NOT NULL
);

CREATE TABLE IF NOT EXISTS student_groups
(
    id   UUID NOT NULL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS courses
(
    id         UUID         NOT NULL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    type       VARCHAR(16)  NOT NULL,
    teacher_id UUID         NOT NULL,
    CONSTRAINT fk_courses_teacher FOREIGN KEY (teacher_id) REFERENCES teachers (id)
);

CREATE TABLE IF NOT EXISTS courses_students
(
    course_id  UUID NOT NULL,
    student_id UUID NOT NULL,
    PRIMARY KEY (course_id, student_id),
    CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES courses (id),
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES students (id)
);

CREATE TABLE IF NOT EXISTS students_groups
(
    group_id   UUID NOT NULL,
    student_id UUID NOT NULL,
    PRIMARY KEY (group_id, student_id),
    CONSTRAINT fk_group FOREIGN KEY (group_id) REFERENCES student_groups (id),
    CONSTRAINT fk_student_group FOREIGN KEY (student_id) REFERENCES students (id)
);

INSERT INTO teachers (id, name, age)
VALUES ('349581c2-bd2a-456d-b2da-93c90fe505db', 'John Smith', 40),
       ('d196dbf1-51e6-4f9d-a05b-35557d61d478', 'Laura Jones', 35);

INSERT INTO students (id, name, age)
VALUES ('736b088f-f862-4602-a7bc-4a7f5be391f2', 'Alice Brown', 20),
       ('c8511f22-ede3-49fa-9a1f-3230f1fa45fb', 'Bob Green', 22),
       ('3eb93f26-5e2b-4ac2-bd30-92416e85b27f', 'Charlie Black', 19);

INSERT INTO student_groups (id, name)
VALUES ('7f24ed76-bc77-4ce6-897a-1d2b8eae34fc', 'Group A'),
       ('8f08a3d1-dba8-473c-bca6-958f7ea0de04', 'Group B');

INSERT INTO courses (id, name, type, teacher_id)
VALUES ('a53007e5-73ac-409d-859e-17fffee7bd87', 'Mathematics', 'MAIN', '349581c2-bd2a-456d-b2da-93c90fe505db'),
       ('0e4c0b2d-12f5-47d2-950a-103db5e256a3', 'Physics', 'SECONDARY', 'd196dbf1-51e6-4f9d-a05b-35557d61d478');

INSERT INTO students_groups (group_id, student_id)
VALUES ('7f24ed76-bc77-4ce6-897a-1d2b8eae34fc', '736b088f-f862-4602-a7bc-4a7f5be391f2'),
       ('7f24ed76-bc77-4ce6-897a-1d2b8eae34fc', 'c8511f22-ede3-49fa-9a1f-3230f1fa45fb'),
       ('8f08a3d1-dba8-473c-bca6-958f7ea0de04', '3eb93f26-5e2b-4ac2-bd30-92416e85b27f');

INSERT INTO courses_students (course_id, student_id)
VALUES ('a53007e5-73ac-409d-859e-17fffee7bd87', '736b088f-f862-4602-a7bc-4a7f5be391f2'),
       ('a53007e5-73ac-409d-859e-17fffee7bd87', 'c8511f22-ede3-49fa-9a1f-3230f1fa45fb'),
       ('0e4c0b2d-12f5-47d2-950a-103db5e256a3', '3eb93f26-5e2b-4ac2-bd30-92416e85b27f');
