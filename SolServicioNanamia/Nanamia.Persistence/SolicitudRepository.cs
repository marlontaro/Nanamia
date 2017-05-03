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
    public class SolicitudRepository : BaseRepository
    {
        public CheckStatus Create(Solicitud oSolicitud) {
            CheckStatus check = new CheckStatus();
            int id = 0;
            try
            {
                using (IDbConnection connection = OpenConnection())
                {
                    
                    var oParams = new DynamicParameters();
                    oParams.Add("TipoContrato", oSolicitud.TipoContrato);
                    oParams.Add("PrimerosAuxilio", oSolicitud.PrimerosAuxilio);
                    oParams.Add("CuidadoEspecial", oSolicitud.CuidadoEspecial);

                    oParams.Add("SueldoMinimo", oSolicitud.SueldoMinimo);
                    oParams.Add("SueldoMaximo", oSolicitud.SueldoMaximo);
                    oParams.Add("Direccion", oSolicitud.Direccion);


                    oParams.Add("Longitud", oSolicitud.Longitud);
                    oParams.Add("Latitud", oSolicitud.Latitud);
                    oParams.Add("Token", oSolicitud.Token);
                    oParams.Add("IdNana", oSolicitud.IdNana);

                    id =connection.Query<int>(
                               "uspSolicitudCreate",
                               oParams,
                               commandType: CommandType.StoredProcedure).FirstOrDefault();
                        }

                check.status = Status.Ok;
                check.id = id.ToString();


            }
            catch (Exception ex)
            {
                check.status = Status.Error;
                check.message = "Error al registrar, por favor revise su datos ingresados.";
            }

            return check;
        }
    }
}
