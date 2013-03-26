---
-- #%L
-- mlaw
-- %%
-- Copyright (C) 2012 Eric Njogu (kunadawa@gmail.com)
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
martinlaw_conveyance_fee_t
(fee_id,matter_id,amount,date_received, ver_nbr, obj_id, client_principal_name)
values
(1001,1001,2500.58,'2012-10-22', default, 1, 'mawanja'),
(1002,1001,10000.00,'2012-10-23', default, 1, 'granyanja');

insert into
martinlaw_conveyance_fee_doc_t
(matter_id, DOC_HDR_ID, ver_nbr, obj_id, fee_id)
values
(1001, "1001", 1, 'cw1', 1001),
(1001, "1002", 1, 'cw2', 1002);