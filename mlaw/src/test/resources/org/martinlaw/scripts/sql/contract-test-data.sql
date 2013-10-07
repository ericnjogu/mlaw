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
(matter_id, contract_duration_id)
values
(1005, 1001),
(1006, 1001),
(1007, 1001);

insert into martinlaw_contract_party_t
(contract_party_id, contract_id, principal_name, ver_nbr, obj_id)
values
(1001, 1005, 'en', 1, 'en1');

insert into martinlaw_contract_signatory_t
(contract_signatory_id, contract_id, principal_name, ver_nbr, obj_id)
values
(1001, 1005,'en', 1, 'en1');