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
 insert into  martinlaw_matter_event_t 
 (id, start_date, date_comment, matter_id, ver_nbr, obj_id, type_id, date_created, location, active) 
 values 
 -- court case
 (1001, '2011-06-01','first hearing date', 1001, default, uuid(), 10029, now(), "nakuru", 'Y'),
 -- land case
 (1002, '2011-06-01','first hearing date', 1004, default, uuid(), 10029, now(), "nakuru", 'Y'),
 -- matter
 (1003, '2011-06-01','first hearing date', 1011, default, uuid(), 10029, now(), "nakuru", 'Y');