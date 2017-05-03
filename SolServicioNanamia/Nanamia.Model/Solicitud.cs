using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Nanamia.Model
{
    public class Solicitud
    {
        public int TipoContrato { get; set; }
        public bool PrimerosAuxilio { get; set; }
        public bool CuidadoEspecial { get; set; }
        public decimal SueldoMinimo { get; set; }
        public decimal SueldoMaximo { get; set; }
        public string Direccion { get; set; }
        public string Longitud { get; set; }
        public string Latitud { get; set; } 
        public int IdNana { get; set; }
        public string Token { get; set; }
    }
}
