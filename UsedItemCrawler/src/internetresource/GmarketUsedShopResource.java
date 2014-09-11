package internetresource;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class GmarketUsedShopResource {
	
	static String ITEM_LIST_URL = "http://category.gmarket.co.kr/listview/List.aspx?gdmc_cd=200000821&ecp_gdlc=&ecp_gdmc=";
	static String GOODS_XPATH = "//*[@id=\"tableCommonList\"]/tbody/tr";
	static HtmlCleaner cleaner = new HtmlCleaner();
	
	static class GoodsNode {
		String imageUrl;
		String title;
		String orgPrice;
		String discountPrice;
		String sellerRating;
	}
	
	TagNode rootNode = null;
	
	static class TagNodeEx {
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
	
	public void getRootTagNode() {
		TagNode itemListXML = null;
		HttpURLConnection conn = null;
		OutputStream os = null;
		BufferedReader br = null;
		try {
			conn = (HttpURLConnection)new URL(ITEM_LIST_URL).openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setDoOutput(false);
			InputStream is = conn.getInputStream();
			itemListXML = cleaner.clean(is, "EUC-KR");
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
	
	public List<GoodsNode> getGoodsNode() throws XPatherException {
		ArrayList<GoodsNode> rtn = new ArrayList<GoodsNode>();
		Object[] goodsNodeList = rootNode.evaluateXPath(GOODS_XPATH);
		for( int count = 0; goodsNodeList != null && count < goodsNodeList.length; count++ ) {
			try {
				GoodsNode goods = new GoodsNode();
				TagNodeEx goodsNode = new TagNodeEx( (TagNode)goodsNodeList[count] );
				goods.imageUrl = goodsNode.getChildTagNodeExbyName("goods")
						.getChildTagNodeExbyName("img")
						.getChildTagNodeExbyName("img-cont")
						.getChildTagNodeExbyName("default")
						.getOrgNode().getText().toString();
				goods.title = goodsNode.getChildTagNodeExbyName("goods")
						.getChildTagNodeExbyName("info")
						.getChildTagNodeExbyName("goods-name")
						.getOrgNode().getText().toString();
				goods.orgPrice = goodsNode.getChildTagNodeExbyName("price")
						.getChildTagNodeExbyName("price-cont")
						.getChildTagNodeExbyName("orgin-price")
						.getOrgNode().getText().toString();
				goods.discountPrice = goodsNode.getChildTagNodeExbyName("price")
						.getChildTagNodeExbyName("price-cont")
						.getChildTagNodeExbyName("discount-price")
						.getOrgNode().getText().toString();
				rtn.add(goods);
			} catch ( Exception e ) {
				// Skip.
			}
		}
		return rtn;
	}
	
	public static void main(String[] args) throws Exception {
		GmarketUsedShopResource resource = new GmarketUsedShopResource();
		resource.getRootTagNode();
		List<GoodsNode> goods = resource.getGoodsNode();
		for( int cnt = 0 ; cnt < goods.size() ; cnt++ ) {
			System.out.println(goods.get(cnt).title);
		}
	}
}
