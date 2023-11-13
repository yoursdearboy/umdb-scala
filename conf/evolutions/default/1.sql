-- Persons schema

-- !Ups

CREATE TABLE "person" (
    "id" SERIAL NOT NULL PRIMARY KEY,
    "sex" VARCHAR,
    "birth_date" TIMESTAMP,
    "death_date" TIMESTAMP,
    "death_date_estimated_indicator" BOOLEAN,
    "death_indicator" BOOLEAN
);

-- !Downs

DROP TABLE person;
