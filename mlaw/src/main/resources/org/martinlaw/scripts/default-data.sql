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

insert into martinlaw_status_t 
(status_id, status, ver_nbr,  obj_id) 
values 
(1001, 'pending', 1, 'rVFEm1NEx898d3LVZ5wpM3NoGj4fc5odHitf'), 
(1002, 'hearing', 1, 'urJMtBzP7nGFkGeCxW6yIUe3TBBO2qyUNpjC'), 
(1003, 'closed', 1, '19ArvTXtirtmHRbmE5mO13R1rGvwej1zO94H'), 
(1004, 'documents missing', 1, 'TrUJbOnoGZguzwZerTVa5Ec2rZQQlvra9jQv'),
(1005, 'adjourned', 1, 'pC91h1ZSYcAdhAXIzjotJIMoowo0ToqcJFGV');

insert into martinlaw_status_scope_t
(status_scope_id, qualified_class_name, status_id, ver_nbr,  obj_id)
values
(1001, "org.martinlaw.bo.courtcase.CourtCase", 1002, 1, "ssc1"),
(1002, "org.martinlaw.bo.courtcase.CourtCase", 1005, 1, "ssc2"),
(1003, "org.martinlaw.bo.conveyance.Conveyance", 1004, 1, "ssc3"),
(1004, "org.martinlaw.bo.courtcase.CourtCase", 1004, 1, "ssc4");
