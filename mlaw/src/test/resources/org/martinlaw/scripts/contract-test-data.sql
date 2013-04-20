---
-- #%L
-- mlaw
-- %%
-- Copyright (C) 2012, 2013 Eric Njogu (kunadawa@gmail.com)
-- %%
-- This program is free software: you can redistribute it and/or modify
-- it under the terms of the GNU General Public License as
-- published by the Free Software Foundation, either version 3 of the 
-- License, or (at your option) any later version.
-- 
-- This program is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU General Public License for more details.
-- 
-- You should have received a copy of the GNU General Public 
-- License along with this program.  If not, see
-- <http://www.gnu.org/licenses/gpl-3.0.html>.
-- #L%
---

insert into martinlaw_contract_duration_t
(contract_duration_id, start_date, end_date)
values
(1001, '2012-08-31  00:00:00', '2012-09-30  00:00:00');

insert into martinlaw_contract_t
(matter_id, local_reference, status_id, obj_id, ver_nbr, contract_type_id, name, contract_duration_id)
values
(1001, "en/cn/001", 1001, 'cn1', 1, 1001, 'buru ph2 h24', 1001),
(1002, "en/cn/002", 1001, 'cn2', 1, 1001, 'nyayo est ph2 m241', 1001),
(1003, "en/cn/003", 1001, 'cn3', 1, 1001, 'supply of oxygen', 1001);

insert into martinlaw_contract_consideration_t
(consideration_id, currency, description, amount, consideration_type_id, matter_id)
values
(1001, 'TZS', 'to be paid in 2 installments', 41000, 1001, 1001);

insert into martinlaw_contract_client_t 
(client_id, matter_id, principal_name) 
values 
(1001, 1001, 'client1'),
(1002, 1001, 'client2');