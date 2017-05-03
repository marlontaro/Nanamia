using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Nanamia.Presentation.WebService.Controllers
{
    public class nanaController : ApiController
    {
        public nanaController()
        {
            usuarioApoderadoBusiness = new UsuarioApoderadoBusiness();
        }

        [HttpGet]
        public HttpResponseMessage Get()
        {
            
        }
    }
}
