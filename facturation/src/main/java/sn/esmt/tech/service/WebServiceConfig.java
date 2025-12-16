package sn.esmt.tech.service;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


@EnableWs
@Configuration
public class WebServiceConfig {

    // 1️ Servlet SOAP
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
        ApplicationContext applicationContext) {

        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);

        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    // 2️ WSDL exposé automatiquement
    @Bean(name = "invoice")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema invoiceSchema) {

        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("InvoicePort");
        wsdl.setLocationUri("/ws");
        wsdl.setTargetNamespace("http://sn.esmt.tech/invoice");
        wsdl.setSchema(invoiceSchema);

        return wsdl;
    }

    // 3️ Chargement du XSD
    @Bean
    public XsdSchema invoiceSchema() {
        return new SimpleXsdSchema(
            new ClassPathResource("schema/invoice.xsd")
        );
    }
}
