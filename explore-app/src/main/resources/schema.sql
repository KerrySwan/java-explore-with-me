drop table if exists "compilation_event";
drop table if exists "user";
drop table if exists "category";
drop table if exists "event";
drop table if exists "compilation";
drop table if exists "request";
drop table if exists "request_status";
drop table if exists "event_state";
drop table if exists "commentary";

CREATE TABLE "user" (
  "id" bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  "name" varchar(255),
  "email" varchar(255),
  PRIMARY KEY ("id")
);

CREATE TABLE "category" (
  "id" bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  "name" varchar(255)
  constraint uk_name_categories
              unique,
  PRIMARY KEY ("id")
);

CREATE TABLE "event" (
  "id" bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  "annotation" varchar(5000),
  "category_id" bigint,
  "confirmed_requests" bigint,
  "created_on" TIMESTAMP WITHOUT TIME ZONE,
  "description" varchar(5000),
  "event_date" TIMESTAMP WITHOUT TIME ZONE,
  "user_id" bigint,
  "location_lat" decimal,
  "location_lon" decimal,
  "paid" boolean,
  "participant_limit" bigint,
  "published_on" TIMESTAMP WITHOUT TIME ZONE,
  "request_moderation" boolean,
  "state_id" bigint,
  "title" varchar(255),
  "views" bigint,
  PRIMARY KEY ("id")
);

CREATE TABLE "compilation" (
  "id" bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  "event_id" bigint,
  "user_id" bigint,
  "pinned" boolean,
  "title" varchar(255),
  PRIMARY KEY ("id")
);

CREATE TABLE "request" (
  "id" bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  "user_id" bigint,
  "event_id" bigint,
  "status_id" bigint,
  "created_on" TIMESTAMP WITHOUT TIME ZONE,
  PRIMARY KEY ("id")
);

CREATE TABLE "request_status" (
  "id" bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  "name" varchar(255),
  PRIMARY KEY ("id")
);

CREATE TABLE "event_state" (
  "id" bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  "name" varchar(255),
  PRIMARY KEY ("id")
);

CREATE TABLE "commentary" (
  "id" bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  "user_id" bigint,
  "event_id" bigint,
  "text" varchar(5000),
  PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS compilation_event (
     compilation_id BIGINT REFERENCES compilation(id) ON DELETE CASCADE,
     event_id BIGINT REFERENCES event(id) ON DELETE CASCADE,
     CONSTRAINT ce_pk PRIMARY KEY (compilation_id, event_id)
);