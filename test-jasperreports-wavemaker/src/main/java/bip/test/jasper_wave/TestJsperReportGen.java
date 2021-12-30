package bip.test.jasper_wave;

import javax.servlet.http.*;

import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
//import com.wavemaker.runtime.WMAppContext; // used to load beans from app context

//import com.wavemaker.runtime.security.SecurityService;
//import com.wavemaker.runtime.service.annotations.ExposeToClient;
//import com.wavemaker.runtime.service.annotations.HideFromClient;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * Created by ramezani on 11/10/2019.
 */
public class TestJsperReportGen {
    Logger logger = LoggerFactory.getLogger(TestJsperReportGen.class);

    public static void main(String[] args) throws Exception{
        //File f= new File("e:/jasper1.pdf");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new TestJsperReportGen().generatePdfReport("report1.jrxml",outputStream);

        File f= new File("e:/jasper1.html");
        FileOutputStream fout = new FileOutputStream(f);
        fout.write(outputStream.toByteArray());
        fout.close();

        ByteArrayOutputStream a;
        //a.size()

        Runtime.getRuntime().exec(new String[]{"cmd","/c",f.getAbsolutePath()});
    }

    public void generatePdfReport(String jrxml,OutputStream outputStream)
    {
        Connection conn = null;
        try
        {
            //Fetching database connection from spring bean
            //DataSource ds = null;//(DataSource)WMAppContext.getInstance().getSpringBean(database + "DataSource");
            //conn = ds.getConnection(); // get connected to database
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.88.2:1521:orcl","sepehr","sepehr");

            //Opening jrxml input stream file from class loader
            InputStream jrxmlInput = getClass().getClassLoader().getResource(jrxml).openStream();

            // loads the jrxml file
            JasperDesign design = JRXmlLoader.load(jrxmlInput);

            File destFile=new File("e:/report1.jasper");

            JasperReport jasperReport=null;
            if(destFile.exists()){
                jasperReport = (JasperReport) JRLoader.loadObject(destFile);
            }else{
                jasperReport = JasperCompileManager.compileReport(design);
                JRSaver.saveObject(jasperReport, destFile);
            }
            //Compiling jrxml file

            //Print jasper report
            Map<String,Object> parameters = new HashMap();
            parameters.put("startDate",new Date());
            parameters.put("officeId",100);
            parameters.put("name","ali");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters , conn);
            logger.info("JasperPrint" + jasperPrint);

            //Export report to pdf format
/*
            JRPdfExporter pdfExporter = new JRPdfExporter();
            pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream();
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfReportStream));
            pdfExporter.exportReport();
            outputStream.write(pdfReportStream.toByteArray());
            outputStream.close();
            pdfReportStream.close();
*/



            HtmlExporter exporter = new HtmlExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(outputStream);
            output.setImageHandler(new WebHtmlResourceHandler("image?image={0}"));
            exporter.setExporterOutput(output);
            exporter.exportReport();
            outputStream.close();


            //Setting response header
//            response.setContentType("application/pdf");
//            response.setHeader("Content-Length", String.valueOf(pdfReportStream.size()));
//            response.addHeader("Content-Disposition", "inline; filename=jasper.pdf;");

            //Closing stream
            //OutputStream responseOutputStream = response.getOutputStream();
            logger.info("Completed Successfully: "); // logger will log the error into the studio logs
        } catch (Exception e) {
            logger.info("Error: ", e);
        } finally {
            if (conn==null) {
                //closing database connection
                try {
                    conn.close();
                }catch(Exception ex){}
            }
        }
    }
}
