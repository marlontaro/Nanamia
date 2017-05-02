using Nanamia.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Nanamia.Presentation.WebService.Helper
{
    public class MessageError
    {
        public string estado { get; set; }
        public string mensaje { get; set; }
        public string fecha { get; set; }

        public MessageError()
        {
            this.estado = Status.Error;
            this.mensaje = "Metodo no implementado";
            this.fecha = DateTime.Now.ToString("dd/MM/yyyy hh:mm:ss");
        }

        public MessageError(string message)
        {
            this.estado = Status.Error;
            this.mensaje = message;
            fecha = DateTime.Now.ToString("dd/MM/yyyy hh:mm:ss");
        }
    }
}