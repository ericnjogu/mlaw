
-- create permissions to create the documents
INSERT INTO `krim_perm_t` 
(`PERM_ID`,`OBJ_ID`,`VER_NBR`,`PERM_TMPL_ID`,`NMSPC_CD`,`NM`,`DESC_TXT`,`ACTV_IND`) 
VALUES 
('create_opinion_maint','tUjsB3QH8InLnLWrLIBfjvfP447FiRmxKQU7',2,'42','KR-NS','Create Opinion Maintenance Document','Allows user to create a new Opinion maintenance document','Y');


-- document type names for the permissions
INSERT INTO `krim_perm_attr_data_t` (`ATTR_DATA_ID`,`OBJ_ID`,`VER_NBR`,`PERM_ID`,`KIM_TYP_ID`,`KIM_ATTR_DEFN_ID`,`ATTR_VAL`) 
VALUES 
('opinion_maint_doc','99A0mwjAnrX00FtYDpyzVzyOwKFCdggtPgmn',1,'create_opinion_maint','56','13','OpinionMaintenanceDocument');

  
-- assign permissions to martinlaw functional role
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','G8KAe8Ed2UUVBdfE0C2MN9q3JHMKyx7DMzn3','create_opinion_maint','org.mlaw.roles.functional','fnl_create_opinion',1);
