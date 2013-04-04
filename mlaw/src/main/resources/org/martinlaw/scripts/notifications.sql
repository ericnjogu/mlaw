-- create channel
INSERT INTO 
`kren_chnl_t` 
(`CHNL_ID`, `NM`, `DESC_TXT`, `SUBSCRB_IND`, `VER_NBR`) 
VALUES 
(1001, 'mLaw Calendar', 'This channel sends out information on new or modified matter dates', 'Y', 1);

-- create producer
INSERT INTO `kren_prodcr_t` 
(`PRODCR_ID`, `NM`, `DESC_TXT`, `CNTCT_INFO`, `VER_NBR`) 
VALUES 
(1001, 'mLaw Calendar', 'This producer represents messages sent about matter dates', 'mlaw@localhost', 1);

-- allow producer to use channel
INSERT INTO `kren_chnl_prodcr_t` (`CHNL_ID`, `PRODCR_ID`) VALUES (1001, 1001);

-- subscribe clerk1 and lawyer1 into channel
INSERT INTO `kren_chnl_subscrp_t` 
(`CHNL_SUBSCRP_ID`, `CHNL_ID`, `PRNCPL_ID`, `OBJ_ID`, `VER_NBR`) 
VALUES 
(1001, 1001, 'clerk1', 'sub1', 1);

INSERT INTO `kren_chnl_subscrp_t` 
(`CHNL_SUBSCRP_ID`, `CHNL_ID`, `PRNCPL_ID`, `OBJ_ID`, `VER_NBR`) 
VALUES 
(1002, 1001, 'lawyer1', 'sub2', 1);

-- enable emails for notifications to the channel 

INSERT INTO `kren_recip_prefs_t` 
(`RECIP_PREFS_ID`,`RECIP_ID`,`PROP`,`VAL`,`VER_NBR`) 
VALUES 
(1000,'clerk1','Email.email_delivery_format','html',5);

INSERT INTO `kren_recip_prefs_t` 
(`RECIP_PREFS_ID`,`RECIP_ID`,`PROP`,`VAL`,`VER_NBR`) 
VALUES 
(1001,'clerk1','Email.email_address','mlaw@localhost',5);

INSERT INTO `kren_recip_prefs_t` 
(`RECIP_PREFS_ID`,`RECIP_ID`,`PROP`,`VAL`,`VER_NBR`) 
VALUES 
(1003,'lawyer1','Email.email_delivery_format','html',5);

INSERT INTO `kren_recip_prefs_t` 
(`RECIP_PREFS_ID`,`RECIP_ID`,`PROP`,`VAL`,`VER_NBR`) 
VALUES 
(1004,'lawyer1','Email.email_address','mlaw@localhost',5);