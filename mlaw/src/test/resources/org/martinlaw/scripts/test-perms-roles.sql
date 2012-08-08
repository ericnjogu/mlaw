-- update the record introduced by /config/data/KRIM_TYP_T.sql - which has no service id
update krim_typ_t set SRVC_NM = 'rolePermissionTypeService' where KIM_TYP_ID = '18';