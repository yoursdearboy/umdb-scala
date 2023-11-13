-- Names schema

-- !Ups

CREATE TABLE "name" (
    "id" SERIAL PRIMARY KEY,
    "person_id" INTEGER REFERENCES "person" ("id"),
    "use" VARCHAR,
    "family" VARCHAR,
    "given" VARCHAR,
    "middle" VARCHAR,
    "patronymic" VARCHAR,
    "prefix" VARCHAR,
    "suffix" VARCHAR
);

-- !Downs

DROP TABLE "name";
