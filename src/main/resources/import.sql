insert into regiones (id,nombre) values (1,'Ambato');
insert into regiones (id,nombre) values (2,'Quito');
insert into regiones (id,nombre) values (3,'Guayaquil');
insert into regiones (id,nombre) values (4,'Latacunga');


insert into clientes(region_id,nombre,apellido,email,create_at) values (1,'Pablo','Alvear','pablo@gmail.com','2018-01-01');
insert into clientes(region_id,nombre,apellido,email,create_at) values (2,'Juan','Perez','juan@gmail.com','2019-01-01');
insert into clientes(region_id,nombre,apellido,email,create_at) values (3,'David','Sanchez','david@gmail.com','2017-01-01');
insert into clientes(region_id,nombre,apellido,email,create_at) values (4,'Paul','Arboleda','paul@gmail.com','2016-01-01');
insert into clientes(region_id,nombre,apellido,email,create_at) values (2,'Andres','Robalino','andres@gmail.com','2015-05-01');
insert into clientes(region_id,nombre,apellido,email,create_at) values (1,'Daniela','Villacis','daniela@gmail.com','2018-07-09');


insert into productos(nombre,precio,create_at) values ('Monitor LG',200,NOW());
insert into productos(nombre,precio,create_at) values ('laptop DELL',1200,NOW());
insert into productos(nombre,precio,create_at) values ('Mouse Logitech',60,NOW());
insert into productos(nombre,precio,create_at) values ('Teclado HP',25,NOW());
insert into productos(nombre,precio,create_at) values ('Ryzen 5',250,NOW());
insert into productos(nombre,precio,create_at) values ('Nvidia RTX 2080',200,NOW());
insert into productos(nombre,precio,create_at) values ('Monitor Panasonic',450,NOW());
insert into productos(nombre,precio,create_at) values ('Mouse Genius',20,NOW());
insert into productos(nombre,precio,create_at) values ('Laptop HP',1000,NOW());
insert into productos(nombre,precio,create_at) values ('Laptop Asus Gaming',1500,NOW());
insert into productos(nombre,precio,create_at) values ('Monitor HP HDMI',1200,NOW());
insert into productos(nombre,precio,create_at) values ('procesador i5',270,NOW());
insert into productos(nombre,precio,create_at) values ('procesador i7',480,NOW());



insert into ventas(descripcion,observacion,cliente_id,create_at) values('Venta de componentes pc',null,1,NOW());
insert into detalle_ventas(cantidad,factura_id,producto_id) values(1,1,1);
insert into detalle_ventas(cantidad,factura_id,producto_id) values(2,1,4);


insert into ventas(descripcion,observacion,cliente_id,create_at) values('Venta de Monitor pc',null,1,NOW());
insert into detalle_ventas(cantidad,factura_id,producto_id) values(1,2,1);
