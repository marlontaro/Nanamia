using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Nanamia.Model
{
    public class CheckStatus
    {
        public string id { set; get; }
        public string status { get; set; }
        public string message { get; set; }

        public CheckStatus() {
            id = "0";
            status = Status.Error;
            message = string.Empty;
        }
    }
}
