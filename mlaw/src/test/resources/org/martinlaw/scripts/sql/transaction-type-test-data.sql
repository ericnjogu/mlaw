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
insert into martinlaw_transaction_type_t
(transaction_type_id, name, description, ver_nbr, obj_id, effect_on_consideration)
values
(1001, "receipt", null, 1, 'CsBD2mSy91XN8mg6QicnAhsCBIRliP0cKDq9', 'DECREASE'),
(1002, "payment", null, 1, 'vXBJCFAXNHIxhzGSYwYg2e51UGpy5pmyVIB2','DECREASE'),
(1003, "interest", 'interest on penalty', 1, '318cwPS4aVhLlV4H6URW7hHlmbpLxidYbUI9','INCREASE');