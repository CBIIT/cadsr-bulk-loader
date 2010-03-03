import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.xerces.dom.AttrImpl;
import org.dom4j.io.SAXReader;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestUtil {

	private static final String TEMPLATE_FILE = "C:\\work\\code\\cadsrbulkloader\\src\\test\\data\\gov\\nih\\nci\\ncicb\\cadsr\\test_template.xls";
	
	private List<String> decIds = new ArrayList<String>();
	private List<String> vdIds = new ArrayList<String>();
	private List<String> cdIds = new ArrayList<String>();
	private List<String> conIds = new ArrayList<String>();
	private List<String> dataTypes = new ArrayList<String>();
	
	private Map<String, String> cdMap = new HashMap<String, String>();
	private Map<String, String> vdMap = new HashMap<String, String>();
	private Map<String, String> decMap = new HashMap<String, String>();
	
	public Document readXMLInput(File _xmlFile) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new FileInputStream(_xmlFile));
			
			return doc;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public File createExcelFile(String _newFilePath) {
		try {
			File newFile = new File(_newFilePath);
			File templateFile = new File (TEMPLATE_FILE);
			InputStream in = new FileInputStream(templateFile);
			OutputStream out = new FileOutputStream(newFile);
			
			int read = 0;
			while ((read = in.read()) != -1) {
				out.write(read);
			}
			
			out.flush();
			out.close();
			in.close();
			
			return newFile;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void extractToExcel(File _xcelFile, Document doc) {
		try {
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(_xcelFile));
			
			NodeList cdeIds = doc.getElementsByTagName("ns1:Question");
			List<Node[]> cdeNodes = new ArrayList<Node[]>();
			List<Node[]> decNodes = new ArrayList<Node[]>();
			List<Node[]> vdNodes = new ArrayList<Node[]>();
			List<Node[]> cdNodes = new ArrayList<Node[]>();
			List<Node[]> conNodes = new ArrayList<Node[]>();
			
			for (int i=0;i<cdeIds.getLength();i++) {
				Node node = cdeIds.item(i);
				NodeList quesNodes = node.getChildNodes();
				Node[] cdeNodeArr = null;
				Node[] decNodeArr = null;
				Node[] vdNodeArr = null;
				Node[] cdNodeArr = null;
				Node[] conNodeArr = null;
				
				for (int j=0;j<quesNodes.getLength();j++) {
					Node quesNode = quesNodes.item(j);
					String quesNodeName = quesNode.getNodeName();
					
					if (quesNodeName.equalsIgnoreCase("ns1:cdeId")) {
						if (cdeNodeArr == null) {
							cdeNodeArr = new Node[4];
							cdeNodes.add(cdeNodeArr);
						}
						cdeNodeArr[0] = quesNode;
					}
					else if (quesNodeName.equalsIgnoreCase("ns1:cdeLongName") && cdeNodeArr != null) {
						cdeNodeArr[1] = quesNode;
					}
					else if (quesNodeName.equalsIgnoreCase("ns1:decId")) {
						if (decNodeArr == null) {
							decNodeArr = new Node[2];
							decNodes.add(decNodeArr);
						}
						decNodeArr[0] = quesNode;
						
						if (cdeNodeArr == null) {
							cdeNodeArr = new Node[4];
							cdeNodes.add(cdeNodeArr);
						}
						cdeNodeArr[2] = quesNode;
					}
					
					else if (quesNodeName.equalsIgnoreCase("ns1:vdId")) {
						if (vdNodeArr == null) {
							vdNodeArr = new Node[4];
							vdNodes.add(vdNodeArr);
						}
						vdNodeArr[0] = quesNode;
						
						if (cdeNodeArr == null) {
							cdeNodeArr = new Node[4];
							cdeNodes.add(cdeNodeArr);
						}
						cdeNodeArr[3] = quesNode;
					}
					
					else if (quesNodeName.equalsIgnoreCase("ns1:dataType") && vdNodeArr!=null) {
						
						vdNodeArr[1] = quesNode;
					}
					
					else if (quesNodeName.equalsIgnoreCase("ns1:enumerated") && vdNodeArr!=null) {
						vdNodeArr[2] = quesNode;
					}
					
					else if (quesNodeName.equalsIgnoreCase("ns1:decConceptualDomainId") || quesNodeName.equalsIgnoreCase("ns1:vdConceptualDomainId")) {
						cdNodeArr = new Node[1];
						cdNodes.add(cdNodeArr);
						
						cdNodeArr[0] = quesNode;
						
						if (quesNodeName.equalsIgnoreCase("ns1:decConceptualDomainId")) {
							if (decNodeArr == null) {
								decNodeArr = new Node[2];
								decNodes.add(decNodeArr);
							}
							decNodeArr[1] = quesNode;
						}
						else if (quesNodeName.equalsIgnoreCase("ns1:vdConceptualDomainId")) {
							if (vdNodeArr == null) {
								vdNodeArr = new Node[4];
								vdNodes.add(vdNodeArr);
							}
							vdNodeArr[3] = quesNode;
						}
					}
					
					else if (quesNodeName.equalsIgnoreCase("ns1:ocPrimConcepts") || quesNodeName.equalsIgnoreCase("ns1:propPrimConcepts")
							|| quesNodeName.equalsIgnoreCase("ns1:repTermQualConcepts")|| quesNodeName.equalsIgnoreCase("ns1:repTermPrimConcepts")
							|| quesNodeName.equalsIgnoreCase("ns1:vmConcepts")) {
						
						conNodeArr = new Node[1];
						conNodes.add(conNodeArr);
						conNodeArr[0] = quesNode;
					}
				}
				
			}
			writeCon(conNodes, wb);
			writeCD(cdNodes, wb);
			writeVD(vdNodes, wb);
			writeDEC(decNodes, wb);
			writeDE(cdeNodes, wb);
			
			NodeList csNodes = doc.getElementsByTagName("ns1:cs");
			if (csNodes.getLength() > 0) {
				writeCS(csNodes.item(0), wb);
			}
			else {
				throw new Exception("No CS defined!!");
			}
			
			NodeList csiNodes = doc.getElementsByTagName("ns1:csi");
			if (csiNodes.getLength() > 0) {
				writeCSI(csiNodes.item(0), wb);
			}
			else {
				throw new Exception("No CSI defined!!");
			}
			wb.write(new FileOutputStream(_xcelFile));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void writeDE(List<Node[]> cdeIdNodes, HSSFWorkbook wb) {
		HSSFSheet sheet = wb.getSheet("tbl_DATA_ELEMENTS");
		int rowNo = -1;
		
		for (int i=0;i<cdeIdNodes.size();i++) {
			Node[] cdeIdRow = cdeIdNodes.get(i);
			Node cdeIdNode = cdeIdRow[0];

			if (cdeIdNode != null) {
				rowNo++;
				String cdeIdRep = cdeIdNode.getTextContent();
				String[] cdeIdParts = cdeIdRep.split("v");
				
				if (cdeIdParts != null && cdeIdParts.length>=2) {
					HSSFRow row = sheet.createRow(10+rowNo);
					
					HSSFCell seqIdCell = row.createCell(1);
					HSSFCell longNameCell = row.createCell(2);
					HSSFCell versionCell = row.createCell(3);
					HSSFCell ctxIdCell = row.createCell(4);
					HSSFCell prefNameCell = row.createCell(5);
					HSSFCell vdIdSeqCell = row.createCell(6);
					HSSFCell decIdSeqCell = row.createCell(7);
					HSSFCell prefDefCell = row.createCell(8);
					HSSFCell aslNameCell = row.createCell(9);
					HSSFCell dateCreatedCell = row.createCell(10);
					HSSFCell createdByCell = row.createCell(11);
					HSSFCell idCell = row.createCell(12);
					
					StringBuffer cdeId = new StringBuffer("DE"+rowNo);
					while (cdeId.length() < 36) {
						cdeId.append("x");
					}
					
					seqIdCell.setCellValue(new HSSFRichTextString(cdeId.toString()));
					ctxIdCell.setCellValue(new HSSFRichTextString("6BF1D8AD-29FB-6CF3-E040-A8C0955834A9"));
					idCell.setCellValue(Double.parseDouble(cdeIdParts[0]));
					versionCell.setCellValue(Double.parseDouble(cdeIdParts[1]));
					
					if (cdeIdRow.length > 1 && cdeIdRow[1] != null) {
						longNameCell.setCellValue(new HSSFRichTextString(cdeIdRow[1].getTextContent()));
					}
					
					prefNameCell.setCellValue(new HSSFRichTextString("DE Pref Name"+rowNo));
					prefDefCell.setCellValue(new HSSFRichTextString("DE Pref Def"+rowNo));
					aslNameCell.setCellValue(new HSSFRichTextString("RELEASED"));
					dateCreatedCell.setCellValue(new Date());
					createdByCell.setCellValue(new HSSFRichTextString("MATHURA"));
					
					if (cdeIdRow.length > 2 && cdeIdRow[2] != null) {
						decIdSeqCell.setCellValue(new HSSFRichTextString(decMap.get(cdeIdRow[2].getTextContent())));
					}
					
					if (cdeIdRow.length > 3 && cdeIdRow[3] != null) {
						vdIdSeqCell.setCellValue(new HSSFRichTextString(vdMap.get(cdeIdRow[3].getTextContent())));
					}
					
					
				}
			}
			
		}
	}
	
	private void writeDEC(List<Node[]> decIdNodes, HSSFWorkbook wb) {
		HSSFSheet sheet = wb.getSheet("tbl_DATA_ELEMENT_CONCEPTS");
		int decRowNum = 9;
		for (int i=0;i<decIdNodes.size();i++) {
			Node[] decIdRow = decIdNodes.get(i);
			Node decIdNode = decIdRow[0];

			if (decIdNode != null) {
				String decIdRep = decIdNode.getTextContent();
				String[] decIdParts = decIdRep.split("v");
				
				if (decIdParts != null && decIdParts.length>=2 && !decIds.contains(decIdParts[0])) {
					decIds.add(decIdParts[0]);
					decRowNum++;
					HSSFRow row = sheet.createRow(decRowNum);
					
					HSSFCell seqIdCell = row.createCell(1);
					HSSFCell versionCell = row.createCell(2);
					HSSFCell ctxIdCell = row.createCell(4);
					HSSFCell prefNameCell = row.createCell(3);
					HSSFCell decIdSeqCell = row.createCell(5);
					HSSFCell prefDefCell = row.createCell(6);
					HSSFCell aslNameCell = row.createCell(7);
					HSSFCell dateCreatedCell = row.createCell(8);
					HSSFCell createdByCell = row.createCell(9);
					HSSFCell idCell = row.createCell(10);
					
					StringBuffer decId = new StringBuffer("DEC"+i);
					
					while (decId.length() < 36) {
						decId.append("x");
					}
					
					seqIdCell.setCellValue(new HSSFRichTextString(decId.toString()));
					ctxIdCell.setCellValue(new HSSFRichTextString("6BF1D8AD-29FB-6CF3-E040-A8C0955834A9"));
					idCell.setCellValue(Double.parseDouble(decIdParts[0]));
					versionCell.setCellValue(Double.parseDouble(decIdParts[1].split(":")[0]));
					
					prefNameCell.setCellValue(new HSSFRichTextString("DEC Pref Name"+i));
					prefDefCell.setCellValue(new HSSFRichTextString("DEC Pref Def"+i));
					aslNameCell.setCellValue(new HSSFRichTextString("RELEASED"));
					dateCreatedCell.setCellValue(new Date());
					createdByCell.setCellValue(new HSSFRichTextString("MATHURA"));
					
					if (decIdRow.length > 1 && decIdRow[1] !=null) {
						String cdId = decIdRow[1].getTextContent();
						
						if (cdMap.get(cdId) != null) {
							decIdSeqCell.setCellValue(new HSSFRichTextString(cdMap.get(cdId)));
						}
					}
					
					decMap.put(decIdRep, decId.toString());
				}
			}
			
		}
	}
	
	private void writeVD(List<Node[]> vdIdNodes, HSSFWorkbook wb) {
		HSSFSheet sheet = wb.getSheet("tbl_VALUE_DOMAINS");
		int vdRowNum = 9;
		for (int i=0;i<vdIdNodes.size();i++) {
			Node[] vdIdRow = vdIdNodes.get(i);
			Node vdIdNode = vdIdRow[0];

			if (vdIdNode == null) {
				if (vdIdRow.length > 1) {
					String dt = vdIdRow[1].getTextContent().toUpperCase();
					if (!dataTypes.contains(dt)) {
						try {
							DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
							DocumentBuilder parser = factory.newDocumentBuilder();
							Document doc = parser.newDocument();
							org.w3c.dom.Element elem = doc.createElement("node");
							elem.setTextContent("1111v1.0:Sample VD");
							
							vdIdNode = elem;
						} catch (DOMException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParserConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
			if (vdIdNode != null) {
				String vdIdRep = vdIdNode.getTextContent();
				String[] vdIdParts = vdIdRep.split("v");
				
				if (vdIdParts != null && vdIdParts.length>=2 && !vdIds.contains(vdIdParts[0])) {
					vdIds.add(vdIdParts[0]);
					vdRowNum++;
					HSSFRow row = sheet.createRow(vdRowNum);
					
					HSSFCell seqIdCell = row.createCell(1);
					HSSFCell versionCell = row.createCell(2);
					HSSFCell longNameCell = row.createCell(3);
					HSSFCell ctxIdCell = row.createCell(5);
					HSSFCell prefNameCell = row.createCell(4);
					HSSFCell prefDefCell = row.createCell(6);
					HSSFCell dtlNameCell = row.createCell(7);
					HSSFCell cdIdSeqCell = row.createCell(8);
					HSSFCell vdTypeCell = row.createCell(9);
					HSSFCell aslNameCell = row.createCell(10);
					HSSFCell dateCreatedCell = row.createCell(11);
					HSSFCell createdByCell = row.createCell(12);
					HSSFCell idCell = row.createCell(13);
					
					StringBuffer vdId = new StringBuffer("VD"+i);
					while (vdId.length() < 36) {
						vdId.append("x");
					}
					
					seqIdCell.setCellValue(new HSSFRichTextString(vdId.toString()));
					ctxIdCell.setCellValue(new HSSFRichTextString("6BF1D8AD-29FB-6CF3-E040-A8C0955834A9"));
					idCell.setCellValue(Double.parseDouble(vdIdParts[0]));
					versionCell.setCellValue(Double.parseDouble(vdIdParts[1].split(":")[0]));
					longNameCell.setCellValue(new HSSFRichTextString(vdIdParts[1].split(":")[1]));
					
					prefNameCell.setCellValue(new HSSFRichTextString("VD Pref Name"+i));
					prefDefCell.setCellValue(new HSSFRichTextString("VD Pref Def"+i));
					aslNameCell.setCellValue(new HSSFRichTextString("RELEASED"));
					dateCreatedCell.setCellValue(new Date());
					createdByCell.setCellValue(new HSSFRichTextString("MATHURA"));
					
					if (vdIdRow.length > 1 && vdIdRow[1] != null) {
						dtlNameCell.setCellValue(new HSSFRichTextString(vdIdRow[1].getTextContent().toUpperCase()));
					}
					
					if (vdIdRow.length > 2 && vdIdRow[2] != null) {
						String enumYesNo = vdIdRow[2].getTextContent();
						String enumer = enumYesNo.equalsIgnoreCase("Yes")?"E":"N";
						
						vdTypeCell.setCellValue(new HSSFRichTextString(enumer));
					}
					
					if (vdIdRow.length > 3 && vdIdRow[3] != null) {
						String cdId = vdIdRow[3].getTextContent();
						
						if (cdMap.get(cdId) != null) {
							cdIdSeqCell.setCellValue(new HSSFRichTextString(cdMap.get(cdId)));
						}
					}
					
					vdMap.put(vdIdRep, vdId.toString());
				}
			}
			
		}
	}
	
	private void writeCD(List<Node[]> cdIdNodes, HSSFWorkbook wb) {
		HSSFSheet sheet = wb.getSheet("tbl_CONCEPTUAL_DOMAINS");
		int cdRowNum = 10;
		for (int i=0;i<cdIdNodes.size();i++) {
			Node[] cdIdRow = cdIdNodes.get(i);
			Node cdIdNode = cdIdRow[0];

			String cdIdRep = cdIdNode.getTextContent();
			String[] cdIdParts = cdIdRep.split("v");
			
			if (cdIdParts != null && cdIdParts.length>=2 && !cdIds.contains(cdIdParts[0])) {
				cdIds.add(cdIdParts[0]);
				cdRowNum++;
				HSSFRow row = sheet.createRow(cdRowNum);
				
				HSSFCell seqIdCell = row.createCell(1);
				HSSFCell versionCell = row.createCell(2);
				HSSFCell ctxIdCell = row.createCell(4);
				HSSFCell prefNameCell = row.createCell(3);
				HSSFCell prefDefCell = row.createCell(5);
				HSSFCell aslNameCell = row.createCell(6);
				HSSFCell dateCreatedCell = row.createCell(7);
				HSSFCell createdByCell = row.createCell(8);
				HSSFCell idCell = row.createCell(9);
				
				StringBuffer cdId = new StringBuffer("CD"+i);
				
				while (cdId.length() < 36) {
					cdId.append("x");
				}
				
				seqIdCell.setCellValue(new HSSFRichTextString(cdId.toString()));
				ctxIdCell.setCellValue(new HSSFRichTextString("6BF1D8AD-29FB-6CF3-E040-A8C0955834A9"));
				idCell.setCellValue(Double.parseDouble(cdIdParts[0]));
				versionCell.setCellValue(Double.parseDouble(cdIdParts[1].split(":")[0]));
				
				prefNameCell.setCellValue(new HSSFRichTextString("CD Pref Name"+i));
				prefDefCell.setCellValue(new HSSFRichTextString("CD Pref Def"+i));
				aslNameCell.setCellValue(new HSSFRichTextString("RELEASED"));
				dateCreatedCell.setCellValue(new Date());
				createdByCell.setCellValue(new HSSFRichTextString("MATHURA"));
				
				cdMap.put(cdIdRep, cdId.toString());
			}
		}
	}
	
	private void writeCS(Node csNode, HSSFWorkbook wb) {
		HSSFSheet sheet = wb.getSheet("tbl_CLASSIFICATION_SCHEMES");
		HSSFRow row = sheet.getRow(10);
		HSSFCell prefNameCell = row.createCell(3);
		HSSFCell longNameCell = row.createCell(4);
		HSSFCell prefDefCell = row.createCell(5);
		
		prefNameCell.setCellValue(new HSSFRichTextString(csNode.getTextContent()));
		longNameCell.setCellValue(new HSSFRichTextString(csNode.getTextContent()));
		prefDefCell.setCellValue(new HSSFRichTextString(csNode.getTextContent()));
	}
	
	private void writeCSI(Node csiNode, HSSFWorkbook wb) {
		HSSFSheet sheet = wb.getSheet("tbl_CS_ITEMS");
		HSSFRow row = sheet.getRow(10);
		HSSFCell prefNameCell = row.createCell(6);
		HSSFCell longNameCell = row.createCell(7);
		HSSFCell prefDefCell = row.createCell(8);
		
		prefNameCell.setCellValue(new HSSFRichTextString(csiNode.getTextContent()));
		longNameCell.setCellValue(new HSSFRichTextString(csiNode.getTextContent()));
		prefDefCell.setCellValue(new HSSFRichTextString(csiNode.getTextContent()));
	}
	
	private void writeCon(List<Node[]> conIdNodes, HSSFWorkbook wb) {
		HSSFSheet sheet = wb.getSheet("tbl_CONCEPTS_EXT");
		int conRowNum = 9;
		int id = 1110;
		
		int j = -1;
		
		for (int i=0;i<conIdNodes.size();i++) {
			Node[] conIdRow = conIdNodes.get(i);
			Node conIdNode = conIdRow[0];

			String conIdRep = conIdNode.getTextContent();
			String[] concepts = conIdRep.split(";");
			
			for (String concept: concepts) {
				j++;
				String[] cdeIdParts = concept.split(":");
				
				if (cdeIdParts != null && cdeIdParts.length>=2 && !conIds.contains(cdeIdParts[0])) {
					String cdeId = cdeIdParts[0].trim();
					String longName = cdeIdParts[1].trim();
					conIds.add(cdeId);
					conRowNum++;
					id++;
					
					HSSFRow row = sheet.createRow(conRowNum);
					
					HSSFCell seqIdCell = row.createCell(1);
					HSSFCell versionCell = row.createCell(6);
					HSSFCell ctxIdCell = row.createCell(5);
					HSSFCell prefNameCell = row.createCell(2);
					HSSFCell longNameCell = row.createCell(3);
					HSSFCell prefDefCell = row.createCell(4);
					HSSFCell aslNameCell = row.createCell(7);
					HSSFCell idCell = row.createCell(8);
					HSSFCell defSourceCell = row.createCell(9);
					
					StringBuffer conId = new StringBuffer("CON"+j);
					
					while (conId.length() < 36) {
						conId.append("x");
					}
					
					seqIdCell.setCellValue(new HSSFRichTextString(conId.toString()));
					ctxIdCell.setCellValue(new HSSFRichTextString("6BF1D8AD-29FB-6CF3-E040-A8C0955834A9"));
					idCell.setCellValue(Double.parseDouble(id+""));
					versionCell.setCellValue(Double.parseDouble("1.0"));
					
					prefNameCell.setCellValue(new HSSFRichTextString(cdeId));
					longNameCell.setCellValue(new HSSFRichTextString(longName));
					prefDefCell.setCellValue(new HSSFRichTextString("DEC Pref Def"+i));
					aslNameCell.setCellValue(new HSSFRichTextString("RELEASED"));
					defSourceCell.setCellValue(new HSSFRichTextString("NCI"));
				}
			}			
		}
	}
	
	public static void main(String[] args) {
		TestUtil testUtil = new TestUtil();
		Document doc = testUtil.readXMLInput(new File("C:\\Docume~1\\mathura2\\Desktop\\Retest_Failures_BL_27jan2010_v2.xml"));
		File xcelFile = testUtil.createExcelFile("C:\\Docume~1\\mathura2\\Desktop\\Retest_Failures_BL_27jan2010_v2.xls");
		testUtil.extractToExcel(xcelFile, doc);
	}
}
