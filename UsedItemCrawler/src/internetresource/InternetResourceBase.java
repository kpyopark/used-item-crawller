package internetresource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class InternetResourceBase {

	protected TagNode rootNode = null;
	protected HtmlCleaner cleaner = new HtmlCleaner();

	protected static class TagNodeEx {
			TagNode orgNode;
			public TagNodeEx(TagNode node) {
				orgNode = node;
			}
			public TagNodeEx getChildTagNodeExbyName(String name) {
				TagNode[] children = orgNode.getChildTags();
				if ( children == null ) return null;
				TagNodeEx rtn = null;
				for( int index = 0 ; index < children.length ; index++ ) {
					if ( children[index].getAttributeByName("class") .equals(name) ) {
						rtn = new TagNodeEx(children[index]);
						break;
					}
				}
				return rtn;
			}
			public TagNode getOrgNode() {
				return orgNode;
			}
		}

	public InternetResourceBase() {
		super();
	}

	public void getRootTagNode(String url, HashMap<String, String> requestParams, String encoding) {
		TagNode itemListXML = null;
		HttpURLConnection conn = null;
		OutputStream os = null;
		BufferedReader br = null;
		try {
			conn = (HttpURLConnection)new URL(url).openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setDoOutput(false);
			if ( requestParams != null ) {
				for ( String paramname : requestParams.keySet() ) {
					conn.setRequestProperty(paramname, requestParams.get(paramname));
				}
			}
			InputStream is = conn.getInputStream();
			itemListXML = cleaner.clean(is, encoding);
			//System.out.println("original:" + itemListXML.getText().toString());
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			if ( conn != null ) try { conn.disconnect(); } catch ( Exception e1 ) {e1.printStackTrace();}
			if ( br != null ) try { br.close(); } catch ( Exception e1 ) {e1.printStackTrace();}
			//else if ( is != null ) try { is.close(); } catch ( Exception e1 ) {e1.printStackTrace();}
		}
		rootNode = itemListXML;
	}

}