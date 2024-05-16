drop database if exists DBKinalExpress;
create database DBKinalExpress;
use DBKinalExpress;

-- solucion error mysql zona horaria 
 -- set global time_Zone = '-6:00';
 
create table Clientes(
	codigoCliente int not null,
	NITcliente varchar(10) not null,
	nombreCliente varchar(50) not null,
	apellidoCliente varchar(50) not null,
	direccionCliente varchar(150),
	telefonoCliente varchar(8),
	correoCliente varchar(45),
	primary key PK_codigoCliente(codigoCliente)
);
create table Proveedores(
	codigoProveedor int not null,
    NITproveedor varchar(10) not null,
    nombreProveedor varchar(60),
    apellidosProveedor varchar(60),
    direccionProveedor varchar(150),
    razonSocial varchar(60),
    contactoPrincipal varchar(100),
    paginaWeb varchar(50),
    primary key PK_codigoProveedor(codigoProveedor)
);

create table CargoEmpleado(
	codigoCargoEmpleado int not null,
	nombreCargo varchar(45),
	descripcionCargo varchar(45),
	primary key PK_codigoCargoEmpleado(codigoCargoEmpleado)
);

create table TipoProducto(
	codigoTipoProducto int not null,
	descripcion varchar(45),
	primary key PK_codigoTipoProducto(codigoTipoProducto)
);

create table Compras(
	numeroDocumento int not null,
	fechaDocumento date,
	descripcion varchar (60),
	totalDocumento decimal(10,2),
	primary key PK_numeroDocumento(numeroDocumento)

);
 
-- -----------------------------Procedimiento Almacenado---------------------------------
-- -------- Clientes
-- Agregar 
Delimiter $$
create procedure Sp_AgregarClientes (in codigoCliente int, in NITcliente varchar(10), in nombreCliente varchar(50), in apellidoCliente varchar(50),
in direccionCliente varchar(150), in telefonoCliente varchar(8), in correoCliente varchar(45))
	begin 
		insert into Clientes (codigoCliente,NITcliente,nombreCliente,apellidoCliente,direccionCliente,telefonoCliente,correoCliente) values (codigoCliente,NITcliente,nombreCliente,apellidoCliente,direccionCliente,telefonoCliente,correoCliente);
	end $$
Delimiter ;
call Sp_AgregarClientes (01,'14587452','harol', 'Luna','San Marcos', '4575','harolsito79@gmail.com' );
call Sp_AgregarClientes (02,'14587452','Luna', 'Donis','San Martin', '4894','Lunita89@gmail.com' );
-- Listar Clientes
Delimiter $$
create procedure sp_ListarClientes()
	begin	
		select 
			C.codigoCliente,
			C.NITcliente,
			C.nombreCliente,
			C.apellidoCliente,
			C.direccionCliente,
			C.telefonoCliente,
			C.correoCliente
			from Clientes C;
	end $$
Delimiter ;
call sp_ListarClientes();
 
-- Buscar Clientes
Delimiter $$
	create procedure sp_BuscarClientes (in codCli int)
    begin 
			select C.codigoCliente,
			C.NITcliente,
			C.nombreCliente,
			C.apellidoCliente,
			C.direccionCliente,
			C.telefonoCliente,
			C.correoCliente
		from Clientes C 
		where codigoCliente = codCli;
	end $$
delimiter ;
call sp_BuscarClientes(1);
 
-- Eliminar Clientes
Delimiter $$
	create procedure sp_EliminarClientes(in codCli int)
		begin 
			delete from Clientes
            where codigoCliente = codCli;
        end $$
delimiter ; 
call sp_EliminarClientes(1);
call sp_ListarClientes();
 
-- editar Clientes
Delimiter $$
	create procedure sp_EditarClientes(in codCli int, in nCliente varchar(10), in nomCliente varchar(50), in apCliente varchar(50),
		in direcCliente varchar(150), in telCliente varchar(8), in corrCliente varchar(45))
			begin 
				update Clientes C
					set
					C.NITcliente = nCliente,
					C.nombreCliente = nomCliente,
					C.apellidoCliente = apCliente,
					C.direccionCliente = direcCliente,
					C.telefonoCliente = telCliente,
					C.correoCliente = corrCliente
                    where codigoCliente = codCli;
			end $$
delimiter ;                 
 
call sp_EditarClientes(02,'owo','seeeee', 'Luna','San Marcos', '4575','harolsito79@gmail.com');
call sp_ListarClientes();
 
 -- -----------------------------Procedimiento Almacenado---------------------------------
