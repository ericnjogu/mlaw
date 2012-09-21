insert into martinlaw_contract_assignment_t
(assignment_id, contract_id, obj_id, ver_nbr)
values
(1001, 1001, 'can1', 1);


insert into martinlaw_contract_assignee_t
(assignee_id, assignment_id, principal_name, obj_id, ver_nbr)
values
(1001, 1001, 'lawyer1', 'ca1', 1),
(1002, 1001, 'clerk1', 'ca2', 1);
