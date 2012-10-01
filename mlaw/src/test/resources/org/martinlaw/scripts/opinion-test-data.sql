insert into martinlaw_opinion_t 
(matter_id,local_reference, summary, status_id, name, obj_id) 
values 
(1001,'op1', null,1002,"legal opinion regarding the sale of brown elephant", "op1");

insert into
martinlaw_opinion_fee_t
(fee_id,matter_id,amount,date_received,description, ver_nbr, obj_id)
values
(1001,1001,2500.58,'2012-09-29','received from znm', default, 1),
(1002,1001,10000.00,'2012-09-30','received from mmm', default, 1);

insert into martinlaw_opinion_client_t (client_id, matter_id, principal_name) 
values 
(1001, 1001, 'jnn'),
(1002, 1001, 'inn');