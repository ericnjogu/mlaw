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
insert into martinlaw_court_case_type_t
(court_case_type_id, name, description, ver_nbr, obj_id)
values
(10001, "civil", null, 1, uuid()),
(10002, "criminal", null, 1, uuid()),
(10003, "commercial", 'any biz mambo', 1, uuid()),
(10004, "election petition", null, 1, uuid()),
(10005, "appeal", null, 1, uuid());