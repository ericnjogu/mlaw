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