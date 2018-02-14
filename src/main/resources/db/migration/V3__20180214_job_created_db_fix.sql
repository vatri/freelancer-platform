update `job` set created = CURRENT_TIMESTAMP WHERE created is null;
alter TABLE job change created `created` timestamp not NULL DEFAULT CURRENT_TIMESTAMP;