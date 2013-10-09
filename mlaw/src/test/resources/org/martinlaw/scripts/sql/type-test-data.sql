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
insert into martinlaw_type_t
(type_id, name, description, ver_nbr, obj_id)
values
-- ids 10001 to 11999 should be reserved for pre-configured types
-- when the user generated types get to id 10000, change the sequence table to jump to 12000
-- 10001 - 11000 - for use in sql e.g. testing values, organization
-- 11001 - 11999 - for use in sql and java constants

-- matter type (court case, conveyance)
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
(10011, "file", null, default, uuid()),

-- consideration type
(11002, "Legal fee", 'default consideration for tracking legal fees', 1, uuid()),
(11003, "Disbursement", 'default consideration for tracking disbursement', 1, uuid()),
(10014, "contract value", null, 1, uuid()),
(10015, "purchase price", 'the purchase price', 1, uuid()),

-- matter annex type
(11001, "document", 'default annex type -  any document', 1, UUID()),
(10016, " meeting minutes", null, 1, UUID()),
(10017, "plaint", null, 1, UUID()),
(10018, "demand letter", 'the battle cry', 1, UUID()),
(10019, "interview", null, 1, UUID()),
(10020, "link(s) to internet resources", null, 1, UUID()),
(10021, "legal opinion", null, 1, UUID()),
(10022, "precedent", null, 1, UUID()),
(10023, "land board approval", null, default, uuid()),
(10024, "city council approval", null, default, uuid()),
(10025, "sale agreement", null, default, uuid()),

-- transaction type
(10026, "receipt", null, 1,  UUID()),
(10027, "payment", null, 1,  UUID()),
(10028, "interest", 'interest on penalty', 1,  UUID()),

-- event type
(10029, "Hearing", null, 1, uuid()),
(10030, "Bring-up", null, 1,  uuid()),
(10031, "Mention", null,  1, uuid()),
(10032, "Lands Board Hearing", null, 1, uuid()),

-- status
(10033, 'pending', null, 1, uuid()), 
(10034, 'hearing', null, 1, uuid()), 
(10035, 'closed', null, 1, uuid()), 
(10036, 'documents missing', null, 1, uuid()),
(10037, 'adjourned', null, 1, uuid());

insert into martinlaw_type_scope_t
(scope_id, qualified_class_name, type_id, ver_nbr,  obj_id)
values
-- matter annex type
--court case, conveyance
(10001, "org.martinlaw.bo.courtcase.CourtCase", 10017, 1, UUID()),
(10002, "org.martinlaw.bo.courtcase.CourtCase", 10018, 1, UUID()),
(10003, "org.martinlaw.bo.courtcase.CourtCase", 10022, 1, UUID()),
(10004, "org.martinlaw.bo.conveyance.Conveyance", 10023, 1, uuid()),
(10005, "org.martinlaw.bo.conveyance.Conveyance", 10024, 1, uuid()),

-- consideration type
(10006, "org.martinlaw.bo.conveyance.Conveyance", 10014, 1, uuid()),
(10007, "org.martinlaw.bo.contract.Contract", 10014, 1, uuid()),
(10008, "org.martinlaw.bo.conveyance.Conveyance", 10015, 1, uuid()),

-- matter type
(10009, "org.martinlaw.bo.courtcase.CourtCase", 10001, 1, uuid()),
(10010, "org.martinlaw.bo.courtcase.CourtCase", 10002, 1, uuid()),
(10011, "org.martinlaw.bo.conveyance.Conveyance", 10003, 1, uuid()),
(10012, "org.martinlaw.bo.courtcase.CourtCase", 10003, 1, uuid()),
(10013, "org.martinlaw.bo.courtcase.CourtCase", 10004, 1, uuid()),
(10015, "org.martinlaw.bo.courtcase.CourtCase", 10005, 1, uuid()),
-- contract
(10016, "org.martinlaw.bo.contract.Contract", 10006, 1, uuid()),
(10017, "org.martinlaw.bo.contract.Contract", 10007, 1, uuid()),
(10018, "org.martinlaw.bo.contract.Contract", 10008, 1, uuid()),
-- conveyance
(10019, "org.martinlaw.bo.conveyance.Conveyance", 10009, 1, uuid()),
(10020, "org.martinlaw.bo.conveyance.Conveyance", 10010, 1, uuid()),

-- event type
(10021, "org.martinlaw.bo.courtcase.CourtCase", 10029, 1, uuid()),
(10022, "org.martinlaw.bo.courtcase.CourtCase", 10031, 1, uuid()),
(10023, "org.martinlaw.bo.conveyance.Conveyance", 10032, 1, uuid()),

-- status
(10024, "org.martinlaw.bo.courtcase.CourtCase", 10034, 1, uuid()),
(10025, "org.martinlaw.bo.courtcase.CourtCase", 10037, 1, uuid()),
(10026, "org.martinlaw.bo.conveyance.Conveyance", 10036, 1, uuid()),
(10027, "org.martinlaw.bo.courtcase.CourtCase", 10036, 1, uuid());