-- -------- Proveedores
-- Agregar 
Delimiter $$
create procedure Sp_AgregarProveedores (in codigoProveedor int, in NITproveedor varchar(10), in nombreProveedor varchar(60), in apellidosProveedor varchar(60),
in direccionProveedor varchar(150), in razonSocial varchar(60), in contactoPrincipal varchar(45), in paginaWeb varchar(50))
	begin 
		insert into Proveedores (codigoProveedor,NITproveedor,nombreProveedor,apellidosProveedor,direccionProveedor,razonSocial,contactoPrincipal,paginaWeb) values (codigoProveedor,NITproveedor,nombreProveedor,apellidosProveedor,direccionProveedor,razonSocial,contactoPrincipal,paginaWeb);
	end $$
Delimiter ;
call Sp_AgregarProveedores (01,'489652', 'hector','Gonzales', '75-75','importadores','hector@gmail.com','http/www/importaciones.com' );
call Sp_AgregarProveedores (02,'748541','Nicola', 'Jimenez','45-68', 'Socios','nicola@gmail.com','http/www/Socios.com' );

-- Listar 
Delimiter $$
create procedure sp_ListarProveedores()
	begin	
		select 
			P.codigoProveedor,
			P.NITproveedor,
            P.nombreProveedor,
            P.apellidosProveedor,
            P.direccionProveedor,
            P.razonSocial,
            P.contactoPrincipal,
            P.paginaWeb
			from Proveedores P;
	end $$
Delimiter ;
call sp_ListarProveedores();

-- Buscar
Delimiter $$
	create procedure sp_BuscarProveedores (in codPro int)
    begin 
			select 
			P.codigoProveedor,
			P.NITproveedor,
            P.nombreProveedor,
            P.apellidosProveedor,
            P.direccionProveedor,
            P.razonSocial,
            P.contactoPrincipal,
            P.paginaWeb
			from Proveedores P
		where codigoProveedor = codPro;
	end $$
delimiter ;
call sp_BuscarProveedores(1);
 
 -- eliminar proveedores
 Delimiter $$
	create procedure sp_EliminarProveedores(in codPro int)
		begin 
			delete from Proveedores
            where codigoProveedor = codPro;
        end $$
delimiter ; 
call sp_EliminarProveedores(1);
call sp_ListarProveedores();

-- editar Proveedores
Delimiter $$
	create procedure sp_EditarProveedores(in codPro int, in nPro varchar(10), in nomPro varchar(60), in apPro varchar(60),
		in direcPro varchar(150), in razonPro varchar(60), in contactoPro varchar(45), pagPro varchar(50))
			begin 
				update Proveedores P
					set
				
				P.NITproveedor= nPro,
				P.nombreProveedor = nomPro,
				P.apellidosProveedor = apPro,
				P.direccionProveedor = direcPro,
				P.razonSocial = razonPro,
				P.contactoPrincipal = contactoPro,
				P.paginaWeb = pagPro
			
			where codigoProveedor = codPro;
			end $$
delimiter ;                 
 
call sp_EditarProveedores(02,'8596','Cockis', 'mark','Santa rosa', 'Importador','Cokismark@gmail.com', 'http/www.Cokismark.com');
call sp_ListarProveedores();
-- -----------------------------Procedimiento Almacenado---------------------------------
-- --------  CargoEmpleado
-- Agregar 
Delimiter $$
create procedure Sp_AgregarCargoEmpleado (in codigoCargoEmpleado int, in nombreCargo varchar(45), in descripcionCargo varchar(45))
	begin 
		insert into CargoEmpleado (codigoCargoEmpleado,nombreCargo,descripcionCargo) values (codigoCargoEmpleado,nombreCargo,descripcionCargo);
	end $$
Delimiter ;
call Sp_AgregarCargoEmpleado (01,'Pedido1', 'Para entretenimiento');
call Sp_AgregarCargoEmpleado (02,'Pedido2','Para mantenimiento');



-- Listar 
Delimiter $$
create procedure sp_ListarCargoEmpleado()
	begin	
		select 
			P.codigoCargoEmpleado,
			P.nombreCargo,
            P.descripcionCargo
          
			from CargoEmpleado P;
	end $$
Delimiter ;
call sp_ListarCargoEmpleado();

-- Buscar
Delimiter $$
	create procedure sp_BuscarCargoEmpleado (in codEmple int)
    begin 
			select 
			P.codigoCargoEmpleado,
			P.nombreCargo,
            P.descripcionCargo
          
			from CargoEmpleado P
		where codigoCargoEmpleado = codEmple;
	end $$
delimiter ;
call sp_BuscarCargoEmpleado(1);

 -- eliminar 
 Delimiter $$
	create procedure sp_EliminarCargoEmpleado(in codEmple int)
		begin 
			delete from CargoEmpleado
            where codigoCargoEmpleado = codEmple;
        end $$
delimiter ; 
call sp_EliminarCargoEmpleado(1);
call sp_ListarCargoEmpleado();

