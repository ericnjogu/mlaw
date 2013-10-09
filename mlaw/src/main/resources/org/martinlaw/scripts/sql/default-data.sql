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

INSERT INTO 
`krim_afltn_typ_t` 
(`AFLTN_TYP_CD`,`OBJ_ID`,`VER_NBR`,`NM`,`EMP_AFLTN_TYP_IND`,`ACTV_IND`,`DISPLAY_SORT_CD`,`LAST_UPDT_DT`) 
VALUES 
('CLIENT',uuid(),1,'Client','N','Y','a','2008-11-13 14:06:30'),
('WITNESS',uuid(),1,'Witness','Y','Y','b','2008-11-13 14:06:30');
