---
-- #%L
-- mlaw
-- %%
-- Copyright (C) 2013 Eric Njogu (kunadawa@gmail.com)
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
insert into martinlaw_consideration_type_t
(consideration_type_id, name, description, ver_nbr, obj_id)
values
(10001, "Legal fee", 'default consideration for tracking legal fees', 1, '8tlpKTKi1JDbj0w87XNE2VNB0SGIpDDzW5RC'),
(10002, "Disbursement", 'default consideration for tracking disbursement', 1, '7Gy9TH3KHe1nlm0NSra6oV0gVv7A306CiCDt'),
(10003, "contract value", null, 1, 'XBhoJBmNyBn9BOpJtwr5cW3X5rZlNOK0Xxi7'),
(10004, "purchase price", 'the purchase price', 1, 'N9kLZhkN2OJaJBygpE7Y23irST6UrNTSeM2E');

insert into martinlaw_consideration_type_scope_t
(consideration_type_scope_id, qualified_class_name, consideration_type_id, ver_nbr,  obj_id)
values
(1001, "org.martinlaw.bo.conveyance.Conveyance", 10003, 1, "ssc1"),
(1002, "org.martinlaw.bo.contract.Contract", 10002, 1, "ssc2"),
(1003, "org.martinlaw.bo.conveyance.Conveyance", 10004, 1, "ssc3");