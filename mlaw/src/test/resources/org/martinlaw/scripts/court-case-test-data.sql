-- insert test data
insert into martinlaw_court_case_t 
(court_case_id,local_reference, court_reference, status_id, name, obj_id) 
values 
(1001,'l1', 'c1',1002,"Barca vs Man U (2011)", "case1");

insert into martinlaw_court_case_client_t (client_id, matter_id, principal_name) values (1001, 1001, 'client1');

insert into martinlaw_court_case_witness_t (court_case_witness_id,court_case_id, principal_name) values (1001, 1001, 'witness1');


insert into
martinlaw_court_case_fee_t
(fee_id,matter_id,amount,date_received,description, ver_nbr, obj_id)
values
(1001,1001,2500.58,'2011-06-12','received from karateka', default, 1),
(1002,1001,10000.00,'2010-08-10','received from artist', default, 1);