-- editar 
Delimiter $$
	create procedure sp_EditarCargoEmpleado(in codEmpleado int, in nomCargo varchar(45), in deCargo varchar(45))
			begin 
				update CargoEmpleado P
					set
				
				P.nombreCargo = nomCargo,
				P.descripcionCargo = deCargo
				
			
			where codigoCargoEmpleado = codEmpleado;
			end $$
delimiter ;                 
 
call sp_EditarCargoEmpleado(02,'Pedidos','verificacion de area');
call sp_ListarCargoEmpleado();

-- -----------------------------Procedimiento Almacenado---------------------------------
-- --------  TipoProducto
-- Agregar 
Delimiter $$
create procedure Sp_AgregarTipoProducto(in codigoTipoProducto int, in descripcion varchar(45))
	begin 
		insert into TipoProducto(codigoTipoProducto,descripcion) values (codigoTipoProducto,descripcion);
	end $$
Delimiter ;
call Sp_AgregarTipoProducto (01,'numero: 4856');
call Sp_AgregarTipoProducto (02,'numero: 4857');

-- Listar 
Delimiter $$
create procedure sp_ListarTipoProducto()
	begin	
		select 
			P.codigoTipoProducto,
			P.descripcion
          
			from TipoProducto P;
	end $$
Delimiter ;
call sp_ListarTipoProducto();

-- Buscar
Delimiter $$
	create procedure sp_BuscarTipoProducto (in codPro int)
    begin 
			select 
			P.codigoTipoProducto,
			P.descripcion
          
			from TipoProducto P
		where codigoTipoProducto = codPro;
	end $$
delimiter ;
 call sp_BuscarTipoProducto(1);

 -- eliminar 
 Delimiter $$
	create procedure sp_EliminarTipoProducto(in codPro int)
		begin 
			delete from TipoProducto
            where codigoTipoProducto = codPro;
        end $$
delimiter ; 
call sp_EliminarTipoProducto(1);
  call sp_ListarTipoProducto();

-- editar 
Delimiter $$
	create procedure sp_EditarTipoProducto(in codPro int, in descrip varchar(45))
			begin 
				update TipoProducto P
					set
				
				P.descripcion = descrip
				
				
			
			where codigoTipoProducto = codPro;
			end $$
delimiter ;                 
 
call sp_EditarTipoProducto(02,'numero: 8429');
call sp_ListarTipoProducto();

-- -----------------------------Procedimiento Almacenado---------------------------------
-- -------- Compras
-- Agregar 
Delimiter $$
create procedure Sp_AgregarCompras (in numeroDocumento int, in fechaDocumento date, in descripcion varchar (60), in totalDocumento decimal(10,2))
	begin 
		insert into Compras (numeroDocumento,fechaDocumento,descripcion,totalDocumento) values (numeroDocumento,fechaDocumento,descripcion,totalDocumento);
	end $$
Delimiter ;
call Sp_AgregarCompras (01, '2024-04-22', 'compra efectiva',20.05);
call Sp_AgregarCompras (02, '2024-04-21', 'compra efectiva', 23.02);

-- numeroDocumento int not null,
-- fechaDocumento date,
-- descripcion varchar (60),
-- totalDocumento decimal(10,2),

-- Listar 
Delimiter $$
create procedure sp_ListarCompras()
	begin	
		select 
			P.numeroDocumento,
			P.fechaDocumento,
            P.descripcion,
            P.totalDocumento
          
			from Compras P;
	end $$
Delimiter ;
call sp_ListarCompras();

-- Buscar
Delimiter $$
	create procedure sp_BuscarCompras (in codPro int)
    begin 
			select 
			P.numeroDocumento,
			P.fechaDocumento,
            P.descripcion,
            P.totalDocumento
          
			from Compras P
		where numeroDocumento = codPro;
	end $$
delimiter ;
 call sp_BuscarCompras(1);
 
 
-- eliminar 
 Delimiter $$
	create procedure sp_EliminarCompras(in codPro int)
		begin 
			delete from Compras
            where numeroDocumento = codPro;
        end $$
delimiter ; 
call sp_EliminarCompras(1);
call sp_ListarCompras();

-- editar 
Delimiter $$
	create procedure sp_EditarCompras(in numDocumento int, in fDocument date, in descrip varchar (60), in toDocumento decimal(10,2))
			begin 
				update Compras P
					set
				
				P.fechaDocumento = fDocument,
                P.descripcion = descrip,
                P.totalDocumento = toDocumento
				
				
			
			where numeroDocumento = numDocumento;
			end $$
delimiter ;                 
 
call sp_EditarCompras(02,'05-11-24','mala administracion',30.50);
call sp_ListarCompras();

