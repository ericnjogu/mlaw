-- insert test data

insert into
martinlaw_convey_type_t
(convey_type_id, name, description, ver_nbr, obj_id)
values
(1001, "Sale of Urban Land", null, default, 1),
(1002, "Sale of Motor Vehicle", null, default, 1);

insert into martinlaw_convey_t 
(conveyance_id, local_reference, status_id, name, convey_type_id, obj_id) 
values 
(1001, 'c1', 1001, "Sale of LR4589", 1001, 'conv1'),
(1002, 'c2', 1001, "Sale of kaq 784l", 1002, 'conv2');

insert into martinlaw_convey_client_t (convey_client_id, conveyance_id, principal_name) values (1001, 1001, 'client2');

insert into
martinlaw_convey_fee_t
(convey_fee_id,conveyance_id,amount,date_received,description, ver_nbr, obj_id)
values
(1001,1001,2500.58,'2011-06-12','received from karateka', default, 1);

insert into
martinlaw_convey_annex_type_t
(convey_annex_type_id, convey_type_id, name, description, ver_nbr, obj_id)
values
(1001, 1001, "land board approval", null, default, 1),
(1002, 1001, "city council approval", null, default, 1),
(1003, 1002, "sale agreement", null, default, 1);

insert into
martinlaw_convey_annex_t
(convey_annex_id, convey_annex_type_id, conveyance_id, ver_nbr, obj_id)
values
(1001, 1001, 1001, default, 1),
(1002, 1002, 1002, default, 1);

insert into
martinlaw_convey_att_t
(convey_att_id, convey_annex_id, ver_nbr, obj_id, note_timestamp)
values
(1001, 1001, default, 1, '2012-07-19 00:00:00'),
(1002, 1001, default, 1, '');

-- test note
INSERT INTO `krns_nte_t`
(`NTE_ID`, `OBJ_ID`, `VER_NBR`, `RMT_OBJ_ID`, `AUTH_PRNCPL_ID`, `POST_TS`, NTE_TYP_CD)
VALUES
(1001, 1, default, 'conv1', 'clerk1', '2012-07-19  00:00:00', 'BO'),
(1002, 2, default, 'case1', 'clerk1', '2012-07-19  00:00:00', 'BO'),
(1003, 3, default, 'case1', 'clerk1', '2012-07-19  00:00:00', 'BO'),
(1004, 4, default, 'conv2', 'clerk1', '2012-07-19  00:00:00', 'BO');

-- test att
INSERT INTO `krns_att_t`
(`NTE_ID`, `OBJ_ID`, `VER_NBR`, `FILE_NM`)
VALUES
(1001, 1, default, 'filename.ext'),
(1002, 2, default, 'submission.pdf'),
(1003, 3, default, 'pleading.odt');
