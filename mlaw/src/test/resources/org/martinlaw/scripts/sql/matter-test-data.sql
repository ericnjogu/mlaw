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

insert into martinlaw_matter_t 
(matter_id,local_reference, status_id, name, obj_id, client_principal_name, class_name, tags, type_id) 
values 
(1001,'l1',1002,"Barca vs Man U (2011)", "case1", 'client1', 'org.martinlaw.bo.courtcase.CourtCase', "testing, sql, sample", 10001),
(1002,'L2',1002,"Good vs Evil", "case2", 'clerk1', 'org.martinlaw.bo.courtcase.CourtCase', null, 10002),
(1003,'L3',1002,"Love vs Fear", "case3", 'witness1', 'org.martinlaw.bo.courtcase.CourtCase', "media", 10003),
-- lands case uses the similar details as the first case to make the unit test easier
(1004,'l1',1002,"Barca vs Man U (2011)", "landcase1", 'client1', 'org.martinlaw.bo.courtcase.LandCase', "testing, sql, sample", 10001),
-- contract
(1005, "en/cn/001", 1001, 'buru ph2 h24', 'cn1', 'client1', 'org.martinlaw.bo.contract.Contract', 'urgent', 10006),
(1006, "EN/CN/002", 1001,  'nyayo est ph2 m241', 'cn2', 'clerk1', 'org.martinlaw.bo.contract.Contract', 'urgent', 10007),
(1007, "EN/CN/003", 1001,  'supply of oxygen', 'cn3', 'witness1', 'org.martinlaw.bo.contract.Contract', null, 10008),
-- conveyance
(1008, 'c1', 1001, "Sale of LR4589",  'conv1', 'client1', 'org.martinlaw.bo.conveyance.Conveyance', 'VIP', 10009),
(1009, 'C2', 1001, "Sale of kaq 784l", 'conv2', 'clerk1', 'org.martinlaw.bo.conveyance.Conveyance', null, 10010),
(1010, 'C3', 1001, "Transfer of plot 2", 'conv3', 'witness1', 'org.martinlaw.bo.conveyance.Conveyance', null, 10009),
-- matter - details the same as case to make testing easier
(1011,'l1',1002,"Barca vs Man U (2011)", "matter1", 'client1', 'org.martinlaw.bo.Matter',"testing, sql, sample", 10005);

insert into martinlaw_matter_consideration_t
(consideration_id, currency, description, amount, type_id, matter_id)
values
-- court case
(1001, 'TZS', 'to be paid in 2 installments', 41000, 10001, 1001),
-- land case
(1002, 'TZS', 'to be paid in 2 installments', 41000, 10001, 1004),
-- contract
(1003, 'TZS', 'to be paid in 2 installments', 41000, 10001, 1005),
-- conveyance
(1004, 'TZS', 'to be paid in 2 installments', 41000, 10001, 1008),
-- matter
(1005, 'TZS', 'to be paid in 2 installments', 41000, 10001, 1011);

insert into martinlaw_matter_client_t 
(client_id, matter_id, principal_name) 
values 
-- court case
(1001, 1001, 'client1'),
(1002, 1001, 'client2'),
-- land case
(1003, 1004, 'client1'),
(1004, 1004, 'client2'),
-- conveyance
(1005, 1008, 'client1'),
(1006, 1008, 'client2'),
-- matter
(1007, 1011, 'client1'),
(1008, 1011, 'client2');


