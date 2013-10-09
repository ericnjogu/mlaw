---
-- #%L
-- mlaw
-- %%
-- Copyright (C) 2013 Eric Njogu (kunadawa@gmail.com)
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

insert into martinlaw_matter_type_t
(type_id)
values
-- matter type
-- court case, conveyance
(10001),
(10002),
(10003),
(10004),
(10005),
-- contract
(10006),
(10007),
(10008),
-- conveyance
(10009),
(10010),
(10011);

insert into martinlaw_matter_type_annex_detail
(matter_type_id, annex_type_id, ver_nbr, obj_id, sequence)
values
-- 'commercial' to 'demand letter'
(10003, 10018, default, uuid(), 3),
-- 'sale of urban land' to 'land board approval'
(10009, 10023, default, uuid(), 2);