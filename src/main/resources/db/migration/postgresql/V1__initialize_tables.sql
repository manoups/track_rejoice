CREATE EXTENSION IF NOT EXISTS postgis;
CREATE EXTENSION IF NOT EXISTS postgis_topology;

CREATE TABLE lookup_subject
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    last_seen_location GEOMETRY(Point, 4326)                   NOT NULL,
    last_seen_date     TIMESTAMP WITHOUT TIME ZONE,
    version            BIGINT                                  NOT NULL,
    created_at         TIMESTAMP WITHOUT TIME ZONE,
    updated_at         TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_lookupsubject PRIMARY KEY (id)
);

CREATE TABLE pet
(
    id               BIGINT  NOT NULL,
    name             VARCHAR(255),
    transponder_code INTEGER NOT NULL,
    species          VARCHAR(255),
    breed            VARCHAR(255),
    color            VARCHAR(255),
    CONSTRAINT pk_pet PRIMARY KEY (id)
);

CREATE TABLE trace
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    version           BIGINT                                  NOT NULL,
    lookup_subject_id BIGINT                                  NOT NULL,
    location          GEOMETRY(Point, 4326)                   NOT NULL,
    date              TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_trace PRIMARY KEY (id)
);

ALTER TABLE pet
    ADD CONSTRAINT FK_PET_ON_ID FOREIGN KEY (id) REFERENCES lookup_subject (id);

ALTER TABLE trace
    ADD CONSTRAINT FK_TRACE_ON_LOOKUPSUBJECT FOREIGN KEY (lookup_subject_id) REFERENCES lookup_subject (id);

CREATE INDEX pet_geometry_point_idx ON lookup_subject USING gist(last_seen_location);