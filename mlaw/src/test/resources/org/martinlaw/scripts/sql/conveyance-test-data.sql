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

insert into
martinlaw_conveyance_type_t
(convey_type_id, name, description, ver_nbr, obj_id)
values
(1001, "Sale of Urban Land", null, default, 'ct1'),
(1002, "Sale of Motor Vehicle", null, default, 'ct2');

insert into martinlaw_conveyance_t 
(matter_id, convey_type_id) 
values 
(1008, 1001),
(1009, 1002),
(1010, 1001);

insert into
martinlaw_conveyance_annex_type_t
(convey_annex_type_id, convey_type_id, name, description, ver_nbr, obj_id)
values
(1001, 1001, "land board approval", null, default, 1),
(1002, 1001, "city council approval", null, default, 1),
(1003, 1002, "sale agreement", null, default, 1);

