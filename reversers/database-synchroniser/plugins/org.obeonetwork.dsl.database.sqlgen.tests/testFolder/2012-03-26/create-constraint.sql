-- ==============================================================
--  Constraint : AD_ACQ_CK1_1                                    
-- ==============================================================
ALTER TABLE AD_ACQ ADD CONSTRAINT AD_ACQ_CK1_1 
	CHECK(AD_XTOPSUP in ('0','1','2','3','4','9','M'));

