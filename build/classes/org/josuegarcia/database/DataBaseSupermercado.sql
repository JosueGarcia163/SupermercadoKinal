drop database if exists DBKinalExpress;
create database DBKinalExpress;
use DBKinalExpress;

-- solucion error mysql zona horaria 
  set global time_Zone = '-6:00';
 
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

create table TelefonoProveedor(
	codigoTelefonoProveedor int not null,
    numeroPrincipal varchar(8),
    numeroSecundario varchar(8),
    observarciones varchar(45),
    codigoProveedor int,
    primary key PK_codigoTelefonoProveedor(codigoTelefonoProveedor),
    constraint FK_TelefonoProveedor_Proveedores foreign key TelefonoProveedor(codigoProveedor)
		references Proveedores(codigoProveedor)
);
 
create table EmailProveedor(
	codigoEmailProveedor int not null,
    emailProveedor varchar(50),
    descripcion varchar(100),
    codigoProveedor int,
	primary key PK_EmailProveedor(codigoEmailProveedor),
	constraint FK_EmailProveedor_Proveedores foreign key EmailProveedor(codigoProveedor)
		references Proveedores(codigoProveedor)
);
 
 
create table Productos(
	codigoProducto varchar(15),
    descripcionProducto varchar(45),
    precioUnitario decimal(10,2),
    precioDocena decimal(10,2),
    precioMayor decimal(10,2),
    imagenProducto varchar(45),
    existencia int not null,
    codigoTipoProducto int,
    codigoProveedor int,
    primary key PK_Productos(codigoProducto),
    constraint FK_Productos_TipoProducto foreign key Productos(codigoTipoProducto)
		references TipoProducto(codigoTipoProducto),
    constraint FK_Productos_Proveedores foreign key Productos(codigoProveedor)
		references Proveedores(codigoProveedor)
);
 
 
create table Empleados(
	codigoEmpleado int not null,
	nombresEmpleado varchar(50),
    apellidosEmpleado varchar(50),
    sueldo decimal(10,2),
    direccion varchar(150),
    turno varchar(15),
    codigoCargoEmpleado int,
    primary key PK_Empleados(codigoEmpleado),
    constraint FK_Empleados_CargoEmpleado foreign key Empleados(codigoCargoEmpleado)
		references CargoEmpleado(codigoCargoEmpleado)
);
 
create table DetalleCompra(
	codigoDetalleCompra int not null,
	costoUnitario decimal(10,2),
    cantidad int not null,
    codigoProducto varchar(15),
    numeroDocumento int,
	primary key PK_DetalleCompra(codigoDetalleCompra),
    constraint FK_DetalleCompra_Productos foreign key DetalleCompra(codigoProducto)
		references Productos(codigoProducto),
	constraint FK_DetalleCompra_Compras foreign key DetalleCompra(numeroDocumento)
		references Compras(numeroDocumento)
);
 
