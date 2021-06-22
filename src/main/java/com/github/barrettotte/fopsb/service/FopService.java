package com.github.barrettotte.fopsb.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.barrettotte.fopsb.model.User;
import com.github.barrettotte.fopsb.utils.FileUtils;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FopService {

    private File reposXslt;

    @Autowired
    FopFactory fopFactory;

    @Autowired
    TransformerFactory transformerFactory;

    public FopService() {}

    public FopService(final File reposXslt) throws IOException {
        this.reposXslt = reposXslt;
    }

    public void setReposXslt(final File f) {
        this.reposXslt = f;
    }

    // run with local test XML string
    public ByteArrayInputStream generateTestReport() 
        throws IOException, FOPException, TransformerConfigurationException, TransformerException 
    {
        return generateTestReport(FileUtils.getResourceFileAsString("data/test_data.xml"));
    }

    // run with specific test XML string
    public ByteArrayInputStream generateTestReport(final String testXml)
        throws FOPException, TransformerConfigurationException, TransformerException
    {
        return applyXslt(new StreamSource(new StringReader(testXml)), new StreamSource(this.reposXslt));
    }

    // generate PDF bytes for GitHub user
    public ByteArrayInputStream generateUserReport(final User user) 
        throws JsonProcessingException, FOPException, TransformerConfigurationException, TransformerException
    {
        final XmlMapper xmlMapper = new XmlMapper();
        final String userXml = xmlMapper.writeValueAsString(user);
        return applyXslt(new StreamSource(new StringReader(userXml)), new StreamSource(this.reposXslt));
    }

    // apply XSLT to XML via FOP and return PDF stream
    private ByteArrayInputStream applyXslt(final StreamSource xml, final StreamSource xslt)
        throws FOPException, TransformerConfigurationException, TransformerException
    {
        final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        final FOUserAgent agent = fopFactory.newFOUserAgent();
        final Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, agent, outStream);

        final Transformer transformer = transformerFactory.newTransformer(xslt);
        transformer.transform(xml, new SAXResult(fop.getDefaultHandler()));
        return new ByteArrayInputStream(outStream.toByteArray());
    }
}
