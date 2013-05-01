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

-- create permissions to create the documents
INSERT INTO `krim_perm_t` 
(`PERM_ID`,`OBJ_ID`,`VER_NBR`,`PERM_TMPL_ID`,`NMSPC_CD`,`NM`,`DESC_TXT`,`ACTV_IND`) 
VALUES 
('create_conveyance_consideration_maint','4sL6FV4FzWCLiIIFSTkAayeYIfZ4oZxUAkgf',2,'42','KR-NS','Create Conveyance Consideration Maintenance Document','Allows user to create a new Conveyance Consideration maintenance document','Y');


-- document type names for the permissions
INSERT INTO `krim_perm_attr_data_t` (`ATTR_DATA_ID`,`OBJ_ID`,`VER_NBR`,`PERM_ID`,`KIM_TYP_ID`,`KIM_ATTR_DEFN_ID`,`ATTR_VAL`) 
VALUES 
('conveyance_consideration_maint_doc','yWvoAgof6BYQprbsQhCJARFGWS4XfNslWWmw',1,'create_conveyance_consideration_maint','56','13','ConveyanceConsiderationMaintenanceDocument');

  
-- assign permissions to martinlaw functional role
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','L9pW97km2lkzjoWjT0YiM31hikHIopTdcoaN','create_conveyance_consideration_maint','org.mlaw.roles.functional','fnl_create_conveyance_consideration',1);
