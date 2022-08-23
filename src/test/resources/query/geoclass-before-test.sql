delete from geoclasses;

insert into geoclasses (id, name, code) values
                                    (1, "Geo Class 11", "GC11"),
                                    (2, "Geo Class 12", "GC12"),
                                    (3, "Geo Class 21", "GC21");

alter table geoclasses AUTO_INCREMENT=4;