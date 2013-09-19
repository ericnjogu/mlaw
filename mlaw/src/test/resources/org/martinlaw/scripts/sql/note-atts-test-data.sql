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
-- test note
INSERT INTO `krns_nte_t`
(`NTE_ID`, `OBJ_ID`, `VER_NBR`, `RMT_OBJ_ID`, `AUTH_PRNCPL_ID`, `POST_TS`, NTE_TYP_CD)
VALUES
-- conveyance (with attachment)
(1001, uuid(), default, 'conv1', 'clerk1', '2012-07-19  00:00:00', 'BO'),
-- court case
(1002, uuid(), default, 'case1', 'clerk1', '2012-07-19  00:00:00', 'BO'),
(1003, uuid(), default, 'case1', 'clerk1', '2012-07-19  00:00:00', 'BO'),
-- conveyance (without attachment)
(1004, uuid(), default, 'conv2', 'clerk1', '2012-07-19  00:00:00', 'BO'),
-- land case
(1005, uuid(), default, 'landcase1', 'clerk1', '2012-07-19  00:00:00', 'BO'),
(1006, uuid(), default, 'landcase1', 'clerk1', '2012-07-19  00:00:00', 'BO'),
-- matter
(1007, uuid(), default, 'matter1', 'clerk1', '2012-07-19  00:00:00', 'BO'),
(1008, uuid(), default, 'matter1', 'clerk1', '2012-07-19  00:00:00', 'BO');;

-- test att
INSERT INTO `krns_att_t`
(`NTE_ID`, `OBJ_ID`, `VER_NBR`, `FILE_NM`)
VALUES
-- conveyance
(1001, uuid(), default, 'filename.ext'),
-- court case
(1002, uuid(), default, 'submission.pdf'),
(1003, uuid(), default, 'pleading.odt'),
-- land case
(1005, uuid(), default, 'submission.pdf'),
(1006, uuid(), default, 'pleading.odt'),
-- matter
(1007, uuid(), default, 'submission.pdf'),
(1008, uuid(), default, 'pleading.odt');