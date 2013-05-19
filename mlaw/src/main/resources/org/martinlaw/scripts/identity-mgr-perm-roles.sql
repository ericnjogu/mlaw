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

-- create the martinlaw idmgr (Identity manager) role
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR, DESC_TXT)
  VALUES ('Y','18','KR-NS','RMhzBNzn73tkCdTt19Xw7WVZjYlxzhP2rCxN','org.mlaw.roles.idmgr','martinlaw identity managers',1, 
  'Users who can modify entity records in Kuali Identity Management');
  
-- assign permissions to martinlaw idmgr role
INSERT INTO KRIM_ROLE_PERM_T 
(ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES 
('Y','VTIBHnJEnL0EZy1pzyFBsez3Ba4l9PKPVzFj','307','org.mlaw.roles.idmgr','idmgr_307',1);
  
-- create custom permission to populate group
INSERT INTO 
`krim_perm_t` 
(`PERM_ID`,`OBJ_ID`,`VER_NBR`,`PERM_TMPL_ID`,`NMSPC_CD`,`NM`,`DESC_TXT`,`ACTV_IND`) 
VALUES 
('pop_grp_mlaw','nXv2VvmRwU2iims2gFF7x1uX38A4mUIV5qzk',1,'38','KR-SYS','Populate Group MARTINLAW Namespace',
'Authorizes users to modify the information on the Assignees Tab of the Group Document and the Group section of the Membership Tab on the Person Document for groups with the MARTINLAW namespace.','Y');

-- create permission attribute
INSERT INTO 
`krim_perm_attr_data_t` 
(`ATTR_DATA_ID`,`OBJ_ID`,`VER_NBR`,`PERM_ID`,`KIM_TYP_ID`,`KIM_ATTR_DEFN_ID`,`ATTR_VAL`) 
VALUES 
('pop_grp_mlaw_attr','PbxIj0ZTHtQnbWPSRZXzvKSVJziICRCsZj2u',1,'pop_grp_mlaw','21','4','MARTINLAW');
 
-- associate custom permission with role
 INSERT INTO 
 KRIM_ROLE_PERM_T 
 (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
 VALUES 
 ('Y','ZQ9hVVkQ9uEjseU7gqKjdHMrr8rqX2yEEIqh','pop_grp_mlaw','org.mlaw.roles.idmgr','idmgr_pop_grp_mlaw',1);

-- make idmgr group a member of martinlaw idmgr role
 INSERT INTO KRIM_ROLE_MBR_T
 (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD)
VALUES
('idmgr_grp', 1, '6AWpQdqMmk8sGCMyeqZN1x3FhcD8Y3Dk319M', 'org.mlaw.roles.idmgr', 'org.martinlaw.group.idmgr', 'G');