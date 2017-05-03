using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Nanamia.Model
{
    public class Nana
    {
        public int Id { get; set; }
        public string Nombre { get; set; }
        public string Apellido { get; set; }
        public int Edad { get; set; }
        public decimal Puntaje { get; set; }
        public string Experiencia { get; set; }
        public string Avatar { get; set; }
        public decimal SalarioMinimo { get; set; }
        public decimal SalarioMaximo { get; set; }
        public bool PrimerosAuxilio { get; set; }
        public bool CuidadoEspecial { get; set; }
    }
}
