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
insert into martinlaw_matter_type_t
(type_id, name, description, ver_nbr, obj_id)
values
(10001, "civil", null, 1, uuid()),
(10002, "criminal", null, 1, uuid()),
(10003, "commercial", 'any biz mambo', 1, uuid()),
(10004, "election petition", null, 1, uuid()),
(10005, "appeal", null, 1, uuid()),
-- contract
(10006, "rent agreement", null, 1, uuid()),
(10007, "life assurance", 'maisha', 1, uuid()),
(10008, "car insurance", null, 1, uuid()),
-- conveyance
(10009, "Sale of Urban Land", null, default, uuid()),
(10010, "Sale of Motor Vehicle", null, default, uuid()),
(10011, "file", null, default, uuid());

insert into martinlaw_matter_type_scope_t
(scope_id, qualified_class_name, type_id, ver_nbr,  obj_id)
values
(1001, "org.martinlaw.bo.courtcase.CourtCase", 10001, 1, uuid()),
(1002, "org.martinlaw.bo.courtcase.CourtCase", 10002, 1, uuid()),
(1003, "org.martinlaw.bo.conveyance.Conveyance", 10003, 1, uuid()),
(1004, "org.martinlaw.bo.courtcase.CourtCase", 10003, 1, uuid()),
(1005, "org.martinlaw.bo.courtcase.CourtCase", 10004, 1, uuid()),
(1006, "org.martinlaw.bo.courtcase.CourtCase", 10005, 1, uuid()),
-- contract
(1007, "org.martinlaw.bo.contract.Contract", 10006, 1, uuid()),
(1008, "org.martinlaw.bo.contract.Contract", 10007, 1, uuid()),
(1009, "org.martinlaw.bo.contract.Contract", 10008, 1, uuid()),
-- conveyance
(1010, "org.martinlaw.bo.conveyance.Conveyance", 10009, 1, uuid()),
(1011, "org.martinlaw.bo.conveyance.Conveyance", 10010, 1, uuid());

insert into
martinlaw_matter_annex_type_t
(type_id, name, description, ver_nbr, obj_id, requires_approval)
values
(10001, "document", 'default annex type -  any document', 1, UUID(), 'Y'),
(10002, " meeting minutes", null, 1, UUID(), 'Y'),
(10003, "plaint", null, 1, UUID(), 'Y'),
(10004, "demand letter", 'the battle cry', 1, UUID(), 'Y'),
(10005, "interview", null, 1, UUID(), 'N'),
(10006, "link(s) to internet resources", null, 1, UUID(), 'N'),
(10007, "legal opinion", null, 1, UUID(), 'Y'),
(10008, "precedent", null, 1, UUID(), 'N'),
(10009, "land board approval", null, default, uuid(), 'N'),
(10010, "city council approval", null, default, uuid(), 'N'),
(10011, "sale agreement", null, default, uuid(), 'Y');

insert into martinlaw_annex_type_scope_t
(scope_id, qualified_class_name, type_id, ver_nbr,  obj_id)
values
(10001, "org.martinlaw.bo.courtcase.CourtCase", 10003, 1, UUID()),
(10002, "org.martinlaw.bo.courtcase.CourtCase", 10004, 1, UUID()),
(10003, "org.martinlaw.bo.courtcase.CourtCase", 10008, 1, UUID()),

(10004, "org.martinlaw.bo.conveyance.Conveyance", 10009, 1, uuid()),
(10005, "org.martinlaw.bo.conveyance.Conveyance", 10010, 1, uuid());

insert into martinlaw_matter_type_annex_detail
(matter_type_id, annex_type_id, ver_nbr, obj_id, sequence)
values
-- 'commercial' to 'demand letter'
(10003, 10004, default, uuid(), 3),
-- 'sale of urban land' to 'land board approval'
(10009, 10009, default, uuid(), 2);