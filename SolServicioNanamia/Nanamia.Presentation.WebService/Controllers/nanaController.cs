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
    public class nanaController : ApiController
    {
        NanaBusiness nanaBusiness;
        public nanaController()
        {
            nanaBusiness = new NanaBusiness();
        }

        [HttpGet]
        public HttpResponseMessage Get()
        {
            IList<Nana> list = new List<Nana>();
            list = nanaBusiness.Query();
            return new HttpResponseMessage(HttpStatusCode.Created)
            {
                Content = new StringContent(JArray.FromObject(list).ToString(), Encoding.UTF8, "application/json")
            };
        }
    }
}
