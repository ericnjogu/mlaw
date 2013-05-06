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
insert into martinlaw_event_type_t
(event_type_id, name, obj_id, ver_nbr)
values
(1001, "Hearing", 'BN6Jdeo0qk6T7m4b6DiiZ0BOfJnohJk0VKTg', 1),
(1002, "Bring-up", 'QlDtJvbJsjQsB9lgPBN3txJpPu4s4QfgNQ9H', 1),
(1003, "Mention", 'bbqiuz02kh8H3dBjtFaWiKoZ6VX5ERZ6jTqV', 1),
(1004, "Lands Board Hearing", 'et1', 1);

insert into martinlaw_event_type_scope_t
(event_type_scope_id, qualified_class_name, event_type_id, ver_nbr,  obj_id)
values
(1001, "org.martinlaw.bo.courtcase.CourtCase", 1001, 1, "ssc1"),
(1002, "org.martinlaw.bo.courtcase.CourtCase", 1003, 1, "ssc2"),
(1003, "org.martinlaw.bo.conveyance.Conveyance", 1004, 1, "ssc3");