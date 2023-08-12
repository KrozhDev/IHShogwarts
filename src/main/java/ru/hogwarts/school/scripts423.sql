select s.name, s.age, f.name from student s
inner join faculty f on s.faculty_id = f.id

select s.name, s.age, f.name from student s
inner join faculty f on s.faculty_id = f.id
where s.avatar_id is not null

