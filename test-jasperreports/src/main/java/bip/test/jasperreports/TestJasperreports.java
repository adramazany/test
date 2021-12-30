package bip.test.jasperreports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ramezani on 11/15/2018.
 */
public class TestJasperreports {

    public static void main(String[] args) throws JRException {
        InputStream employeeReportStream
                = TestJasperreports.class.getResourceAsStream("/test.jrxml");
        JasperReport jasperReport
                = JasperCompileManager.compileReport(employeeReportStream);
        //JRSaver.saveObject(jasperReport, "test.jasper");


        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("title", "Employee Report");
        parameters.put("minSalary", 15000.0);
        parameters.put("condition", " LAST_NAME ='Smith' ORDER BY FIRST_NAME");

        JasperPrint jasperPrint
                = JasperFillManager.fillReport(jasperReport,parameters);


        JRXlsxExporter exporter = new JRXlsxExporter();
// Set input and output ...
        SimpleXlsxReportConfiguration reportConfig
                = new SimpleXlsxReportConfiguration();
        reportConfig.setSheetNames(new String[] { "Employee Data" });

        exporter.setConfiguration(reportConfig);
        exporter.exportReport();
    }
}
