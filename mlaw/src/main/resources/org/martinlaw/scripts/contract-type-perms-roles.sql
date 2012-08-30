
-- create permissions to create the documents
INSERT INTO `krim_perm_t` 
(`PERM_ID`,`OBJ_ID`,`VER_NBR`,`PERM_TMPL_ID`,`NMSPC_CD`,`NM`,`DESC_TXT`,`ACTV_IND`) 
VALUES 
('create_contract_type_maint','oTpouveg89B9LK8u2n0HoeMylOZijFhaPOVk',2,'42','KR-NS','Create Contract Type Maintenance Document','Allows user to create a new Contract Type maintenance document','Y');


-- document type names for the permissions
INSERT INTO `krim_perm_attr_data_t` (`ATTR_DATA_ID`,`OBJ_ID`,`VER_NBR`,`PERM_ID`,`KIM_TYP_ID`,`KIM_ATTR_DEFN_ID`,`ATTR_VAL`) 
VALUES 
('contract_type_maint_doc','wUn4EjVClS13aFZLD1H0OGb0Xz82DiauFFKe',1,'create_contract_type_maint','56','13','ContractTypeMaintenanceDocument');

  
-- assign permissions to martinlaw functional role
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','EpESttlpYnT63zAegtgdjJ2I9DnfnUaAWDVO','create_contract_type_maint','org.mlaw.roles.functional','fnl_create_contract_type',1);
