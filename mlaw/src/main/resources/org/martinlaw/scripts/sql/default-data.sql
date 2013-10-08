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
(type_id, name, description, ver_nbr, obj_id) 
values 
(1001, 'pending', null, 1, uuid()), 
(1002, 'hearing', null, 1, uuid()), 
(1003, 'closed', null, 1, uuid()), 
(1004, 'documents missing', null, 1, uuid()),
(1005, 'adjourned', null, 1, uuid());

insert into martinlaw_status_scope_t
(scope_id, qualified_class_name, type_id, ver_nbr,  obj_id)
values
(1001, "org.martinlaw.bo.courtcase.CourtCase", 1002, 1, "ssc1"),
(1002, "org.martinlaw.bo.courtcase.CourtCase", 1005, 1, "ssc2"),
(1003, "org.martinlaw.bo.conveyance.Conveyance", 1004, 1, "ssc3"),
(1004, "org.martinlaw.bo.courtcase.CourtCase", 1004, 1, "ssc4");

INSERT INTO 
`krim_afltn_typ_t` 
(`AFLTN_TYP_CD`,`OBJ_ID`,`VER_NBR`,`NM`,`EMP_AFLTN_TYP_IND`,`ACTV_IND`,`DISPLAY_SORT_CD`,`LAST_UPDT_DT`) 
VALUES 
('CLIENT',uuid(),1,'Client','N','Y','a','2008-11-13 14:06:30'),
('WITNESS',uuid(),1,'Witness','Y','Y','b','2008-11-13 14:06:30');
