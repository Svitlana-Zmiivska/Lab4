insert into lpr (lname, lrange) values ('Default', 10);
commit;

update lpr set lnum = 0 where lname = 'Default';
commit;