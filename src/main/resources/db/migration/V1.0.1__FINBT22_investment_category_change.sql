ALTER TABLE investment_types
ADD created_by varchar(255);

UPDATE investment_types SET created_by = 'SYSTEM';

ALTER TABLE investment_types ADD COLUMN is_common bool;
UPDATE investment_types SET is_common = true;