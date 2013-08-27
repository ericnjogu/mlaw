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
(matter_id,local_reference, court_reference, status_id, name, obj_id, court_case_type_id, client_principal_name, class_name) 
values 
(1001,'l1', 'c1',1002,"Barca vs Man U (2011)", "case1", 10001, 'client1', 'org.martinlaw.bo.courtcase.CourtCase'),
(1002,'L2', 'c2',1002,"Good vs Evil", "case2", 10002, 'clerk1', 'org.martinlaw.bo.courtcase.CourtCase'),
(1003,'L3', 'c2',1002,"Love vs Fear", "case3", 10003, 'witness1', 'org.martinlaw.bo.courtcase.CourtCase'),
-- lands case uses the similar details as the first case to make the unit test easier
(1004,'l1', 'c1',1002,"Barca vs Man U (2011)", "landcase1", 10001, 'client1', 'org.martinlaw.bo.courtcase.LandCase');

INSERT INTO `martinlaw_land_case_t`
(`land_reference`, `matter_id`)
VALUES
('LR JOHN 3/16', 1004);

insert into martinlaw_court_case_consideration_t
(consideration_id, currency, description, amount, consideration_type_id, matter_id)
values
(1001, 'TZS', 'to be paid in 2 installments', 41000, 10001, 1001),
(1002, 'TZS', 'to be paid in 2 installments', 41000, 10001, 1004);

insert into martinlaw_court_case_client_t 
(client_id, matter_id, principal_name) 
values 
(1001, 1001, 'client1'),
(1002, 1001, 'client2'),
(1003, 1004, 'client1'),
(1004, 1004, 'client2');

insert into martinlaw_court_case_witness_t 
(court_case_witness_id,court_case_id, principal_name) 
values 
(1001, 1001, 'witness1'),
(1002, 1004, 'witness1');

