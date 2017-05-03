using Nanamia.Business;
using Nanamia.Model;
using Nanamia.Presentation.WebService.Helper;
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
    public class usuarioapoderadoController : ApiController
    {
        UsuarioApoderadoBusiness usuarioApoderadoBusiness;

        public usuarioapoderadoController()
        {
            usuarioApoderadoBusiness = new UsuarioApoderadoBusiness();

        }
         
        [HttpGet]
        public HttpResponseMessage Get()
        {
            return new HttpResponseMessage(HttpStatusCode.BadRequest)
            {
                Content = new StringContent(JObject.FromObject(new MessageError()).ToString(), Encoding.UTF8, "application/json")
            };
        }
 
        [HttpPost]
        public HttpResponseMessage Post(UsuarioApoderado user)
        {

            UsuarioApoderadoOutput checkStatus = new UsuarioApoderadoOutput();
            if (ModelState.IsValid)
            {
                try
                {
                    checkStatus = usuarioApoderadoBusiness.Create(user);

                    HttpResponseMessage response = new HttpResponseMessage(HttpStatusCode.Created)
                    {
                        Content = new StringContent(JObject.FromObject(checkStatus).ToString(), Encoding.UTF8, "application/json")
                    };
                                         
                    return response;
                }
                catch
                {
                    return new HttpResponseMessage(HttpStatusCode.BadRequest)
                    {
                        Content = new StringContent(JObject.FromObject(new MessageError("")).ToString(),
                                        Encoding.UTF8, "application/json")
                    };
                }
            }
            else
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest)
                {
                    Content = new StringContent(JObject.FromObject(ModelState).ToString(), Encoding.UTF8, "application/json")
                };
            }
        }

    }
}
