package internetresource.category;

import internetresource.InternetResourceBase;

import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import structure.CategoryNode;

public class GmarketCategoryResource extends InternetResourceBase {
	
	static String ITEM_LIST_URL = "http://category.gmarket.co.kr/listview/L100000051.aspx";
	static String CATEGORY_XPATH = "//*[@id=\"gnb\"]/ul[*]/li[*]/a[1]";
	
	public List<CategoryNode> getCategoryNode() throws XPatherException {
		ArrayList<CategoryNode> rtn = new ArrayList<CategoryNode>();
		Object[] categoryNodes = rootNode.evaluateXPath(CATEGORY_XPATH);
		for( int count = 0; categoryNodes != null && count < categoryNodes.length; count++ ) {
			try {
				CategoryNode category = new CategoryNode();
				TagNodeEx categoryNode = new TagNodeEx( (TagNode)categoryNodes[count] );
				category.setCategoryURL( categoryNode.getOrgNode().getAttributeByName("href") );
				category.setTitle( categoryNode.getOrgNode().getText().toString() );
				rtn.add(category);
			} catch ( Exception e ) {
				// Skip.
			}
		}
		return rtn;
	}
	
	public static void main(String[] args) throws Exception {
		GmarketCategoryResource resource = new GmarketCategoryResource();
		resource.getRootTagNode(ITEM_LIST_URL, null, "EUC-KR");
		List<CategoryNode> categories = resource.getCategoryNode();
		for( int cnt = 0 ; cnt < categories.size() ; cnt++ ) {
			System.out.println(categories.get(cnt).getTitle() + ":" + categories.get(cnt).getCategoryURL() );
		}
	}
}
