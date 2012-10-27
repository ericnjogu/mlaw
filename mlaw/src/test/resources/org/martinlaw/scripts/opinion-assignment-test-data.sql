insert into martinlaw_opinion_assignment_t
(matter_id, obj_id, ver_nbr)
values
(1001, 'can1', 1);


insert into martinlaw_opinion_assignee_t
(assignee_id, matter_id, principal_name, obj_id, ver_nbr)
values
(1001, 1001, 'pn', 'ca1', 1),
(1002, 1001, 'aw', 'ca2', 1),
(1003, 1001, 'clerk1', 'ca3', 1);
