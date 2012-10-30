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
-- test note
INSERT INTO `krns_nte_t`
(`NTE_ID`, `OBJ_ID`, `VER_NBR`, `RMT_OBJ_ID`, `AUTH_PRNCPL_ID`, `POST_TS`, NTE_TYP_CD)
VALUES
(1001, 1, default, 'conv1', 'clerk1', '2012-07-19  00:00:00', 'BO'),
(1002, 2, default, 'case1', 'clerk1', '2012-07-19  00:00:00', 'BO'),
(1003, 3, default, 'case1', 'clerk1', '2012-07-19  00:00:00', 'BO'),
(1004, 4, default, 'conv2', 'clerk1', '2012-07-19  00:00:00', 'BO');

-- test att
INSERT INTO `krns_att_t`
(`NTE_ID`, `OBJ_ID`, `VER_NBR`, `FILE_NM`)
VALUES
(1001, 1, default, 'filename.ext'),
(1002, 2, default, 'submission.pdf'),
(1003, 3, default, 'pleading.odt');