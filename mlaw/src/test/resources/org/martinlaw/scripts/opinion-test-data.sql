insert into martinlaw_opinion_t 
(matter_id,local_reference, summary, status_id, name, obj_id) 
values 
(1001,'op1', null,1002,"legal opinion regarding the sale of brown elephant", "op1"),
(1002,'op2', null,1002,"legal opinion regarding the status quo", "op2");

insert into martinlaw_opinion_client_t (client_id, matter_id, principal_name) 
values 
(1001, 1001, 'client1'),
(1003, 1001, 'janyanjo');