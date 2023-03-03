drop table if exists SAVESTMENT_USERS;
drop table if exists INVESTMENT_TYPES;
drop table if exists INVESTMENT_ITEMS;
drop table if exists EXPENDITURE_TYPE;
drop table if exists EXPENDITURE_CATEGORY;
drop table if exists EXPENDITURE;

CREATE TABLE IF NOT EXISTS SAVESTMENT_USERS
(
    USER_UID UUID NOT NULL,
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
    INVESTMENT_TYPES_INVESTMENT_TYPE_ID BIGSERIAL,
    SYMBOL VARCHAR(100) NOT NULL,
    TRADE_DATE DATE NOT NULL,
    TRADE_TYPE INT NOT NULL,
    UNITS NUMERIC NOT NULL,
    PRICE NUMERIC(10,2) NOT NULL,
    AMOUNT_INVESTED NUMERIC(10,2) NOT NULL,
    CREATED_BY UUID,
    CREATED_ON TIMESTAMPTZ NOT NULL,
    UPDATED_ON TIMESTAMPTZ NOT NULL,
    CONSTRAINT FK_INVESTMENT_TYPE_ID FOREIGN KEY(INVESTMENT_TYPES_INVESTMENT_TYPE_ID) REFERENCES INVESTMENT_TYPES(INVESTMENT_TYPE_ID),
    CONSTRAINT FK_CREATED_BY FOREIGN KEY(CREATED_BY) REFERENCES SAVESTMENT_USERS(USER_UID)
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
    CREATED_BY UUID,
    CREATED_ON TIMESTAMPTZ NOT NULL,
    UPDATED_ON TIMESTAMPTZ NOT NULL,
    CONSTRAINT FK_EXPENDITURE_TYPE_ID FOREIGN KEY(EXPENDITURE_TYPE_EXPENDITURE_TYPE_ID) REFERENCES EXPENDITURE_TYPE(EXPENDITURE_TYPE_ID)
);

CREATE TABLE IF NOT EXISTS EXPENDITURE
(
    EXPENDITURE_NUMBER VARCHAR PRIMARY KEY,
    EXPENDITURE_CATEGORY_EXPENDITURE_CATEGORY_ID BIGSERIAL,
    EXPENDITURE_AMOUNT NUMERIC(10,2) NOT NULL,
    EXPENDITURE_DESCRIPTION VARCHAR(500),
    MODE_OF_PAYMENT INT NOT NULL,
    DATE_OF_EXPENDITURE DATE,
    CREATED_BY UUID,
    CREATED_ON TIMESTAMPTZ NOT NULL,
    UPDATED_ON TIMESTAMPTZ NOT NULL,
    CONSTRAINT FK_EXPENDITURE_CATEGORY_ID FOREIGN KEY(EXPENDITURE_CATEGORY_EXPENDITURE_CATEGORY_ID) REFERENCES EXPENDITURE_CATEGORY(EXPENDITURE_CATEGORY_ID)
);

