ALTER table subscriber ADD COLUMN call_count INT UNSIGNED NOT NULL DEFAULT 0;
ALTER table subscriber ADD column sms_count INT UNSIGNED NOT NULL DEFAULT 0;