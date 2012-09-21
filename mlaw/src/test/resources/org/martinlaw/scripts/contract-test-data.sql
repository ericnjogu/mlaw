insert into martinlaw_contract_consideration_t
(contract_consideration_id, currency, description, amount)
values
(1001, 'KES', 'Kshs', 41000);

insert into martinlaw_contract_duration_t
(contract_duration_id, start_date, end_date)
values
(1001, '2012-08-31  00:00:00', '2012-09-30  00:00:00');

insert into martinlaw_contract_t
(contract_id, local_reference, status_id, obj_id, ver_nbr, type_id, name, contract_consideration_id, contract_duration_id)
values
(1001, "en/cn/001", 1001, 'cn1', 1, 1001, 'buru ph2 h24', 1001, 1001);

insert into martinlaw_contract_t
(contract_id, local_reference, status_id, obj_id, ver_nbr, type_id, name, contract_consideration_id, contract_duration_id)
values
(1002, "en/cn/002", 1001, 'cn2', 1, 1001, 'nyayo est ph2 m241', 1001, 1001);