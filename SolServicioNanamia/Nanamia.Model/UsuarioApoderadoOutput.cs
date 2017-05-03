using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Nanamia.Model
{
    public class UsuarioApoderadoOutput:DataInd
    {
        public string nombre { get; set; }
        public string apellido { get; set; }
        public string dni { get; set; }
        public string celular { get; set; }
        public string correo { get; set; }
        public string token { get; set; }
        public string avatar { get; set; }

        public UsuarioApoderadoOutput() {
            nombre = String.Empty;
            apellido = String.Empty;
            dni = String.Empty;
            celular = String.Empty;
            correo = String.Empty;
            token = String.Empty;
            mensaje = String.Empty;
            estado = Status.Error;
            avatar = @"http://nanamia-001-site1.etempurl.com/image/defaultuser.png";
        }
    }
}
