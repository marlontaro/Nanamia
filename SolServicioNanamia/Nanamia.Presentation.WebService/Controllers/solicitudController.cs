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
    public class solicitudController : ApiController
    {
        SolicitudBusiness usuarioApoderadoBusiness;

        public solicitudController()
        {
            usuarioApoderadoBusiness = new SolicitudBusiness();

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
        public HttpResponseMessage Post(Solicitud oSolicitud)
        {

            CheckStatus check = new CheckStatus();
            if (ModelState.IsValid)
            {
                try
                {
                    check = usuarioApoderadoBusiness.Create(oSolicitud);

                    HttpResponseMessage response = new HttpResponseMessage(HttpStatusCode.Created)
                    {
                        Content = new StringContent(JObject.FromObject(check).ToString(), Encoding.UTF8, "application/json")
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
