ALTER TABLE student ADD constraint age_constraint check ( age >= 16 );
ALTER TABLE student ADD constraint name_constraint check ( name != '' );
ALTER TABLE student ALTER COLUMN name SET NOT NULL;
ALTER TABLE student ADD constraint name_unique unique (name);
Alter Table faculty Add constraint col_name_unique unique (color,name);
ALTER TABLE student alter column age set DEFAULT 20 for ;



