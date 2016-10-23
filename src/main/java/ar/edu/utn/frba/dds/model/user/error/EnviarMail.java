package ar.edu.utn.frba.dds.model.user.error;

import java.util.List;

import ar.edu.utn.frba.dds.model.accion.Accion;
import ar.edu.utn.frba.dds.model.user.Usuario;
import ar.edu.utn.frba.dds.util.mail.MailSender;

public class EnviarMail extends ErrorHandler {

    @Override
    public boolean handle(Usuario usuario, Accion accion, List<Integer> params) {
        MailSender mailSender = new MailSender();
        String to = usuario.getEmail();
        String subject = "Error en proceso:"+accion.getNombre();
        String body = "Se ha producido un error en la ejecución del proceso";
        mailSender.sendMail(to, subject, body, false);
        return false;
    }

}
