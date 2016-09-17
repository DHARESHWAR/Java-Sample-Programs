import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * Created by ganeshdhareshwar on 24/07/16.
 */
public class emailSend {
    public static void main(String[] args) throws AddressException {

        String TO="user1@gmail.com,user2@gmail.com";
        String CC ="pqr@gmail.com";
        String BCC = "stu@gmail.com";
        final String from="no_reply@gmail.com";
        final String password="**********";

        //2) Create session object
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from,password);
                    }
                });

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(TO));
            message.addRecipients(Message.RecipientType.BCC,InternetAddress.parse(BCC));
            message.addRecipients(Message.RecipientType.CC,InternetAddress.parse(CC));

            message.setSubject("Auto Mail");

            //3) create BodyPart object for text
            BodyPart messageBodyPartText = new MimeBodyPart();
            messageBodyPartText.setText("This is mail sent from machine. Don't reply to this mail");

            //4) create MimeBodyPart object for Excel file
            MimeBodyPart messageBodyPartExcel = new MimeBodyPart();
            String filenameExcel = "testReadStudents.xlsx";
            DataSource source = new FileDataSource(filenameExcel);
            messageBodyPartExcel.setDataHandler(new DataHandler(source));
            messageBodyPartExcel.setFileName("Excel File");

            //5) create MimeBodyPart object for PDF file
            MimeBodyPart messageBodyPartPdf = new MimeBodyPart();
            String filenamePdf="/Users/Ganesh/Documents/demo.pdf";
            DataSource sourcePdf = new FileDataSource(filenamePdf);
            messageBodyPartPdf.setDataHandler(new DataHandler(sourcePdf));
            messageBodyPartPdf.setFileName("Pdf File");

            //6) create Multipart object and add MimeBodyPart objects to this object
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPartText);
            multipart.addBodyPart(messageBodyPartExcel);
            multipart.addBodyPart(messageBodyPartPdf);

            //7) set the multipart object to the message object
            message.setContent(multipart);

            //8) send message
            Transport.send(message);

            System.out.println("Mail sent successfully");
        }catch (MessagingException ex) {ex.printStackTrace();}
    }



}

