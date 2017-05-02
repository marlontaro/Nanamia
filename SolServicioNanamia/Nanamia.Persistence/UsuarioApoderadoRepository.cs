using Dapper;
using Nanamia.Model;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Nanamia.Persistence
{
    public class UsuarioApoderadoRepository : BaseRepository
    {
        public UsuarioApoderadoOutput Create(UsuarioApoderado user)
        {
        
            UsuarioApoderadoOutput login = new UsuarioApoderadoOutput();

            try
            {
                using (IDbConnection connection = OpenConnection())
                {
                    var oParams = new DynamicParameters();
                    oParams.Add("Nombre", user.nombre);
                    oParams.Add("Apellido", user.apellido);
                    oParams.Add("Celular", user.celular);

                    oParams.Add("Dni", user.dni);
                    oParams.Add("Tipo", user.tipo);
                    oParams.Add("Correo", user.correo);

                    oParams.Add("Password", user.password);

                    login = connection.Query<UsuarioApoderadoOutput>(
                               "uspUsuarioApoderadoCreate",
                               oParams,
                               commandType: CommandType.StoredProcedure).FirstOrDefault();



                    if (login == null)
                    {
                        login = new UsuarioApoderadoOutput();
                        login.mensaje = "Error al registrar usuario";
                        login.estado = Status.Error;
                    }
                    else
                    {
                        login.estado = Status.Ok;
                    }

                }

            } catch (Exception ex)
            {
                login.estado = Status.Error ;
                login.mensaje = "Error al registrar, por favor revise su datos ingresados.";
            }

            return login;

        }

        public UsuarioApoderadoOutput Login(int tipo,string usuario,string password)
        {
            UsuarioApoderadoOutput login = new UsuarioApoderadoOutput();

            using (IDbConnection connection = OpenConnection())
            {
                var oParams = new DynamicParameters();
                oParams.Add("tipo", tipo);
                oParams.Add("usuario", usuario);
                oParams.Add("password", password);

                login = connection.Query<UsuarioApoderadoOutput>(
                                "uspUsuarioApoderadoLogin",
                                oParams,
                                commandType: CommandType.StoredProcedure).FirstOrDefault();
            }

            if (login == null)
            {
                login = new UsuarioApoderadoOutput();
                login.mensaje = "Login invalido";
                login.estado = Status.Error;
            }
            else {
                login.estado = Status.Ok;
            }

            return login;
        }


        public UsuarioApoderadoOutput Token(string token)
        {
            UsuarioApoderadoOutput login = new UsuarioApoderadoOutput();

            using (IDbConnection connection = OpenConnection())
            {
                var oParams = new DynamicParameters();              
                oParams.Add("token", token);

                login = connection.Query<UsuarioApoderadoOutput>(
                                "uspTokenLogin",
                                oParams,
                                commandType: CommandType.StoredProcedure).FirstOrDefault();
            }

            if (login == null)
            {
                login = new UsuarioApoderadoOutput();
                login.mensaje = "Token invalido";
                login.estado = Status.Error;
            }
            else
            {
                login.estado = Status.Ok;
            }

            return login;
        }

    }
}
