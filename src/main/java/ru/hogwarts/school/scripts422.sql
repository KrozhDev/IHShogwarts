CREATE TABLE cars (id BIGINT PRIMARY KEY,
                   brand VARCHAR (255),
                   model VARCHAR (255),
                   price NUMERIC
                  );

CREATE TABLE persons ( id BIGINT PRIMARY KEY ,
                       name VARCHAR (255),
                       age INTEGER,
                       drivers_license BOOLEAN,
                       car_id BIGINT REFERENCES cars (id)
);