using Nanamia.Model;
using Nanamia.Persistence;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Nanamia.Business
{
    public class UsuarioApoderadoBusiness
    {
        UsuarioApoderadoRepository usuarioApoderadoRepository;

        public UsuarioApoderadoBusiness()
        {
            usuarioApoderadoRepository = new UsuarioApoderadoRepository();
            
        }

        public UsuarioApoderadoOutput Create(UsuarioApoderado user)
        {
            UsuarioApoderadoOutput login = new UsuarioApoderadoOutput();
            login = usuarioApoderadoRepository.Create(user);
            return login;
        }

        public UsuarioApoderadoOutput Login(int tipo, string usuario, string password)
        {
            UsuarioApoderadoOutput login = new UsuarioApoderadoOutput();
            login = usuarioApoderadoRepository.Login(tipo,usuario,password);
            return login;
        }

        public UsuarioApoderadoOutput Token(string token)
        {
            UsuarioApoderadoOutput login = new UsuarioApoderadoOutput();
            login = usuarioApoderadoRepository.Token(token);
            return login;
        }

    }
}
