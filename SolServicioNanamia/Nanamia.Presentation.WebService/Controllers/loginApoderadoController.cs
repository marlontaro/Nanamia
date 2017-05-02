using Nanamia.Business;
using Nanamia.Model;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Web.Http;

namespace Nanamia.Presentation.WebService.Controllers
{
    public class loginapoderadoController : ApiController
    {
        UsuarioApoderadoBusiness usuarioApoderadoBusiness;

        public loginapoderadoController() {
            usuarioApoderadoBusiness = new UsuarioApoderadoBusiness();
        }

        [HttpGet]
        public HttpResponseMessage Get(string token)
        {
            UsuarioApoderadoOutput login = new UsuarioApoderadoOutput();
            login = usuarioApoderadoBusiness.Token(token);

            return new HttpResponseMessage(HttpStatusCode.OK)
            {
                Content = new StringContent(JObject.FromObject(login).ToString(), Encoding.UTF8, "application/json")
            };
        }

        [HttpGet]
        public HttpResponseMessage Get(int tipo, string usuario, string password)
        {
            UsuarioApoderadoOutput login = new UsuarioApoderadoOutput();
            login = usuarioApoderadoBusiness.Login(tipo, usuario, password);
            
            return new HttpResponseMessage(HttpStatusCode.OK)
            {
                Content = new StringContent(JObject.FromObject(login).ToString(), Encoding.UTF8, "application/json")
            };
        }
    }
}
