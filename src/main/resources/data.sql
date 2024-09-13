-- courses inserteds
INSERT INTO course (id, name, max_students) VALUES
('d290f1ee-6c54-4b01-90e6-d701748f0851', 'Mathematics', 30),
('5d8a3e47-37b9-4b89-b832-0345d10c2e2c', 'Physics', 25);

-- students inserteds
INSERT INTO student (id, name, age, course_id) VALUES
('b3da1c56-2adf-4ac4-9cf3-8c890028476d', 'Alice', 17, 'd290f1ee-6c54-4b01-90e6-d701748f0851'),
('ab857fa2-f9e9-4b25-bd08-c3e0537283b4', 'Bob', 16, '5d8a3e47-37b9-4b89-b832-0345d10c2e2c');
