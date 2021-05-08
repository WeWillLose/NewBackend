insert into user(id,username, password, is_active) value (
    1,
    "admin",
    "$2a$10$9x1fBdq.fXcCcZuF0SHKQewKbrK2RrCFOLzQctuBRdaJceIpOcmZ.",
    1
);
insert into user_role value (1,'CHAIRMAN');
insert into user_role value (1,'ADMIN');
insert into user_role value (1,'TEACHER');