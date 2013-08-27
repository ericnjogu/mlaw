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

insert into
martinlaw_court_case_transaction_doc_t
(matter_id, DOC_HDR_ID, ver_nbr, obj_id, amount,transaction_date, client_principal_name, consideration_id, transaction_type_id)
values
(1001, "1001", 1, 'ct1', 2501,'2012-10-22','mawanja', 1001, 1001),
(1001, "1002", 1, 'ct2', 10000.00,'2012-10-23', 'granyanja', 1001, 1002),
(1004, "1003", 1, 'lct1', 2501,'2012-10-22','mawanja', 1002, 1001),
(1004, "1004", 1, 'lct2', 10000.00,'2012-10-23', 'granyanja', 1002, 1002);