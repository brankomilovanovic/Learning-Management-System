package rs.ac.singidunum.novisad.isa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mail")
public class EmailSenderController {
	
    @Autowired
    private JavaMailSender mailSender;

	@GetMapping("/{toEmail}/{subject}/{body}")
    public void sendEmail(@PathVariable("toEmail") String toEmail, @PathVariable("subject") String subject, @PathVariable("body") String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("learningmanagementsystem2022@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
