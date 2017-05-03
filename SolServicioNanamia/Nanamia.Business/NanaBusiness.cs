using Nanamia.Model;
using Nanamia.Persistence;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Nanamia.Business
{
    public class NanaBusiness
    {
        NanaRepository nanaRepository;

        public NanaBusiness()
        {
            nanaRepository = new NanaRepository();

        }

        public IList<Nana> Query()
        {
            IList<Nana> list = new List<Nana>();
            list = nanaRepository.Query();
            return list;
        }
    }
}