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
    public class NanaRepository : BaseRepository
    {
        public IList<Nana> Query()
        {
            IList<Nana> list = new List<Nana>();
            using (IDbConnection connection = OpenConnection())
            {
                list = connection.Query<Nana>(
                           "uspNanaQuery",
                           commandType: CommandType.StoredProcedure).ToList();

                foreach (Nana nana in list) {
                    nana.Avatar = @"http://nanamia-001-site1.etempurl.com/image/" + nana.Avatar;
                }
                return list;
            }
        }
    }
}
