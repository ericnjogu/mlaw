-- insert test data
insert into martinlaw_court_case_t 
(matter_id,local_reference, court_reference, status_id, name, obj_id) 
values 
(1001,'l1', 'c1',1002,"Barca vs Man U (2011)", "case1");

insert into martinlaw_court_case_client_t 
(client_id, matter_id, principal_name) 
values 
(1001, 1001, 'client1'),
(1002, 1001, 'client2');

insert into martinlaw_court_case_witness_t (court_case_witness_id,court_case_id, principal_name) values (1001, 1001, 'witness1');

