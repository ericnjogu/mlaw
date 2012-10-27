-- insert test data

insert into
martinlaw_convey_type_t
(convey_type_id, name, description, ver_nbr, obj_id)
values
(1001, "Sale of Urban Land", null, default, 1),
(1002, "Sale of Motor Vehicle", null, default, 1);

insert into martinlaw_convey_t 
(matter_id, local_reference, status_id, name, convey_type_id, obj_id) 
values 
(1001, 'c1', 1001, "Sale of LR4589", 1001, 'conv1'),
(1002, 'c2', 1001, "Sale of kaq 784l", 1002, 'conv2');

insert into martinlaw_convey_client_t 
(client_id, matter_id, principal_name) 
values 
(1001, 1001, 'client1'),
(1002, 1001, 'client2');

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

