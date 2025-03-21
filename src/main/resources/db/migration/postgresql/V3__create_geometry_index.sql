CREATE INDEX geometry_idx ON be_on_the_look_out USING gist(last_seen_location);
CREATE INDEX be_on_the_look_out_state_index ON be_on_the_look_out (state);