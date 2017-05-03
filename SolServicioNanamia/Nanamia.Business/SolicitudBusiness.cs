using Nanamia.Model;
using Nanamia.Persistence;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Nanamia.Business
{
    public class SolicitudBusiness
    {
        SolicitudRepository solicitudRepository;

        public SolicitudBusiness()
        {
            solicitudRepository = new SolicitudRepository();

        }

        public CheckStatus Create(Solicitud user)
        {
            CheckStatus check = new CheckStatus();
            check = solicitudRepository.Create(user);
            return check;
        }
    }
}
