package internetresource.goods;

import internetresource.InternetResourceBase;

import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import structure.GoodsNode;

public class GmarketUsedShopResource extends InternetResourceBase {
	
	static String ITEM_LIST_URL = "http://category.gmarket.co.kr/listview/List.aspx?gdmc_cd=200000821&ecp_gdlc=&ecp_gdmc=";
	static String GOODS_XPATH = "//*[@id=\"tableCommonList\"]/tbody/tr";
	
	public List<GoodsNode> getGoodsNode() throws XPatherException {
		ArrayList<GoodsNode> rtn = new ArrayList<GoodsNode>();
		Object[] goodsNodeList = rootNode.evaluateXPath(GOODS_XPATH);
		for( int count = 0; goodsNodeList != null && count < goodsNodeList.length; count++ ) {
			try {
				GoodsNode goods = new GoodsNode();
				TagNodeEx goodsNode = new TagNodeEx( (TagNode)goodsNodeList[count] );
				goods.setImageUrl( goodsNode.getChildTagNodeExbyName("goods")
						.getChildTagNodeExbyName("img")
						.getChildTagNodeExbyName("img-cont")
						.getChildTagNodeExbyName("default")
						.getOrgNode().getText().toString() );
				goods.setTitle( goodsNode.getChildTagNodeExbyName("goods")
						.getChildTagNodeExbyName("info")
						.getChildTagNodeExbyName("goods-name")
						.getOrgNode().getText().toString() );
				goods.setOrgPrice( goodsNode.getChildTagNodeExbyName("price")
						.getChildTagNodeExbyName("price-cont")
						.getChildTagNodeExbyName("orgin-price")
						.getOrgNode().getText().toString() );
				goods.setDiscountPrice( goodsNode.getChildTagNodeExbyName("price")
						.getChildTagNodeExbyName("price-cont")
						.getChildTagNodeExbyName("discount-price")
						.getOrgNode().getText().toString() );
				rtn.add(goods);
			} catch ( Exception e ) {
				// Skip.
			}
		}
		return rtn;
	}
	
	public static void main(String[] args) throws Exception {
		GmarketUsedShopResource resource = new GmarketUsedShopResource();
		resource.getRootTagNode(ITEM_LIST_URL, null, "EUC-KR");
		List<GoodsNode> goods = resource.getGoodsNode();
		for( int cnt = 0 ; cnt < goods.size() ; cnt++ ) {
			System.out.println(goods.get(cnt).getTitle());
		}
	}
}
