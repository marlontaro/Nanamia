using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Nanamia.Model
{
    public static class StatusToken
    {
        public static int Ok = 1;
        public static int NotFound = 0;
        public static int Error = -1;
    }

    public static class TypeToken {
        public static int Both = 0; //ambos
        public static int Customer = 1; //Cliente app
        public static int Expert = 2; //Expert app
    }
}
