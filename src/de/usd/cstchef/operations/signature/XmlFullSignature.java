package de.usd.cstchef.operations.signature;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import de.usd.cstchef.operations.OperationCategory;
import de.usd.cstchef.operations.Operation.OperationInfos;

@OperationInfos(name = "Xml Full Signature", category = OperationCategory.SIGNATURE, description = "Create a XML signature over the whole document.")
public class XmlFullSignature extends XmlSignature {

    public XmlFullSignature() {
      super();
    }

	protected byte[] perform(byte[] input) throws Exception {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(input));

      this.createSignature(doc);

      DOMSource source = new DOMSource(doc);
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      StreamResult result = new StreamResult(bos);
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.transform(source, result);
      return bos.toByteArray();
	}

}
