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
('create_conv_maint','31e244bb-1739-4871-9e61-36575ebd5bb8',2,'42','KR-NS','Create Conveyance Maintenance Document','Allows user to create a new Conveyance maintainence document','Y');

INSERT INTO `krim_perm_t` (`PERM_ID`,`OBJ_ID`,`VER_NBR`,`PERM_TMPL_ID`,`NMSPC_CD`,`NM`,`DESC_TXT`,`ACTV_IND`) 
VALUES 
('create_case_maint','2da9074c-d471-4d62-8f7b-3ff0e5e67ac1',2,'42','KR-NS','Create Case Maintenance Document','Allows user to create a new Case maintainance document','Y');

INSERT INTO `krim_perm_t` (`PERM_ID`,`OBJ_ID`,`VER_NBR`,`PERM_TMPL_ID`,`NMSPC_CD`,`NM`,`DESC_TXT`,`ACTV_IND`) 
VALUES 
('create_status_maint','2da9075c-d471-4d62-8f7b-3ff0e5e67ac2',2,'42','KR-NS','Create Status Maintenance Document','Allows user to create a new status maintainance document','Y');

INSERT INTO `krim_perm_t` (`PERM_ID`,`OBJ_ID`,`VER_NBR`,`PERM_TMPL_ID`,`NMSPC_CD`,`NM`,`DESC_TXT`,`ACTV_IND`) 
VALUES 
('create_convtype_maint','2da9076c-d471-4d62-8f7b-3ff0e5e67ac3',2,'42','KR-NS','Create Conveyance Type Maintenance Document','Allows user to create a new conveyance type maintainance document','Y');

-- document type names for the permissions
INSERT INTO `krim_perm_attr_data_t` (`ATTR_DATA_ID`,`OBJ_ID`,`VER_NBR`,`PERM_ID`,`KIM_TYP_ID`,`KIM_ATTR_DEFN_ID`,`ATTR_VAL`) 
VALUES 
('conv_maint_doc','97ed01e6-a94f-4ac5-a84a-3ae9b3b736b6',1,'create_conv_maint','56','13','ConveyanceMaintenanceDocument');

INSERT INTO `krim_perm_attr_data_t` (`ATTR_DATA_ID`,`OBJ_ID`,`VER_NBR`,`PERM_ID`,`KIM_TYP_ID`,`KIM_ATTR_DEFN_ID`,`ATTR_VAL`) 
VALUES 
('case_maint_doc','a1ae505d-67bb-4159-8b07-001e2afc7c4f',1,'create_case_maint','56','13','CaseMaintenanceDocument');

INSERT INTO `krim_perm_attr_data_t` (`ATTR_DATA_ID`,`OBJ_ID`,`VER_NBR`,`PERM_ID`,`KIM_TYP_ID`,`KIM_ATTR_DEFN_ID`,`ATTR_VAL`) 
VALUES 
('status_maint_doc','a1ae507d-67bb-4159-8b07-001e2afc7c5f',1,'create_status_maint','56','13','StatusMaintenanceDocument');

INSERT INTO `krim_perm_attr_data_t` (`ATTR_DATA_ID`,`OBJ_ID`,`VER_NBR`,`PERM_ID`,`KIM_TYP_ID`,`KIM_ATTR_DEFN_ID`,`ATTR_VAL`) 
VALUES 
('convtype_maint_doc','a1ae508d-67bb-4159-8b07-001e2afc7c6f',1,'create_convtype_maint','56','13','ConveyanceTypeMaintenanceDocument');


-- create the martinlaw functional role
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR, DESC_TXT)
  VALUES ('Y','18','KR-NS','ea9ed95a-7e6b-102c-97b6-ed816fdaf560','org.mlaw.roles.functional','martinlaw functional users',1, 'Able to create and edit all martinlaw docs');
  
-- assign permissions to martinlaw functional role
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','fa13d546-7e6e-102c-97b6-ed906fdaf540','create_conv_maint','org.mlaw.roles.functional','fnl_create_conv',1);

INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','fa02d546-7e6e-102c-97b6-ed912fdaf540','create_case_maint','org.mlaw.roles.functional','fnl_create_case',1);
  
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','fa02d547-7e6e-102c-97b6-ed912fdaf541','create_status_maint','org.mlaw.roles.functional','fnl_create_status',1);
  
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','fa02d548-7e6e-102c-97b6-ed912fdaf542','create_convtype_maint','org.mlaw.roles.functional','fnl_create_convtype',1);

-- make clerks group a member of martinlaw functional role
  INSERT INTO KRIM_ROLE_MBR_T(ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD)
VALUES('fnl_client', 1, '62311430-7dfb-102c-97b6-ed716fdaf540', 'org.mlaw.roles.functional', 'org.martinlaw.clerk', 'G');

-- make lawyers group a member of martinlaw_ops roles
  INSERT INTO KRIM_ROLE_MBR_T(ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD)
VALUES('fnl_lawyer', 1, '62311431-7dfb-102c-97b6-ed716fdaf542', 'org.mlaw.roles.functional', 'org.martinlaw.lawyer', 'G');