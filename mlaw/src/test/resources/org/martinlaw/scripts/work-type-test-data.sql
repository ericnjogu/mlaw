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
insert into martinlaw_work_type_t
(work_type_id, name, description, ver_nbr, obj_id)
values
(10001, "document", 'default work type -  any document', 1, UUID()),
(10002, " meeting minutes", null, 1, UUID()),
(10003, "plaint", null, 1, UUID()),
(10004, "demand letter", 'the battle cry', 1, UUID()),
(10005, "interview", null, 1, UUID()),
(10006, "link(s) to internet resources", null, 1, UUID()),
(10007, "legal opinion", null, 1, UUID()),
(10008, "precedent", null, 1, UUID());

insert into martinlaw_work_type_scope_t
(work_type_scope_id, qualified_class_name, work_type_id, ver_nbr,  obj_id)
values
(10001, "org.martinlaw.bo.courtcase.CourtCase", 10003, 1, UUID()),
(10002, "org.martinlaw.bo.courtcase.CourtCase", 10004, 1, UUID()),
(10003, "org.martinlaw.bo.courtcase.CourtCase", 10008, 1, UUID());