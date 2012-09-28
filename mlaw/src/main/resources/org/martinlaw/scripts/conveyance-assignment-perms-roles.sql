
-- create permissions to create the documents
INSERT INTO `krim_perm_t` 
(`PERM_ID`,`OBJ_ID`,`VER_NBR`,`PERM_TMPL_ID`,`NMSPC_CD`,`NM`,`DESC_TXT`,`ACTV_IND`) 
VALUES 
('create_conv_assignment_maint','eWbfbFXZhonb2LoWaStjtPoqODfeDNobmHV8',2,'42','KR-NS','Create Conveyance Assignment Maintenance Document','Allows user to create a new Conveyance Type maintenance document','Y');


-- document type names for the permissions
INSERT INTO `krim_perm_attr_data_t` (`ATTR_DATA_ID`,`OBJ_ID`,`VER_NBR`,`PERM_ID`,`KIM_TYP_ID`,`KIM_ATTR_DEFN_ID`,`ATTR_VAL`) 
VALUES 
('conv_assignment_maint_doc','InfoxMzVgZkhLAHqZKAEnaog378ByieB4xHf',1,'create_conv_assignment_maint','56','13','ConveyanceAssignmentMaintenanceDocument');

  
-- assign permissions to martinlaw functional role
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','CxofEyLVxeSIyNFuVAe9xl6g8kbomtDYQHlO','create_conv_assignment_maint','org.mlaw.roles.functional','fnl_create_conv_assignment',1);
