CREATE OR REPLACE TRIGGER lpr_auto_id
BEFORE INSERT ON LPR
FOR EACH ROW

BEGIN
  SELECT seq_category.NEXTVAL
  INTO   :new.LNum
  FROM   dual;
END;


CREATE OR REPLACE TRIGGER result_auto_id
BEFORE INSERT ON Result
FOR EACH ROW

BEGIN
  SELECT seq_category.NEXTVAL
  INTO   :new.RNum
  FROM   dual;
END;


CREATE OR REPLACE TRIGGER alternative_auto_id
BEFORE INSERT ON Alternative
FOR EACH ROW

BEGIN
  SELECT seq_category.NEXTVAL
  INTO   :new.ANum
  FROM   dual;
END;


CREATE OR REPLACE TRIGGER vector_auto_id
BEFORE INSERT ON Vector
FOR EACH ROW

BEGIN
  SELECT seq_category.NEXTVAL
  INTO   :new.VNum
  FROM   dual;
END;


CREATE OR REPLACE TRIGGER mark_auto_id
BEFORE INSERT ON Mark
FOR EACH ROW

BEGIN
  SELECT seq_category.NEXTVAL
  INTO   :new.MNum
  FROM   dual;
END;


CREATE OR REPLACE TRIGGER criterion_auto_id
BEFORE INSERT ON Criterion
FOR EACH ROW

BEGIN
  SELECT seq_category.NEXTVAL
  INTO   :new.CNum
  FROM   dual;
END;