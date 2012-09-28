
-- create permissions to create the documents
INSERT INTO `krim_perm_t` 
(`PERM_ID`,`OBJ_ID`,`VER_NBR`,`PERM_TMPL_ID`,`NMSPC_CD`,`NM`,`DESC_TXT`,`ACTV_IND`) 
VALUES 
('create_courtcase_assignment_maint','A3ITZ4edOJWZ1Lzo8IK1UDCvJqdBpv9Fzxvs',2,'42','KR-NS','Create CourtCase Assignment Maintenance Document','Allows user to create a new CourtCase Type maintenance document','Y');


-- document type names for the permissions
INSERT INTO `krim_perm_attr_data_t` (`ATTR_DATA_ID`,`OBJ_ID`,`VER_NBR`,`PERM_ID`,`KIM_TYP_ID`,`KIM_ATTR_DEFN_ID`,`ATTR_VAL`) 
VALUES 
('courtcase_assignment_maint_doc','ZsRtOdaEwO70s55gcFxP9e9Pv06QjinEopeN',1,'create_courtcase_assignment_maint','56','13','CourtCaseAssignmentMaintenanceDocument');

  
-- assign permissions to martinlaw functional role
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','ScPodW0okui6iDRoEohEy8lPStuHd52sf27G','create_courtcase_assignment_maint','org.mlaw.roles.functional','fnl_create_courtcase_assignment',1);
