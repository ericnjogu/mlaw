insert into
martinlaw_convey_fee_t
(fee_id,matter_id,amount,date_received, ver_nbr, obj_id, client_principal_name)
values
(1001,1001,2500.58,'2012-10-22', default, 1, 'mawanja'),
(1002,1001,10000.00,'2012-10-23', default, 1, 'granyanja');

insert into
martinlaw_convey_fee_doc_t
(matter_id, DOC_HDR_ID, ver_nbr, obj_id, fee_id)
values
(1001, "1001", 1, 'cw1', 1001),
(1001, "1002", 1, 'cw2', 1002);