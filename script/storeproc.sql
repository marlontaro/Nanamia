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
(year(getdate())-year(per.FechaNacimiento)) as edad
from [dbo].[Nana] nan
inner join Persona per
on nan.IdPersona = per.IdPersona