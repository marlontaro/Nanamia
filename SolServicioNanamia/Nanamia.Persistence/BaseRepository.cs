using System.Configuration;
using System.Data;
using System.Data.SqlClient;

namespace Nanamia.Persistence
{
    public abstract class BaseRepository
    {
        public static string cCadenaConexion { get; set; }
     

        public BaseRepository()
        {
            cCadenaConexion = ConfigurationManager.ConnectionStrings["cnNanaMia"].ConnectionString;           
        }

        protected static IDbConnection OpenConnection()
        {
            IDbConnection connection = new SqlConnection(cCadenaConexion);
            connection.Open();
            return connection;
        }

       
        
    }
}