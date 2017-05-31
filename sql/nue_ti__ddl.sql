CREATE SEQUENCE seq_LPR;
CREATE SEQUENCE seq_Result;
CREATE SEQUENCE seq_Alternative;
CREATE SEQUENCE seq_Vector;
CREATE SEQUENCE seq_Mark;
CREATE SEQUENCE seq_Criterion;


CREATE TABLE LPR (
    LNum number(19) NOT NULL,
    LName varchar2(255),
    LRange number(10),
    PRIMARY KEY (LNum)
);

CREATE TABLE Result (
    RNum number(19) NOT NULL,
    LNum number(19) NOT NULL,
    ANum number(19) NOT NULL,
    Range float,
    AWeight number(10),
    PRIMARY KEY (RNum)
);

CREATE TABLE Alternative (
    ANum number(19) NOT NULL,
    AName varchar2(255),
    PRIMARY KEY (ANum)
);

CREATE TABLE Vector (
    VNum number(19) NOT NULL,
    ANum number(19) NOT NULL,
    MNum number(19) NOT NULL,
    PRIMARY KEY (VNum)
);

CREATE TABLE Mark (
    MNum number(19) NOT NULL,
    CNum number(19) NOT NULL,
    MName varchar2(255),
    MRange number(10),
    NumMark float,
    NormMark number(10),
    PRIMARY KEY (MNum)
);

CREATE TABLE Criterion (
    CNum number(19) NOT NULL,
    CName varchar2(255),
    CRange number(10),
    CWeight number(10),
    CType varchar2(255),
    OptimType varchar2(255),
    EdIzmer varchar2(255),
    ScaleType varchar2(255),
    PRIMARY KEY (CNum)
);


ALTER TABLE Result ADD CONSTRAINT FK_LNum FOREIGN KEY (LNum) REFERENCES LPR (LNum);
ALTER TABLE Result ADD CONSTRAINT FK_ANum_R FOREIGN KEY (ANum) REFERENCES Alternative (ANum);
ALTER TABLE Vector ADD CONSTRAINT FK_ANum_V FOREIGN KEY (ANum) REFERENCES Alternative (ANum);
ALTER TABLE Vector ADD CONSTRAINT FK_MNum FOREIGN KEY (MNum) REFERENCES Mark (MNum);
ALTER TABLE Mark ADD CONSTRAINT FK_CNum FOREIGN KEY (CNum) REFERENCES Criterion (CNum);