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
insert into
martinlaw_matter_work_doc_t
(matter_id, DOC_HDR_ID, ver_nbr, obj_id, work_type_id)
values
-- court case work
(1001, "1001", 1, uuid(), 10001),
(1001, "1002", 1, uuid(), 10001),
-- land case work
(1004, "1003", 1, uuid(), 10001),
(1004, "1004", 1, uuid(), 10001),
-- conveyance work
(1008, "1005", 1, uuid(), 10001),
(1008, "1006", 1, uuid(), 10001),
-- contract work
(1005, "1007", 1, uuid(), 10001),
(1005, "1008", 1, uuid(), 10001),
-- matter work
(1011, "1009", 1, uuid(), 10001),
(1011, "1010", 1, uuid(), 10001);