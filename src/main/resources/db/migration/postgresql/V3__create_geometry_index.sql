CREATE INDEX pet_geometry_point_idx ON pet USING gist(last_seen_location);
CREATE INDEX pet_geometry_multipoint_idx ON item USING gist(last_seen_location);