package com.mycompany.email.service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.font.FontProvider;
import com.mycompany.email.dto.Email;
import com.mycompany.email.dto.EndPosition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.*;


/**
 * Created by shambhu.mehta on 3/10/2022
 * Description:
 */
@Slf4j
@Service
public class EmailService {
    private  final TemplateEngine templateEngine;
    private final JavaMailSender  javaMailSender;


    public EmailService(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    public  String sendMail(Email email, boolean attachPdf) throws  Exception{
        Context context= new Context();
        context.setVariable("email",email);

        String htmlText= templateEngine.process("emails/welcome.html",context);
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage,true,"UTF-8");
        mimeMessageHelper.setTo(email.getTo());
        mimeMessageHelper.setSubject("Welcome "+email.getName());
        mimeMessageHelper.setText(htmlText,true);
        if(attachPdf == true){
            //byte[] bytes = generateSinglePagePdf(htmlText);
            OutputStream  outputStream=null;
            byte[] bytes = generateSinglePagePdf(htmlText);
            mimeMessageHelper.addAttachment(email.getName()+".pdf", new ByteArrayResource(bytes));
        }
        javaMailSender.send(mimeMailMessage);
        return  "email sent to"+ email.getTo();


    }
    public byte[] getPDFContents(String content, String fileName) throws  Exception{

        //HtmlConverter.convertToPdf(new File("./input.html"),new File("output.pdf"));
        // string to pdf
        /*OutputStream fileOutputStream = new FileOutputStream("output.pdf");
        HtmlConverter.convertToPdf("<h1>Sample string</h1>", fileOutputStream);*/
        // input stream to pdf
        //HtmlConverter.convertToPdf(htmlStringStream, fileOutputStream);

        // deal with resource
        /*ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("https://springhow.com/");
        HtmlConverter.convertToPdf(new File("./input.html"), new File("output.pdf"), converterProperties);
        */
         // Deal with font
        /*ConverterProperties converterProperties = new ConverterProperties();
        FontProvider fontProvider  = new FontProvider();
        fontProvider.addFont("/system-path/new-font.ttf");
        fontProvider.addStandardPdfFonts();
        fontProvider.addSystemFonts(); // default fonts
        converterProperties.setFontProvider(fontProvider);

        HtmlConverter.convertToPdf(htmlString, outputStream, converterProperties);*/

        ConverterProperties converterProperties = new ConverterProperties();
        FontProvider fontProvider  = new FontProvider();
        fontProvider.addFont("/system-path/new-font.ttf");
        fontProvider.addStandardPdfFonts();
        fontProvider.addSystemFonts(); // default fonts
        converterProperties.setFontProvider(fontProvider);


        OutputStream fileOutputStream = new FileOutputStream(fileName);
        HtmlConverter.convertToPdf(content, fileOutputStream);
       return  fileOutputStream.toString().getBytes("UTF-8");

    }

    private byte[] generateSinglePagePdf(final String inputHtml) throws IOException, IOException {
        File tempFile = File.createTempFile("temp_file", ".pdf");
        ConverterProperties properties = new ConverterProperties();
        PdfWriter writer = new PdfWriter(tempFile);
        PdfDocument pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(new PageSize(595, 14400));
        Document document = HtmlConverter
                .convertToDocument(new ByteArrayInputStream(inputHtml.getBytes()), pdf, properties);
        EndPosition endPosition = new EndPosition();
        LineSeparator separator = new LineSeparator(endPosition);
        document.add(separator);
        document.getRenderer().close();
        PdfPage page = pdf.getPage(1);
        float y = endPosition.getLineWidth() - 36;
        page.setMediaBox(new Rectangle(0, y, 595, 14400 - y));
        document.close();
        return FileUtils.readFileToByteArray(tempFile);
    }
}
