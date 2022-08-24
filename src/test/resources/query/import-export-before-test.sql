delete from files;

insert into files(id, status) values
            (1, 'DONE'),
            (2, 'IN PROGRESS'),
            (3, 'ERROR');

alter table files AUTO_INCREMENT=4;