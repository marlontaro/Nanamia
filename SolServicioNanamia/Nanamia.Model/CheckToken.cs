using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Nanamia.Model
{
    public class CheckToken
    {
        public string token { set; get; }
        public string status { get; set; }
        public string message { get; set; }

        public CheckToken()
        {
            token = "";
            status = Status.Error;
            message = string.Empty;
        }
    }
}