create table Factura(
	numeroFactura int not null,
    estado varchar(50),
    totalFactura decimal(10,2),
    fechaFactura varchar(45),
    codigoCliente int,
    codigoEmpleado int,
    primary key PK_Factura(numeroFactura),
    constraint FK_Factura_Clientes foreign key Factura(codigoCliente)
		references Clientes(codigoCliente),
    constraint FK_Factura_Empleados foreign key Factura(codigoEmpleado)
		references Empleados(codigoEmpleado)
);
 
 
create table DetalleFactura(
	codigoDetalleFactura int not null,
	precioUnitario decimal(10,2),
    cantidad int not null,
    numeroFactura int,
    codigoProducto varchar(15),
    primary key PK_DetalleFactura(codigoDetalleFactura),
    constraint FK_DetalleFactura_Factura foreign key DetalleFactura(numeroFactura)
		references Factura(numeroFactura),
    constraint FK_DetalleFactura_Productos foreign key DetalleFactura(codigoProducto)
		references Productos(codigoProducto)
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
-- call sp_EliminarClientes(1);
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
 -- call sp_EliminarProveedores(1);
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
-- call sp_EliminarCargoEmpleado(1);
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
-- call sp_EliminarTipoProducto(1);
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
-- call sp_EliminarCompras(1);
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


-- -----------------------------Procedimiento Almacenado---------------------------------
-- -------- TelefonoProveedor
-- Agregar 
Delimiter $$
create procedure Sp_AgregarTelefonoProveedor (in codigoTelefonoProveedor int, in numeroPrincipal varchar(8), in numeroSecundario varchar (8), in observarciones varchar(45), in codigoProveedor int)
	begin 
		insert into TelefonoProveedor (codigoTelefonoProveedor,numeroPrincipal,numeroSecundario,observarciones,codigoProveedor) values (codigoTelefonoProveedor,numeroPrincipal,numeroSecundario,observarciones,codigoProveedor);
	end $$
Delimiter ;
call Sp_AgregarTelefonoProveedor (01, '48579635', '48512463','No perder numero principal', 1);
call Sp_AgregarTelefonoProveedor (02, '78954763', '84172369', 'No perder el numero secundario', 2);

 -- codigoTelefonoProveedor int not null,
  --  numeroPrincipal varchar(8),
 --   numeroSecundario varchar(8),
  --  observarciones varchar(45),
   -- codigoProveedor int,

-- Listar 
Delimiter $$
create procedure sp_ListarTelefonoProveedor()
	begin	
		select 
			T.codigoTelefonoProveedor,
			T.numeroPrincipal,
			T.numeroSecundario,
            T.observarciones,
            T.codigoProveedor
          
			from TelefonoProveedor T;
	end $$
Delimiter ;
call sp_ListarTelefonoProveedor();

-- Buscar
Delimiter $$
	create procedure sp_BuscarTelefonoProveedor (in codTe int)
    begin 
			select 
			T.codigoTelefonoProveedor,
			T.numeroPrincipal,
			T.numeroSecundario,
            T.observarciones,
            T.codigoProveedor
          
			from TelefonoProveedor T
		where codigoTelefonoProveedor = codTe;
	end $$
delimiter ;
 call sp_BuscarTelefonoProveedor(1);
 
 
-- eliminar 
 Delimiter $$
	create procedure sp_EliminarTelefonoProveedor(in codTe int)
		begin 
			delete from TelefonoProveedor
            where codigoTelefonoProveedor = codTe;
        end $$
delimiter ; 
-- call sp_EliminarTelefonoProveedor(1);
call sp_ListarTelefonoProveedor();

-- editar 
Delimiter $$
	create procedure sp_EditarTelefonoProveedor(in codigoTe int, in numeroP varchar(8), in numeroS varchar (8), in obser varchar(45))
			begin 
				update TelefonoProveedor T
					set
				
				T.numeroPrincipal = numeroP,
                T.numeroSecundario = numeroS,
                T.observarciones = obser
				
				
			
			where codigoTelefonoProveedor = codigoTe;
			end $$
delimiter ;                 
 
call sp_EditarTelefonoProveedor(01,'69696969','79797979', 'Debe de cambiar de numero');
call sp_ListarTelefonoProveedor();



-- -----------------------------Procedimiento Almacenado---------------------------------
-- -------- EmailProveedor
-- Agregar 
Delimiter $$
create procedure Sp_AgregarEmailProveedor (in codigoEmailProveedor int, in emailProveedor varchar(50), in descripcion varchar (100), in codigoProveedor int)
	begin 
		insert into EmailProveedor (codigoEmailProveedor,emailProveedor,descripcion,codigoProveedor) values (codigoEmailProveedor,emailProveedor,descripcion,codigoProveedor);
	end $$
Delimiter ;
call Sp_AgregarEmailProveedor (01, 'cocacola69@gmail.com', 'Azucar para el dia', 1);
call Sp_AgregarEmailProveedor (02, 'pepsi69@gmail.com', 'Energia para el dia', 2);

--  codigoEmailProveedor int not null,
--    emailProveedor varchar(50),
--    descripcion varchar(100),
--    codigoProveedor int,

-- Listar 
Delimiter $$
create procedure sp_ListarEmailProveedor()
	begin	
		select 
			E.codigoEmailProveedor,
			E.emailProveedor,
			E.descripcion,
            E.codigoProveedor
         
          
			from EmailProveedor E;
	end $$
Delimiter ;
call sp_ListarEmailProveedor();

-- Buscar
Delimiter $$
	create procedure sp_BuscarEmailProveedor (in codEm int)
    begin 
			select 
			E.codigoEmailProveedor,
			E.emailProveedor,
			E.descripcion,
            E.codigoProveedor
          
			from EmailProveedor E
		where codigoEmailProveedor = codEm;
	end $$
delimiter ;
 call sp_BuscarEmailProveedor(1);
 
 
-- eliminar 
 Delimiter $$
	create procedure sp_EliminarEmailProveedor(in codEm int)
		begin 
			delete from EmailProveedor
            where codigoEmailProveedor = codEm;
        end $$
delimiter ; 
-- call sp_EliminarEmailProveedor(2);
call sp_ListarEmailProveedor();

-- editar 
Delimiter $$
	create procedure sp_EditarEmailProveedor(in codigoE int, in emailPro varchar(50), in descrip varchar (100))
			begin 
				update EmailProveedor E
					set
				
				
                E.emailProveedor = emailPro,
                E.descripcion = descrip
				
				
			
			where codigoEmailProveedor = codigoE;
			end $$
delimiter ;                 
 
call sp_EditarEmailProveedor(01,'Empresa69@gmail.com','Los mas exitosos del planeta');
call sp_ListarEmailProveedor();


-- -----------------------------Procedimiento Almacenado---------------------------------
-- -------- Productos
-- Agregar 
Delimiter $$
create procedure Sp_AgregarProductos (in codigoProducto varchar(15), in descripcionProducto varchar(45), in precioUnitario decimal(10,2), in precioDocena decimal(10,2), in precioMayor decimal(10,2),  in existencia int, in codigoTipoProducto int, in codigoProveedor int)
	begin 
		insert into Productos (codigoProducto,descripcionProducto,precioUnitario,precioDocena,precioMayor,existencia,codigoTipoProducto,codigoProveedor) values (codigoProducto,descripcionProducto,precioUnitario,precioDocena,precioMayor,existencia,codigoTipoProducto,codigoProveedor);
	end $$
Delimiter ;
call Sp_AgregarProductos ('E5', 'Producto exitoso', 10.52, 125.69, 300.96, 56, 1, 1);
call Sp_AgregarProductos ('E6', 'Producto hecho para familias', 11.89, 365.96, 400.56, 49, 2, 2);

 --   codigoProducto varchar(15),
 --   descripcionProducto varchar(45),
 --   precioUnitario decimal(10,2),
 --   precioDocena decimal(10,2),
 --   precioMayor decimal(10,2),
 --   imagenProducto varchar(45),
 --   existencia int not null,
 --   codigoTipoProducto int,
 --   codigoProveedor int,

-- Listar 
Delimiter $$
create procedure sp_ListarProductos()
	begin	
		select 
			P.codigoProducto,
			P.descripcionProducto,
			P.precioUnitario,
            P.precioDocena,
            P.precioMayor,
            P.existencia,
            P.codigoTipoProducto,
            P.codigoProveedor
          
			from Productos P;
	end $$
Delimiter ;
call sp_ListarProductos();

-- Buscar
Delimiter $$
	create procedure sp_BuscarProductos (in codP varchar(15))
    begin 
			select 
			P.codigoProducto,
			P.descripcionProducto,
			P.precioUnitario,
            P.precioDocena,
            P.precioMayor,
            P.existencia,
            P.codigoTipoProducto,
            P.codigoProveedor
          
			from Productos P
		where codigoProducto = codP;
	end $$
delimiter ;
 call sp_BuscarProductos('E6');
 
 
-- eliminar 
 Delimiter $$
	create procedure sp_EliminarProductos(in codP varchar(15))
		begin 
			delete from Productos
            where codigoProducto = codP;
        end $$
delimiter ; 
-- call sp_EliminarProductos('E6');
call sp_ListarProductos();

-- editar 
Delimiter $$
	create procedure sp_EditarProductos(in codigoPro varchar(15), in descripcionPro varchar(45), in precioUn decimal(10,2), in precioDoc decimal(10,2), in precioM decimal(10,2),  in exis int)
			begin 
				update Productos P
					set
				
				
				P.descripcionProducto = descripcionPro,
				P.precioUnitario = precioUn,
				P.precioDocena = precioDoc,
				P.precioMayor = precioM,
				P.existencia = exis
				
				
				
			
			where codigoProducto = codigoPro;
			end $$
delimiter ;                 
 
call sp_EditarProductos('E5','Bastante util',69.69, 102.84, 502.87, 2);
call sp_ListarProductos();



-- -----------------------------Procedimiento Almacenado---------------------------------
-- -------- Empleados
-- Agregar 
Delimiter $$
create procedure Sp_AgregarEmpleados (in codigoEmpleado int, in nombresEmpleado varchar(50), in apellidosEmpleado varchar(50), in sueldo decimal(10,2), in direccion varchar(150),  in turno varchar(15), in codigoCargoEmpleado int)
	begin 
		insert into Empleados (codigoEmpleado,nombresEmpleado,apellidosEmpleado,sueldo,direccion,turno,codigoCargoEmpleado) values (codigoEmpleado,nombresEmpleado,apellidosEmpleado,sueldo,direccion,turno,codigoCargoEmpleado);
	end $$
Delimiter ;
call Sp_AgregarEmpleados (1, 'Josue David', 'Garcia Mendez', 1500, '2 calle Mexico', 'primero', 1);
call Sp_AgregarEmpleados (2, 'Marciano Gabrie;', 'Gonzales Oscal;', 2000, ' 3calle la limonada', 'segundo', 2);

--    codigoEmpleado int not null,
--    nombresEmpleado varchar(50),
--    apellidosEmpleado varchar(50),
--    sueldo decimal(10,2),
--    direccion varchar(150),
--    turno varchar(15),
--    codigoCargoEmpleado int,

-- Listar 
Delimiter $$
create procedure sp_ListarEmpleados()
	begin	
		select 
			E.codigoEmpleado,
			E.nombresEmpleado,
			E.apellidosEmpleado,
            E.sueldo,
            E.direccion,
            E.turno,
            E.codigoCargoEmpleado
            
          
			from Empleados E;
	end $$
Delimiter ;
call sp_ListarEmpleados();

-- Buscar
Delimiter $$
	create procedure sp_BuscarEmpleados (in codEm int)
    begin 
			select 
			E.codigoEmpleado,
			E.nombresEmpleado,
			E.apellidosEmpleado,
            E.sueldo,
            E.direccion,
            E.turno,
            E.codigoCargoEmpleado
          
			from Empleados E
		where codigoEmpleado = codEm;
	end $$
delimiter ;
 call sp_BuscarEmpleados(1);
 
 
-- eliminar 
 Delimiter $$
	create procedure sp_EliminarEmpleados(in codEm int)
		begin 
			delete from Empleados
            where codigoEmpleado = codEm;
        end $$
delimiter ; 
-- call sp_EliminarEmpleados(1);
call sp_ListarEmpleados();

-- editar 
Delimiter $$
	create procedure sp_EditarEmpleados(in codigoE int, in nombresE varchar(50), in apellidosE varchar(50), in sd decimal(10,2), in dire varchar(150), in turn varchar(15))
			begin 
				update Empleados E
					set
						E.nombresEmpleado = nombresE,
						E.apellidosEmpleado = apellidosE,
						E.sueldo = sd,
						E.direccion = dire,
                        E.turno = turn

			
			where codigoEmpleado = codigoE;
			end $$
delimiter ;                 
 
call sp_EditarEmpleados(1,'Jose Mario', 'Gonzales Gutierrez' , 15.30, '4Calle Mixco', 'Tercer turno');
call sp_ListarEmpleados();




-- -----------------------------Procedimiento Almacenado---------------------------------
-- -------- DetalleCompra
-- Agregar 
Delimiter $$
create procedure Sp_AgregarDetalleCompra (in codigoDetalleCompra int, in costoUnitario decimal(10,2), in cantidad int, in codigoProducto varchar(15), in numeroDocumento int)
	begin 
		insert into DetalleCompra (codigoDetalleCompra,costoUnitario,cantidad,codigoProducto,numeroDocumento) values (codigoDetalleCompra,costoUnitario,cantidad,codigoProducto,numeroDocumento);
	end $$
Delimiter ;
call Sp_AgregarDetalleCompra (1, 30.20, 30,  'E5', 1);
call Sp_AgregarDetalleCompra (2, 28.96, 40,  'E6', 2);

-- 	codigoDetalleCompra int not null,
-- 	costoUnitario decimal(10,2),
--  cantidad int not null,
--  codigoProducto varchar(15),
--  numeroDocumento int,

-- Listar 
Delimiter $$
create procedure sp_ListarDetalleCompra()
	begin	
		select 
			D.codigoDetalleCompra,
			D.costoUnitario,
			D.cantidad,
            D.codigoProducto,
            D.numeroDocumento
            
          
			from DetalleCompra D;
	end $$
Delimiter ;
call sp_ListarDetalleCompra();

-- Buscar
Delimiter $$
	create procedure sp_BuscarDetalleCompra (in codD int)
    begin 
			select 
			D.codigoDetalleCompra,
			D.costoUnitario,
			D.cantidad,
            D.codigoProducto,
            D.numeroDocumento
          
			from DetalleCompra D
		where codigoDetalleCompra = codD;
	end $$
delimiter ;
 call sp_BuscarDetalleCompra(1);
 
 
-- eliminar 
 Delimiter $$
	create procedure sp_EliminarDetalleCompra(in codD int)
		begin 
			delete from DetalleCompra
            where codigoDetalleCompra = codD;
        end $$
delimiter ; 
-- call sp_EliminarDetalleCompra(2);
call sp_ListarDetalleCompra();

-- editar 
Delimiter $$
	create procedure sp_EditarDetalleCompra(in codigoD int, in costoU decimal(10,2), in cant int)
			begin 
				update DetalleCompra D
					set
						D.costoUnitario = costoU,
						D.cantidad = cant

			
			where codigoDetalleCompra = codigoD;
			end $$
delimiter ;                 
 
call sp_EditarDetalleCompra(2, 102.85, 200);
call sp_ListarDetalleCompra();



-- -----------------------------Procedimiento Almacenado---------------------------------
-- -------- Factura
-- Agregar 
Delimiter $$
create procedure Sp_AgregarFactura (in numeroFactura int, in estado varchar(50), in totalFactura decimal(10,2), in fechaFactura varchar(45), in codigoCliente int,  in codigoEmpleado int)
	begin 
		insert into Factura (numeroFactura,estado,totalFactura,fechaFactura,codigoCliente,codigoEmpleado) values (numeroFactura,estado,totalFactura,fechaFactura,codigoCliente,codigoEmpleado);
	end $$
Delimiter ;
call Sp_AgregarFactura (1, 'Caducada', 102.39, '12-5-2025', 1, 1);
call Sp_AgregarFactura (2, 'Vencida', 52.84,' 11-4-2016', 2, 2);

-- 	  numeroFactura int not null,
--    estado varchar(50),
--    totalFactura decimal(10,2),
--    fechaFactura varchar(45),
--    codigoCliente int,
--    codigoEmpleado int,

-- Listar 
Delimiter $$
create procedure sp_ListarFactura()
	begin	
		select 
			F.numeroFactura,
			F.estado,
			F.totalFactura,
            F.fechaFactura,
            F.codigoCliente,
            F.codigoEmpleado
            
			from Factura F;
	end $$
Delimiter ;
call sp_ListarFactura();

-- Buscar
Delimiter $$
	create procedure sp_BuscarFactura (in codF int)
    begin 
			select 
			F.numeroFactura,
			F.estado,
			F.totalFactura,
            F.fechaFactura,
            F.codigoCliente,
            F.codigoEmpleado
          
			from Factura F
		where numeroFactura = codF;
	end $$
delimiter ;
 call sp_BuscarFactura(1);
 
 
-- eliminar 
 Delimiter $$
	create procedure sp_EliminarFactura(in codF int)
		begin 
			delete from Factura
            where numeroFactura = codF;
        end $$
delimiter ; 
-- call sp_EliminarFactura(1);
call sp_ListarFactura();

-- editar 
Delimiter $$
	create procedure sp_EditarFactura(in numeroF int, in es varchar(50), in totalF decimal(10,2), in fechaF varchar(45))
			begin 
				update Factura F
					set
						
						F.estado = es,
						F.totalFactura = totalF,
                        F.fechaFactura = fechaF

			
			where numeroFactura = numeroF;
			end $$
delimiter ;                 
 
call sp_EditarFactura(2,'Factura reciente', 800.50 , '12-6-2024');
call sp_ListarFactura();



-- -----------------------------Procedimiento Almacenado---------------------------------
-- -------- DetalleFactura
-- Agregar 
Delimiter $$
create procedure Sp_AgregarDetalleFactura (in codigoDetalleFactura int, in precioUnitario decimal(10,2), in cantidad int, in numeroFactura int, in codigoProducto varchar(15))
	begin 
		insert into DetalleFactura (codigoDetalleFactura,precioUnitario,cantidad,numeroFactura,codigoProducto) values (codigoDetalleFactura,precioUnitario,cantidad,numeroFactura,codigoProducto);
	end $$
Delimiter ;
call Sp_AgregarDetalleFactura (1, 10.20, 35, 1, 'E5');
call Sp_AgregarDetalleFactura (2, 11.36, 42, 2, 'E6');

-- codigoDetalleFactura int not null,
-- precioUnitario decimal(10,2),
-- cantidad int not null,
-- numeroFactura int,
-- codigoProducto varchar(15),

-- Listar 
Delimiter $$
create procedure sp_ListarDetalleFactura()
	begin	
		select 
			F.codigoDetalleFactura,
			F.precioUnitario,
			F.cantidad,
            F.numeroFactura,
            F.codigoProducto
            
			from DetalleFactura F;
	end $$
Delimiter ;
call sp_ListarDetalleFactura();

-- Buscar
Delimiter $$
	create procedure sp_BuscarDetalleFactura (in codF int)
    begin 
			select 
			F.codigoDetalleFactura,
			F.precioUnitario,
			F.cantidad,
            F.numeroFactura,
            F.codigoProducto
          
			from DetalleFactura F
		where codigoDetalleFactura = codF;
	end $$
delimiter ;
 call sp_BuscarDetalleFactura(1);
 
 
-- eliminar 
 Delimiter $$
	create procedure sp_EliminarDetalleFactura(in codF int)
		begin 
			delete from DetalleFactura
            where codigoDetalleFactura = codF;
        end $$
delimiter ; 
-- call sp_EliminarDetalleFactura(2);
call sp_ListarDetalleFactura();

-- editar 
Delimiter $$
	create procedure sp_EditarDetalleFactura(in codigoDetalleFac int, in precioUni decimal(10,2), in canti int)
			begin 
				update DetalleFactura F
					set
						
						F.precioUnitario = precioUni,
						F.cantidad = canti

			
			where codigoDetalleFactura = codigoDetalleFac;
			end $$
delimiter ;                 
 
call sp_EditarDetalleFactura(2, 100.02, 30);
call sp_ListarDetalleFactura();



