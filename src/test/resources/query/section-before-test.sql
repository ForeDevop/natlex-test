delete from geoclasses;
delete from sections;

insert into sections (id, name) values
    (1, "Section 1"),
    (2, "Section 2");

alter table sections AUTO_INCREMENT=3;