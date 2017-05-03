delete from [dbo].[Usuario]
delete from [dbo].[Nana]
delete from [dbo].[Apoderado]
delete from [dbo].[Persona]
go

declare @IdPerson int
declare @Fecha datetime
set @Fecha = Cast('7/7/1975' as datetime)
insert into [dbo].[Persona](Nombre, Apellido, FechaNacimiento, DNI, Celular, Estado)
values('Mary','Poppins', @Fecha, '54216766','',1 )
set @IdPerson = SCOPE_IDENTITY()

insert into [dbo].[Nana](IdPersona, Puntaje, Experiencia, SalarioMinimo, SalarioMaximo, 
					Distrito, PrimerosAuxilio, CuidadoEspecial)
values(@IdPerson, 4.6,'Mas de 20 años cuidando nenes.',1200,1500,'Barranco',1,1)

set @IdPerson =0
set @Fecha = Cast('7/7/1980' as datetime)
insert into [dbo].[Persona](Nombre, Apellido, FechaNacimiento, DNI, Celular, Estado)
values('Mary','Poppins', @Fecha, '54216766','',1 )
set @IdPerson = SCOPE_IDENTITY()

insert into [dbo].[Nana](IdPersona, Puntaje, Experiencia, SalarioMinimo, SalarioMaximo, 
					Distrito, PrimerosAuxilio, CuidadoEspecial)
values(@IdPerson, 4.6,'Mas de 20 años cuidando nenes.',1200,1500,'Barranco',1,1)
go

select * from [dbo].[Usuario]
select * from [dbo].[Apoderado]
select * from [dbo].[Persona]
select * from [dbo].[Nana]
go