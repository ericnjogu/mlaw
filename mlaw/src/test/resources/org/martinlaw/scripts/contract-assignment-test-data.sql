insert into martinlaw_contract_assignment_t
(matter_id, obj_id, ver_nbr)
values
(1001, 'can1', 1);


insert into martinlaw_contract_assignee_t
(assignee_id, matter_id, principal_name, obj_id, ver_nbr)
values
(1001, 1001, 'pn', 'ca1', 1),
(1002, 1001, 'aw', 'ca2', 1);
