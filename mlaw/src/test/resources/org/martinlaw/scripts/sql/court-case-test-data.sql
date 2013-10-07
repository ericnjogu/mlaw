---
-- #%L
-- mlaw
-- %%
-- Copyright (C) 2012, 2013 Eric Njogu (kunadawa@gmail.com)
-- %%
-- This program is free software: you can redistribute it and/or modify
-- it under the terms of the GNU General Public License as
-- published by the Free Software Foundation, either version 3 of the 
-- License, or (at your option) any later version.
-- 
-- This program is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU General Public License for more details.
-- 
-- You should have received a copy of the GNU General Public 
-- License along with this program.  If not, see
-- <http://www.gnu.org/licenses/gpl-3.0.html>.
-- #L%
---
-- insert test data

insert into martinlaw_court_case_t 
(matter_id, court_reference) 
values 
(1001, 'c1'),
(1002, 'c2'),
(1003, 'c2'),
-- lands case uses the similar details as the first case to make the unit test easier
(1004,'c1');

INSERT INTO `martinlaw_land_case_t`
(`land_reference`, `matter_id`)
VALUES
('LR JOHN 3/16', 1004);

insert into martinlaw_court_case_witness_t 
(court_case_witness_id,court_case_id, principal_name) 
values 
(1001, 1001, 'witness1'),
(1002, 1004, 'witness1');

