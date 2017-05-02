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
        public CheckStatus Create(UsuarioApoderado user)
        {
            CheckStatus checkstatus = new CheckStatus();
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
                    oParams.Add("Token", dbType: DbType.String, direction: ParameterDirection.Output);

 
                    connection.Execute("uspUsuarioApoderadoCreate",
                                   oParams,
                                   commandType: CommandType.StoredProcedure);


                    checkstatus.status = Status.Ok;
                    checkstatus.id = oParams.Get<string>("Token");
                    checkstatus.message = "";
                    
                }

            } catch (Exception ex)
            {
                checkstatus.status = Status.Error ;
                checkstatus.message = "Error al registrar, por favor revise su datos ingresados.";
            }

            return checkstatus;

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
                                "uspUsuarioApoderadoLogin",
                                oParams,
                                commandType: CommandType.StoredProcedure).FirstOrDefault();
            }



            return login;
        }

    }
}
