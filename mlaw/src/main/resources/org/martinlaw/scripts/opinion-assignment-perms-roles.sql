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

-- create permissions to create the documents
INSERT INTO `krim_perm_t` 
(`PERM_ID`,`OBJ_ID`,`VER_NBR`,`PERM_TMPL_ID`,`NMSPC_CD`,`NM`,`DESC_TXT`,`ACTV_IND`) 
VALUES 
('create_opinion_assignment_maint','shHHwLdzlvFfASNkGt4gXObIQ9peeEfgOPir',2,'42','KR-NS','Create Opinion Assignment Maintenance Document','Allows user to create a new Opinion Type maintenance document','Y');


-- document type names for the permissions
INSERT INTO `krim_perm_attr_data_t` (`ATTR_DATA_ID`,`OBJ_ID`,`VER_NBR`,`PERM_ID`,`KIM_TYP_ID`,`KIM_ATTR_DEFN_ID`,`ATTR_VAL`) 
VALUES 
('opinion_assignment_maint_doc','4GXEhD8PYM1lld7AwqOfL9jeOAeEZBe1IxjH',1,'create_opinion_assignment_maint','56','13','OpinionAssignmentMaintenanceDocument');

  
-- assign permissions to martinlaw functional role
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','xYfIld7YnVGSUGh77tflanAvmkdpuNfj1Q9Y','create_opinion_assignment_maint','org.mlaw.roles.functional','fnl_create_opinion_assignment',1);
