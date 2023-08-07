select * from student;

select * from student where age between 18 and 19;

select name from student;

select * from student where name like '%y%';

select * from student where age < student.id;

select * from student order by age desc;

select s.age, s.name as student_name, f.name as faculty_name, f.color from student as s, faculty as f
where s.faculty_id = f.id;