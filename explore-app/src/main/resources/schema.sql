drop table if exists "explore.user";
drop table if exists "explore.category";
drop table if exists "explore.event";
drop table if exists "explore.compilation";
drop table if exists "explore.request";
drop table if exists "explore.request_status";
drop table if exists "explore.event_state";
drop table if exists "explore.commentary";

CREATE TABLE "explore.user" (
  "id" bigint,
  "name" varchar(255),
  "email" varchar(255),
  PRIMARY KEY ("id")
);

CREATE TABLE "explore.category" (
  "id" bigint,
  "name" varchar(255),
  PRIMARY KEY ("id")
);

CREATE TABLE "explore.event" (
  "id" bigint,
  "annotation" varchar(255),
  "category_id" bigint,
  "confirmed_requests" bigint,
  "created_on" TIMESTAMP WITHOUT TIME ZONE,
  "description" varchar(255),
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
  PRIMARY KEY ("id")
);

CREATE TABLE "explore.compilation" (
  "id" bigint,
  "event_id" bigint,
  "user_id" bigint,
  "pinned" boolean,
  "title" varchar(255),
  PRIMARY KEY ("id")
);

CREATE TABLE "explore.request" (
  "id" bigint,
  "user_id" bigint,
  "event_id" bigint,
  "status_id" bigint,
  "created_on" TIMESTAMP WITHOUT TIME ZONE,
  PRIMARY KEY ("id")
);

CREATE TABLE "explore.request_status" (
  "id" bigint,
  "name" varchar(255),
  PRIMARY KEY ("id")
);

CREATE TABLE "explore.event_state" (
  "id" bigint,
  "name" varchar(255),
  PRIMARY KEY ("id")
);

CREATE TABLE "explore.commentary" (
  "id" bigint,
  "user_id" bigint,
  "event_id" bigint,
  "text" varchar(255),
  PRIMARY KEY ("id")
);

