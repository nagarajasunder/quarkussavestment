--drop table if exists SAVESTMENT_USERS;
--drop table if exists INVESTMENT_TYPES;
--drop table if exists INVESTMENT_ITEMS;
--drop table if exists EXPENDITURE_TYPE;
--drop table if exists EXPENDITURE_CATEGORY;
--drop table if exists EXPENDITURE;

CREATE TABLE IF NOT EXISTS SAVESTMENT_USERS
(
    USER_UID UUID NOT NULL,
    USER_ID VARCHAR UNIQUE NOT NULL,
    USER_FULL_NAME VARCHAR(100) NOT NULL,
    USER_DISPLAY_NAME VARCHAR(100) NOT NULL,
    USER_EMAIL_ADDRESS VARCHAR(100) NOT NULL UNIQUE,
    USER_PROFILE_URL VARCHAR(200),
    CREATED_ON TIMESTAMPTZ NOT NULL,
    UPDATED_ON TIMESTAMPTZ NOT NULL,
    UNIQUE (USER_UID)
);


CREATE TABLE IF NOT EXISTS INVESTMENT_TYPES
(
    INVESTMENT_TYPE_ID BIGSERIAL PRIMARY KEY,
    INVESTMENT_NAME VARCHAR(100) NOT NULL,
    CREATED_ON TIMESTAMPTZ NOT NULL,
    UPDATED_ON TIMESTAMPTZ NOT NULL
);


CREATE TABLE IF NOT EXISTS INVESTMENT_ITEMS
(
    INVESTMENT_ID VARCHAR PRIMARY KEY,
    INVESTMENT_TYPES_INVESTMENT_TYPE_ID BIGSERIAL REFERENCES INVESTMENT_TYPES(INVESTMENT_TYPE_ID),
    SYMBOL VARCHAR(100) NOT NULL,
    TRADE_DATE DATE NOT NULL,
    TRADE_TYPE VARCHAR(5) NOT NULL,
    UNITS NUMERIC(10,2) NOT NULL,
    PRICE NUMERIC(10,2) NOT NULL,
    AMOUNT_INVESTED NUMERIC(10,2) NOT NULL,
    CREATED_BY VARCHAR REFERENCES SAVESTMENT_USERS(USER_ID),
    CREATED_ON TIMESTAMPTZ NOT NULL,
    UPDATED_ON TIMESTAMPTZ NOT NULL
);

CREATE TABLE IF NOT EXISTS EXPENDITURE_TYPE
(
    EXPENDITURE_TYPE_ID BIGSERIAL PRIMARY KEY,
    EXPENDITURE_NAME VARCHAR(100) NOT NULL,
    CREATED_ON TIMESTAMPTZ NOT NULL,
    UPDATED_ON TIMESTAMPTZ NOT NULL
);

CREATE TABLE IF NOT EXISTS EXPENDITURE_CATEGORY
(
    EXPENDITURE_CATEGORY_ID BIGSERIAL PRIMARY KEY,
    EXPENDITURE_TYPE_EXPENDITURE_TYPE_ID BIGSERIAL,
    CATEGORY_NAME VARCHAR(100) NOT NULL,
    IS_COMMON boolean DEFAULT false,
    CREATED_BY VARCHAR,
    CREATED_ON TIMESTAMPTZ NOT NULL,
    UPDATED_ON TIMESTAMPTZ NOT NULL,
    CONSTRAINT FK_EXPENDITURE_TYPE_ID FOREIGN KEY(EXPENDITURE_TYPE_EXPENDITURE_TYPE_ID) REFERENCES EXPENDITURE_TYPE(EXPENDITURE_TYPE_ID)
);

CREATE TABLE IF NOT EXISTS EXPENDITURE
(
    EXPENDITURE_NUMBER VARCHAR PRIMARY KEY,
    EXPENDITURE_CATEGORY_EXPENDITURE_CATEGORY_ID BIGSERIAL REFERENCES EXPENDITURE_CATEGORY(EXPENDITURE_CATEGORY_ID),
    EXPENDITURE_AMOUNT NUMERIC(10,2) NOT NULL,
    EXPENDITURE_DESCRIPTION VARCHAR(500),
    MODE_OF_PAYMENT INT NOT NULL,
    DATE_OF_EXPENDITURE DATE,
    CREATED_BY VARCHAR REFERENCES SAVESTMENT_USERS(USER_ID),
    CREATED_ON TIMESTAMPTZ NOT NULL,
    UPDATED_ON TIMESTAMPTZ NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS expenditure_category_id_generator INCREMENT 1 START 10;

INSERT INTO EXPENDITURE_TYPE (EXPENDITURE_TYPE_ID,EXPENDITURE_NAME,CREATED_ON,UPDATED_ON)
VALUES (1,'Income',now(),now()),(2,'Expense',now(),now());


INSERT INTO EXPENDITURE_CATEGORY
(
	EXPENDITURE_CATEGORY_ID,
    EXPENDITURE_TYPE_EXPENDITURE_TYPE_ID,
    CATEGORY_NAME,
    IS_COMMON,
    CREATED_BY,
    CREATED_ON,
    UPDATED_ON
)
VALUES
(1,1,'Salary',true,'SYSTEM',now(),now()),
(2,2,'Rent',true,'SYSTEM',now(),now()),
(3,2,'Food',true,'SYSTEM',now(),now()),
(4,2,'Electricity',true,'SYSTEM',now(),now());


INSERT INTO INVESTMENT_TYPES (INVESTMENT_TYPE_ID,INVESTMENT_NAME,CREATED_ON,UPDATED_ON)
VALUES
(10,'Indian Stocks',now(),now()),
(11,'Mutual Funds',now(),now()),
(12,'US Stocks',now(),now());

--INSERT INTO INVESTMENT_ITEMS (INVESTMENT_ID,INVESTMENT_TYPES_INVESTMENT_TYPE_ID,TRADE_DATE,AMOUNT_INVESTED,TRADE_TYPE,CREATED_ON,UPDATED_ON)
--VALUES
--('INV-10',10,'2023-02-11',776,0,now(),now()),
--('INV-16',10,'2023-02-11',77,0,now(),now()),
--('INV-11',11,'2023-02-15',2500000,1,now(),now()),
--('INV-12',11,'2023-02-16',112,0,now(),now()),
--('INV-13',10,'2023-02-21',3433,1,now(),now());