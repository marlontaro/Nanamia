alter proc uspUsuarioApoderadoCreate
@Nombre varchar(500), 
@Apellido varchar(500), 
@Celular varchar(10),
@Dni varchar(10),
@Tipo int,
@Correo varchar(50),
@Password varchar(50)
as
declare @IdPersona int
declare @IdUsuario int
declare @Token varchar(50) 
set @IdUsuario =0 
if(@Tipo=2)
begin
	set @IdUsuario = isnull((select IdUsuario from Usuario
						where [Usuario]= @Correo and Tipo = 2),0)
end

if(@IdUsuario=0)
begin

	insert into [dbo].[Persona](Nombre, Apellido, FechaNacimiento, DNI, Celular, Estado)
	values(@Nombre,@Apellido,null,@Dni,'',1)
	set @IdPersona = SCOPE_IDENTITY();

	insert into [dbo].[Apoderado](IdPersona, Direccion, Longitud, Latitud)
	values(@IdPersona,'','','')


	set @Token= convert(varchar(100), NEWID())
	if(@Tipo = 1)
	begin
		insert [dbo].[Usuario](Usuario, [Password], SocialToken, Token, Tipo, TipoApp, Estado, IdPersona)
		values(@Correo,@Password,'',@Token,@Tipo,1,1,@IdPersona)
		set @IdUsuario = scope_identity()
	end 
	else
	begin
		insert [dbo].[Usuario](Usuario, [Password], SocialToken, Token, Tipo, TipoApp, Estado, IdPersona)
		values(@Correo,'',@Password,@Token,@Tipo,1,1,@IdPersona)
		set @IdUsuario = scope_identity()
	end
end


select 
per.Nombre,
per.Apellido,
per.Dni,
per.Celular,
usu.Usuario as Correo,
usu.Token
from Usuario usu
inner join Persona per
on usu.IdPersona = per.IdPersona
where usu.IdUsuario = @IdUsuario and
		usu.Estado = 1
go

alter proc uspUsuarioApoderadoLogin
@tipo int,
@usuario varchar(50),
@password varchar(50)
as
declare @IdUsuario int = 0
declare @Token varchar(50) = ''
if(@tipo = 1)
begin
	set @IdUsuario = isnull((select IdUsuario from [dbo].[Usuario]
						where Estado = 1 and Usuario = @usuario and
								[Password] = @password),0)
end
else
begin
	set @IdUsuario = isnull((select IdUsuario from [dbo].[Usuario]
						where  Estado = 1 and Usuario = @usuario and
								[Password] = @password),0)
end

set @Token = convert(varchar(100), NEWID())
update [dbo].[Usuario] set
Token= @Token
where IdUsuario = @IdUsuario


select 
per.Nombre,
per.Apellido,
per.Dni,
per.Celular,
usu.Usuario as Correo,
usu.Token
from Usuario usu
inner join Persona per
on usu.IdPersona = per.IdPersona
where usu.IdUsuario = @IdUsuario and
		usu.Estado = 1

go

alter proc uspTokenLogin
@token varchar(500)
as
declare @IdUsuario int 

set @IdUsuario= isnull(	( select IdUsuario from [dbo].[Usuario] 
				where token = @token and estado = 1),0)
select 
per.Nombre,
per.Apellido,
per.Dni,
per.Celular,
usu.Usuario as Correo,
usu.Token
from Usuario usu
inner join Persona per
on usu.IdPersona = per.IdPersona
where usu.IdUsuario = @IdUsuario and
		usu.Estado = 1
go

alter proc uspNanaQuery
as
select 
nan.IdPersona as Id,
per.Nombre,
per.Apellido,
(year(getdate())-year(per.FechaNacimiento)) as Edad,
nan.Puntaje,
nan.[Experiencia],
nan.Avatar,
nan.[SalarioMinimo],
nan.[SalarioMaximo],
nan.[PrimerosAuxilio],
nan.[CuidadoEspecial]
from [dbo].[Nana] nan
inner join Persona per
on nan.IdPersona = per.IdPersona
go

alter proc uspGetToken
@token varchar(100),
@idPersona int output
as
set @idPersona = isnull((
					select IdPersona from [dbo].[Usuario]
					where token = @token),0)
go
 
alter proc uspSolicitudCreate
@TipoContrato int, 
@PrimerosAuxilio bit, 
@CuidadoEspecial bit, 
@SueldoMinimo decimal(10,2), 
@SueldoMaximo decimal(10,2), 
@Direccion varchar(500), 
@Longitud varchar(500), 
@Latitud varchar(500), 
@Token varchar(100),
@IdNana int

as
declare @IdSolicitud int 
declare @IdPersona int 
set @IdSolicitud= 0
set @IdPersona = 0
set @IdPersona = isnull((
					select IdPersona from [dbo].[Usuario]
					where token = @token),0)
insert into [dbo].[Solicitud](
FechaCreacion, TipoContrato, PrimerosAuxilio, 
CuidadoEspecial, SueldoMinimo, SueldoMaximo,
 Direccion, Longitud, Latitud, Estado, IdApoderado, IdNana)
 values(getdate(), @TipoContrato, @PrimerosAuxilio, 
@CuidadoEspecial , @SueldoMinimo , @SueldoMaximo ,
 @Direccion, @Longitud , @Latitud , 1,@IdPersona ,@IdNana)

 set @IdSolicitud= SCOPE_IDENTITY()

 select @IdSolicitud