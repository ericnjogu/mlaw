---
-- #%L
-- mlaw
-- %%
-- Copyright (C) 2012 Eric Njogu (kunadawa@gmail.com)
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
insert into martinlaw_contract_assignment_t
(matter_id, obj_id, ver_nbr)
values
(1001, 'can1', 1);


insert into martinlaw_contract_assignee_t
(assignee_id, matter_id, principal_name, obj_id, ver_nbr)
values
(1001, 1001, 'pn', 'ca1', 1),
(1002, 1001, 'aw', 'ca2', 1),
(1003, 1001, 'clerk1', 'ca3', 1);
