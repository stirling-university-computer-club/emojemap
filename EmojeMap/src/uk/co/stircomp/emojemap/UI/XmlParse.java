package uk.co.stircomp.emojemap.UI;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParse {

	public static void main (String[] args)
	{
		ArrayList<ArrayList<String>> coordinates = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<ArrayList<String>>> coordinateX = new ArrayList<ArrayList<ArrayList<String>>>(), coordinateY = new ArrayList<ArrayList<ArrayList<String>>>(); 
		ArrayList<String> names = new ArrayList<String>();
		
		String filename = "world.xml";
		
		
		try {
		 
			File fXmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
		 
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		 
			NodeList data = getNode(doc.getChildNodes(), "root").getChildNodes();
			
			for (int i = 1; i < data.getLength(); i += 2)
			{
				int arrayIndex = i - (i + 1) / 2;
				coordinates.add(new ArrayList<String>());
				NodeList country = data.item(i).getChildNodes();
				// coordinates
				// polygons 1
				
				/*
				for (int j = 0; i < getNode(country,"geometry").getChildNodes().getLength(); i++)
					System.out.println(getNode(country,"geometry").getChildNodes().item(i).getNodeName());
				*/
				NodeList polygons = null;

				if (getNode(getNode(country,"geometry").getChildNodes(), "MultiGeometry") == null)
					polygons = getNode(country,"geometry").getChildNodes();
				else
					polygons = getNode(getNode(country,"geometry").getChildNodes(), "MultiGeometry").getChildNodes();
				
				for (int j = 1; j < polygons.getLength(); j += 2)
				{
					// polygon -> outerBoundaryIs -> LinearRing -> coordinates
					Node item = getNode(getNode(getNode(polygons.item(j).getChildNodes(),"outerBoundaryIs").getChildNodes(),"LinearRing").getChildNodes(),"coordinates");
					//System.out.println(item.getTextContent());
					coordinates.get(arrayIndex).add(item.getTextContent());
				}
				// name 3
				Node name = getNode(country,"Name");
				names.add(name.getTextContent());
			}
			
			
		    } catch (Exception e) {
			e.printStackTrace();
		}

		
		
		
		for (int i = 0; i < names.size(); i++)
		{
			coordinateX.add(new ArrayList<ArrayList<String>>());
			coordinateY.add(new ArrayList<ArrayList<String>>());
			for (int j = 0; j < coordinates.get(i).size(); j++)
			{
				coordinateX.get(i).add(new ArrayList<String>());
				coordinateY.get(i).add(new ArrayList<String>());
				
				String[] arr = coordinates.get(i).get(j).split(",");
				for (int k = 0; k < arr.length; k++)
				{
					// if even and is y
					if (arr[k].contains("0 ")){
						coordinateY.get(i).get(j).add(arr[k].substring(2, arr[k].length()-1));
						//System.out.println(arr[k]);
					}
					else if (k == 0)
					{
						coordinateY.get(i).get(j).add(arr[k]);
					}
					// if x
					else if (k != arr.length - 1)
					{
						coordinateX.get(i).get(j).add(arr[k]);
						//System.out.println(arr[k]);
					}
				}/*
				for (int k = 0; k < coordinateY.get(i).get(j).size(); k++)
					System.out.println(coordinateX.get(i).get(j).get(k) + " / " + coordinateY.get(i).get(j).get(k));*/
			}
			
			//System.out.println(names.get(i));
		}
		

		for (int i = 0; i < names.size(); i++)
		{
			for (int j = 0; j < coordinates.get(i).size(); j++)
			{
				
				StringBuilder variableName = new StringBuilder();
				if (names.get(i).contains(" ") && names.get(i).contains("."))
				{
					StringBuilder tmp = new StringBuilder();

					String [] nameArr1 = names.get(i).split(" ");
					for (int a = 0; a < nameArr1.length; a++)
					{
						if (a == 0)
							tmp.append(nameArr1[a].toLowerCase());
						else
							tmp.append(nameArr1[a]);
					}
					String [] nameArr2 = names.get(i).split(".");
					for (int a = 0; a < nameArr2.length; a++)
					{
						if (a == 0)
							variableName.append(nameArr2[a].toLowerCase());
						else
							variableName.append(nameArr2[a]);
					}
				}
				else if (names.get(i).contains(" ") || names.get(i).contains("."))
				{
					String seperator = "";
					if (names.get(i).contains(" "))
						seperator = " ";
					else
						seperator = ".";
					
					String [] nameArr = names.get(i).split(seperator);
					for (int a = 0; a < nameArr.length; a++)
					{
						if (a == 0)
							variableName.append(nameArr[a].toLowerCase());
						else
							variableName.append(nameArr[a]);
					}
				}
				else
					variableName.append(names.get(i).toLowerCase());
				
				variableName.append(Integer.toString(j));
				
				System.out.print("MapPolygonImpl " + variableName + " = new MapPolygonImpl(\"" + names.get(i) + "\", ");
			
				for (int k = 0; k < coordinateX.get(i).get(j).size(); k++)
				{
					String x = String.format("%.4g", Float.parseFloat(coordinateX.get(i).get(j).get(k)));
					String y = String.format("%.4g", Float.parseFloat(coordinateY.get(i).get(j).get(k)));
					if (k != coordinateX.get(i).get(j).size() - 1)
						System.out.print("c(" + x + "," + y + "), ");
					else
						System.out.print("c(" + x + "," + y + ")");
				}
				System.out.println(");");
				System.out.println("countryList.add(" + variableName + ");");
				System.out.println();				
			}
			
		}

	}
	
	private static Node getNode(NodeList list, String name)
	{
		Node node = null;
		
		for (int i = 0; i < list.getLength(); i++)
		{
			if (list.item(i).getNodeName() == name)
				return list.item(i);
		}
		
		return node;
	}
}
